package org.springredis.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springredis.services.CRUDService;

import java.util.Arrays;

@Service
@Log4j2
public class RedisSubscriber implements MessageListener {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private CRUDService crudService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String command = new String(message.getBody());
        String patternStr = new String(pattern);

        log.debug(channel + " " + command + " " + patternStr);

        if(command.equals("set")){
            String redisKey = channel.split(":")[1];
            String redisValue = crudService.getKeyValue(redisKey);
            template.convertAndSend("/keySubscription/" + redisKey, redisValue);
        }
    }
}
