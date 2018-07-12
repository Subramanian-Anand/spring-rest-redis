package org.springredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springredis.controller.RedisSubscriber;

@Configuration
@EnableRedisRepositories
class AppConfig {

    @Bean
    JedisConnectionFactory connectionFactory(RedisConfig redisConfig) {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(redisConfig.getHost());
//        redisStandaloneConfiguration.setPort(redisConfig.getPort());
//        redisStandaloneConfiguration.setDatabase(redisConfig.getDb());
//        RedisPassword redisPassword = RedisPassword.of(redisConfig.getPass());
//        redisStandaloneConfiguration.setPassword(redisPassword);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(redisConfig.getHost());
        connectionFactory.setPort(redisConfig.getPort());
        connectionFactory.setDatabase(redisConfig.getDb());
        connectionFactory.setPassword(redisConfig.getPass());

        connectionFactory.setTimeout(redisConfig.getTimeout());
        connectionFactory.setUsePool(true);

//        connectionFactory.getPoolConfig().setMaxIdle(30);
//        connectionFactory.getPoolConfig().setMinIdle(10);
        return connectionFactory;
    }

    @Bean
    RedisTemplate<?, ?> redisTemplate(JedisConnectionFactory connectionFactory) {

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);

        template.setExposeConnection(true);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    MessageListenerAdapter messageListener(RedisSubscriber redisSubscriber) {
        return new MessageListenerAdapter(redisSubscriber);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        return container;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("*").allowedOrigins("*");
            }
        };
    }
}