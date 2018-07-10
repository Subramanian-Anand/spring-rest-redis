package org.springredis.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import org.springredis.services.CRUDService;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@Log4j2
public class GreetingController {

    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;
    @Autowired
    private MessageListenerAdapter messageListenerAdapter;
    @Autowired
    private CRUDService crudService;

    @MessageMapping("/hello/{rediskey}")
    @SendTo("/keySubscription/{rediskey}")
    public String greeting(@DestinationVariable("rediskey") String redisKey) throws Exception {
        log.info("RedisKey --> " + redisKey);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new ChannelTopic("__keyspace@0__:"+ redisKey));
        log.info("RedisValue --> " + crudService.getKeyValue(redisKey));
        return crudService.getKeyValue(redisKey);
    }
}
