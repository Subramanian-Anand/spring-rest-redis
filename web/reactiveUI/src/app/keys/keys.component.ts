import { Input, Output, EventEmitter, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-keys',
  templateUrl: './keys.component.html',
  styleUrls: ['./keys.component.css']
})
export class KeysComponent implements OnInit {

  keyval = {}
  duration = 10
  keypattern = "*INV*#act_p"
  keysToMonitor: any = []
  sourceConnections = []

  tblConf = JSON.stringify({
              "DeviceId": "rediskey.split('#')[8]",
              "DeviceType": "rediskey.split('#')[4]",
              "Parameter": "rediskey.split('#')[9]",
              "Value": "redisvalue['value']",
              "Timestamp": "redisvalue['ts']",
              "Status": "redisvalue['status']"
            })
  /**tblConf = JSON.stringify({
      "DeviceId": "rediskey.split('-')[0]",
      "Parameter": "rediskey.split('-')[1]",
      "Timestamp": "redisvalue",
      "Value": "redisvalue"
  })**/
  tblData = {}
  tblCols = []


  constructor(private http: HttpClient) {
  }

  initTbl() {
    var that = this;
    that.tblData = JSON.parse(that.tblConf);
    that.tblCols = Object.keys(that.tblData);
  }

  startMonitor(){
  	var that = this;
  	that.initTbl();
  	that.stopMonitor();
  	this.http.get("http://localhost:8080/keys/" + encodeURIComponent(this.keypattern))
  	.subscribe(keysData => {
  	  let keys = keysData as Array<string>;
  	  keys.sort();
  		that.keysToMonitor = keys;
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

  eval(exp, rediskey, redisvalue) {
    var that = this;

    return eval(exp);
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
