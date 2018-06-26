# Spring-rest-redis
This project enables all the Redis Operations as Rest calls via Spring Boot

## Pre-requisites
```shell
docker run --name redis-server -p 6379:6379 -d redis
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
3. Socket enabled get on a key
4. SSE enabled get on a key pattern
5. Socket enabled get on a key pattern




