# Spring-rest-redis
This project enables all the Redis Operations as Rest calls via Spring Boot

## Following are the avaibles operaitons
URL | Description | Example
--- | --- | ---
**/get** | This is used to get value of the key, 'name' | */get/name*
**/set** | This is used to set the value, 'kaushik' for the key, 'name' | */set/name/kaushik*
**/keys** | This is used to get all the keys in Redis | */keys*
**/keys/\<pattern\>** | This is used to get all the keys in Redis with the given pattern | */keys/na**

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
3. SSE eanbled get on a key
4. Socket enabled get on a key
5. SSE enabled get on a key pattern
6. Socket enabled get on a key pattern




