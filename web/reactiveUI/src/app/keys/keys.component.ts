import { Input, Output, EventEmitter, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Component({
  selector: 'app-keys',
  templateUrl: './keys.component.html',
  styleUrls: ['./keys.component.css']
})
export class KeysComponent implements OnInit {

  keyval = {}
  duration = 10
  keypattern = "*IC11-WS-TR*"
  keysToMonitor: any = []
  sourceConnections = []

  constructor(private http: HttpClient) { 
  }

  startMonitor(){
  	var that = this;
  	that.stopMonitor();
  	this.http.get("http://localhost:8080/keys/" + encodeURIComponent(this.keypattern))
  	.subscribe(keysData => {
  		that.keysToMonitor = keysData;
  		that.subscriber(keysData);
  	})
  }

  subscriber(keysData) {
  	var that = this;

  	keysData.forEach((key, index) => {
  	 	var url = 'http://localhost:8080/reactivegetSSE/'+encodeURIComponent(key)+'/';
  	 	var source = new EventSource(url);
  	 	that.sourceConnections.push(source);
        source.addEventListener('message', function(data){
        	console.log("Data Received")
        	var json = JSON.parse(data['data']);
        	that.keyval[key] = JSON.parse(json.redisvalue);
        }, false);
  	});
  }

  subscriber1(keysData) {
  	var that = this;
  	var url = 'http://localhost:8080/reactivegets/'+encodeURIComponent(that.keypattern)+"/"+that.duration;
  	
 	var source = new EventSource(url);
 	that.sourceConnections.push(source);
	source.addEventListener('message', function(data){
		console.log("Data Received")
		var json = JSON.parse(data['data']);
		json.forEach((jsonItem, idx) => {
			that.keyval[jsonItem.rediskey] = JSON.parse(jsonItem.redisvalue);
		})
	}, false);
  }

  stopMonitor(){
  	var that = this;
  	that.sourceConnections.forEach((source) => {
  	console.log(source)
	  	source.close();
  	});

  	that.keyval = {}
  }

  ngOnInit() {}

}
