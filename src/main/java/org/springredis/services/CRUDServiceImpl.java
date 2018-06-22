package org.springredis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class CRUDServiceImpl implements CRUDService {
    @Autowired
    private RedisTemplate<String, String> template;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> ops;

    @Override
    public String getKeyValue(String redisKey) throws RedisConnectionFailure {
        return this.ops.get(redisKey);
    }

    @Override
    public boolean setKeyValue(String redisKey, String redisValue) throws RedisConnectionFailure {
        this.ops.set(redisKey, redisValue);
        return true;
    }

    @Override
    public boolean deleteKeyValue(String redisKey) throws RedisConnectionFailure {
        return this.template.delete(redisKey);
    }

    @Override
    public boolean containsKeyValue(String redisKey) throws RedisConnectionFailure {
        return false;
    }

    @Override
    public Set<String> listKeys(String pattern) throws RedisConnectionFailure {
        try {
            return this.template.keys(pattern);
        } catch (Exception e) {
            throw new RedisConnectionFailure("Redis Connection Failure");
        }
    }
}
