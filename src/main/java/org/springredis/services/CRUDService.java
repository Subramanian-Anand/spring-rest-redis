package org.springredis.services;

import java.util.Set;

public interface CRUDService {
    String getKeyValue(String redisKey) throws RedisConnectionFailure;

    boolean setKeyValue(String redisKey, String redisValue) throws RedisConnectionFailure;

    boolean deleteKeyValue(String redisKey) throws RedisConnectionFailure;

    boolean containsKeyValue(String redisKey) throws RedisConnectionFailure;

    Set<String> listKeys(String pattern) throws RedisConnectionFailure;
}
