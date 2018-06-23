package org.springredis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class RedisConfig {
    @Value("${redis.host:localhost}")
    private String host;
    @Value("${redis.port:{6379}}")
    private int port;
    @Value("${redis.db:{0}}")
    private int db;
    @Value("${redis.password:}")
    private String pass;
    @Value("${redis.timeout:{10000}}")
    private int timeout;
}
