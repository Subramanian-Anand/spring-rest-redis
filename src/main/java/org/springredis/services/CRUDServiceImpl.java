package org.springredis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CRUDServiceImpl implements CRUDService {
    @Autowired
    private RedisTemplate<String, String> template;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> ops;

    @Override
    public String getKeyValue(String redisKey) {
        return this.ops.get(redisKey);
    }

    @Override
    public boolean putKeyValue(String redisKey, String redisValue) {
        this.ops.set(redisKey, redisValue);
        return true;
    }

    @Override
    public boolean deleteKeyValue(String redisKey) {
        return false;
    }

    @Override
    public boolean containsKeyValue(String redisKey) {
        return false;
    }
}
