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
        try {
            return this.ops.get(redisKey);
        } catch (Exception e) {
            throw new RedisConnectionFailure("Redis Connection Failure");
        }
    }

    @Override
    public boolean setKeyValue(String redisKey, String redisValue) throws RedisConnectionFailure {
        try {
            this.ops.set(redisKey, redisValue);
            return true;
        } catch (Exception e) {
            throw new RedisConnectionFailure("Redis Connection Failure");
        }
    }

    @Override
    public boolean deleteKeyValue(String redisKey) throws RedisConnectionFailure {
        try {
            return this.template.delete(redisKey);
        } catch (Exception e) {
            throw new RedisConnectionFailure("Redis Connection Failure");
        }
    }

    @Override
    public boolean containsKeyValue(String redisKey) throws RedisConnectionFailure {
        try {
            return this.template.hasKey(redisKey);
        } catch (Exception e) {
            throw new RedisConnectionFailure("Redis Connection Failure");
        }
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
