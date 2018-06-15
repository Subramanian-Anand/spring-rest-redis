package org.springredis.services;

import java.util.Set;

public interface CRUDService {
    String getKeyValue(String redisKey);

    boolean setKeyValue(String redisKey, String redisValue);

    boolean deleteKeyValue(String redisKey);

    boolean containsKeyValue(String redisKey);

    Set<String> listKeys(String pattern);
}
