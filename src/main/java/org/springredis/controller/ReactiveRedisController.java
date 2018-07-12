package org.springredis.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springredis.services.CRUDService;

@Controller
@Log4j2
public class ReactiveRedisController {

    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;
    @Autowired
    private MessageListenerAdapter messageListenerAdapter;
    @Autowired
    private CRUDService crudService;

    @MessageMapping("/initSubscription/{rediskey}")
    @SendTo("/keySubscription/{rediskey}")
    @CrossOrigin
    public String greeting(@DestinationVariable("rediskey") String redisKey) throws Exception {
        log.info("RedisKey --> " + redisKey);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new ChannelTopic("__keyspace@0__:"+ redisKey));
        log.info("RedisValue --> " + crudService.getKeyValue(redisKey));

        String redisValue = crudService.getKeyValue(redisKey);
        String payload = "{\"rediskey\":\""+redisKey+"\",\"redisvalue\":"+redisValue+"}";
        return payload;
    }
}
