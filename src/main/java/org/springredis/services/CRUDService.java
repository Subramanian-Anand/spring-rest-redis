package org.springredis.services;

public interface CRUDService {
    String getKeyValue(String redisKey);
    boolean putKeyValue(String redisKey, String redisValue);
    boolean deleteKeyValue(String redisKey);
    boolean containsKeyValue(String redisKey);
}
