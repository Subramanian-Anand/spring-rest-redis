package org.springredis.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springredis.beans.RedisData;
import org.springredis.services.CRUDService;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ReactiveController {

    @Autowired
    private CRUDService crudService;

    @ApiOperation("This is used to get a reactive SSE response for the mentioned rediskey every mentioned seconds")
    @GetMapping(value = "/reactiveget/{rediskey}/{durationinsec}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<RedisData> getStream(@PathVariable("rediskey") String rediskey, @PathVariable("durationinsec") int durationInSec) {

        return Flux.interval(Duration.ofSeconds(durationInSec))
                .flatMap(item -> Flux.just(new RedisData(rediskey, this.crudService.getKeyValue(rediskey))));
    }
}
