package org.springredis.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springredis.services.RedisConnectionFailure;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(RedisConnectionFailure.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Connection to Redis Failed")
    public void handleRedisConnctionError(Exception e) {
        GlobalExceptionHandler.log.error("Redis Connection Error", e);
    }
}
