# Spring-rest-redis
This project enables all the Redis Operations as Rest calls via Spring Boot

## Pre-requisites
```shell
docker run --name redis-server -p 6379:6379 -d redis
```

## Docker Binary (Run Pre-requisits first)
```shell
docker pull gtskaushik/spring-rest-redis
docker run --name spring-rest-redis -v <path to application.properties>:/application.properties --network host gtskaushik/spring-rest-redis
```

## Following are the avaibles operaitons
URL | Description | Example
--- | --- | ---
**/get/\<rediskey\>** | This is used to get value of the key, 'name' | */get/name*
**/set/\<rediskey\>/\<redisvalue\>** | This is used to set the value, 'kaushik' for the key, 'name' | */set/name/kaushik*
**/has/\<rediskey\>** | This is used to check if the given key is present in Redis | */has/name*
**/keys** | This is used to get all the keys in Redis | */keys*
**/keys/\<pattern\>** | This is used to get all the keys in Redis with the given pattern | */keys/na**
**/delete/\<rediskey\>** | This is used to get all the keys in Redis with the given pattern | */delete/name*
**/reactiveget/\<rediskey\>/\<durationinsec\>** | This gives a SSE as response for every given duration | */reactiveget/name/2
**/monitor** | This is a sample page which notifies on a key change in Redis and updates it in a table | */monitor

## WebSocket Integration
In this project websockets are craeted using **SockJs** and **Stomp** which are pretty much the standard<br>
**SockJs** - https://cdnjs.com/libraries/sockjs-client <br>
**Stomp** - https://cdnjs.com/libraries/stomp.js/

### Code Snippet to use in client-side Javascript
```javascript
// Note:- Cross Origin is enabled by default
// The url mentioned below is the websocket server
var socket = new SockJS('http://localhost:8080/reactive-redis-websocket');
var stompClient = Stomp.over(socket);

// Note:- Subscription must be called first before init so that the client side of the
// socket does not miss the initial value for the subscribed key
stompClient.subscribe('/keySubscription/'+ <rediskey>, function (data) {
    console.log(data.body)
});
stompClient.send("/sendToServer/initSubscription/" + <rediskey>, {}, JSON.stringify({}));
//Note:- For subscription and initialization, do not prefix the url with domain url
```

## Build the jar on your own
### Dependencies
1. Java 8 or above
2. Gradle 4.8.1 or above
3. npm
4. angular cli (Installed globally)
### Command to Build
```shell
gradle bootJar
```
Jar is present in ***spring-rest-redis/build/libs/*** <br>
The above command will build angular project and make it as part of Spring Boot application.<br>
Refer to ***springAngularMerge*** task in build.gradle file

## Road Map
1. Delete with pattern
2. List Operations
    * get
    * set
    * find an element
    * get timeseries list
    * set timeseries list
    * SSE on timeseries list
    * Socket on timeseries list
3. SSE enabled get on a key pattern
4. Socket enabled get on a key pattern




