package org.springredis.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springredis.config.AppProperties;

class RedisKeyValidator implements ConstraintValidator<RedisKey, String> {

    Logger logger = LoggerFactory.getLogger(RedisKeyValidator.class);

    @Autowired
    private AppProperties appProps;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = true;
        if (this.appProps.isRestricted()) {
            if(value.trim().isEmpty())
                return false;
            result = !value.trim().equals("*");
        }
        return result;
    }
}