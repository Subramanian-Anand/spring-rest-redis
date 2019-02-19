package org.springredis.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RedisKeyValidator.class)
@Documented
public @interface RedisKey {

    String message() default "RedisKey pattern is invalid";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}