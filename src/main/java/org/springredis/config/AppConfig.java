package org.springredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableRedisRepositories
class AppConfig {

    @Bean
    RedisConnectionFactory connectionFactory(RedisConfig redisConfig) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisConfig.getHost());
        redisStandaloneConfiguration.setPort(redisConfig.getPort());
        redisStandaloneConfiguration.setDatabase(redisConfig.getDb());
        RedisPassword redisPassword = RedisPassword.of(redisConfig.getPass());
        redisStandaloneConfiguration.setPassword(redisPassword);

        RedisConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        ((LettuceConnectionFactory) connectionFactory).setTimeout(redisConfig.getTimeout());
        return connectionFactory;
    }

    @Bean
    RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);

        return template;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}