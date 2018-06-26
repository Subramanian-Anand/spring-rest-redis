package org.springredis.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedisData {
    private String rediskey;
    private Object redisvalue;
}
