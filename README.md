# Spring-rest-redis
This project enables all the Redis Operations as Rest calls via Spring Boot

## Following are the avaibles operaitons
URL | Description | Example
--- | --- | ---
**/get** | This is used to get value of the key, 'name' | */get/name*
**/set** | This is used to set the value, 'kaushik' for the key, 'name' | */set/name/kaushik*
**/keys** | This is used to get all the keys in Redis | */keys*
**/keys/<pattern>** | This is used to get all the keys in Redis with the given pattern | */keys/na**
