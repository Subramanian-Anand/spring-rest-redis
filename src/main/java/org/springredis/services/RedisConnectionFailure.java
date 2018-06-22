package org.springredis.services;

import io.lettuce.core.RedisConnectionException;

public class RedisConnectionFailure extends RedisConnectionException {
    public RedisConnectionFailure(String msg) {
        super(msg);
    }

    public RedisConnectionFailure(String msg, Throwable cause) {
        super(msg, cause);
    }
}
