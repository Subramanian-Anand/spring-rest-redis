package org.springredis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties {
    @Value("${isRestricted:false}")
    private boolean restricted;

    @Value("${redisKeyPattern:*key}")
    private String redisKeyPattern;

    @Value("${tableConfig:{\"RedisKey\": \"rediskey\", \"RedisValue\": \"redisvalue\"}}")
    private String tableConfig;
}
