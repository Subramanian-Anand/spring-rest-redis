import { Input, Output, EventEmitter, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as SockJS from './sockjs.min.js';
import { Stomp } from './stomp.min.js';
import { isDevMode } from '@angular/core';

@Component({
  selector: 'app-keys',
  templateUrl: './keys.component.html',
  styleUrls: ['./keys.component.css']
})
export class KeysComponent implements OnInit {

  keyval = {}
  duration = 10
  keypattern = "*key"
  keysToMonitor: any = []
  sourceConnections = []
  stompClient: any;
  domainUrlPrefix = isDevMode() ? "http://localhost:8080/" : "/"


  /**tblConf = JSON.stringify({
              "DeviceId": "rediskey.split('#')[8]",
              "DeviceType": "rediskey.split('#')[4]",
              "Parameter": "rediskey.split('#')[9]",
              "Value": "redisvalue['value']",
              "Timestamp": "redisvalue['ts']",
              "Status": "redisvalue['status']"
            })**/
  tblConf = JSON.stringify({
    "RedisKey": "rediskey",
    "RedisValue": "redisvalue"
  })
  /**tblConf = JSON.stringify({
      "DeviceId": "rediskey.split('-')[0]",
      "Parameter": "rediskey.split('-')[1]",
      "Timestamp": "redisvalue.ts",
      "Value": "redisvalue.value"
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
    var that = this
    var socket = new SockJS(that.domainUrlPrefix+'gs-guide-websocket');
    that.stompClient = Stomp.over(socket);
    that.stompClient.debug = null;
    that.stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        that.initTbl();
        that.stopMonitor();
        that.http.get(that.domainUrlPrefix + "keys/" + encodeURIComponent(that.keypattern))
        .subscribe(keysData => {
          //console.log('keysData', keysData)
          let keys = keysData as Array<string>;
          keys.sort();
          that.keysToMonitor = keys;
          that.webSocketSubscriber(keysData);
        })
    });
  }

  webSocketSubscriber(keysData) {
    var that = this
    keysData.forEach((key, index) => {
      that.stompClient.subscribe('/keySubscription/'+ key, function (data) {
          var json = JSON.parse(data.body);
          console.log('json.redisvalue', json.redisvalue)
          that.keyval[key] = json.redisvalue;
      });
      that.stompClient.send("/app/hello/" + key, {}, JSON.stringify({}));
    });
  }

  subscriber(keysData) {
  	var that = this;

  	keysData.forEach((key, index) => {
  	 	var url = that.domainUrlPrefix + 'reactivegetSSE/'+encodeURIComponent(key)+'/';
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

    var result = "";

    try{
      result = eval(exp);
    }catch(e){
      //console.log(rediskey, redisvalue, exp, e)
      //console.log(rediskey, redisvalue)
      result = "NA"
    }

    return result;
  }

  subscriber1(keysData) {
  	var that = this;
  	var url = that.domainUrlPrefix + 'reactivegets/'+encodeURIComponent(that.keypattern)+"/"+that.duration;

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
  	  //console.log(source)
	  	source.close();
  	});

  	that.keyval = {}
  }

  ngOnInit() {}

}
