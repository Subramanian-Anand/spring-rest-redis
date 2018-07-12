package org.springredis.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springredis.beans.RedisData;
import org.springredis.services.CRUDService;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ReactiveController {

    @Autowired
    private CRUDService crudService;

    @ApiOperation("This is used to get a reactive SSE response for the mentioned rediskey every mentioned seconds")
    @GetMapping(value = "/reactiveget/{rediskey}/{durationinsec}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @CrossOrigin
    public Flux<RedisData> getStream(@PathVariable("rediskey") String rediskey, @PathVariable("durationinsec") int durationInSec) {

        return Flux.interval(Duration.ofSeconds(durationInSec))
                .flatMap(item -> Flux.just(new RedisData(rediskey, this.crudService.getKeyValue(rediskey))));
    }

    @ApiOperation("This is used to get a reactive SSE response for the mentioned rediskey every mentioned seconds")
    @GetMapping(value = "/reactivegetSSE/{rediskey}/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @CrossOrigin
    public Flux<RedisData> getStreamWithoutDuration(@PathVariable("rediskey") String rediskey) {

        return Flux.just(new RedisData(rediskey, this.crudService.getKeyValue(rediskey)));
    }


    @ApiOperation("This is used to get a reactive SSE response for the mentioned rediskey every mentioned seconds")
    @GetMapping(value = "/reactivegets/{pattern}/{durationinsec}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @CrossOrigin
    public Flux<Object> getStreams(@PathVariable("pattern") String pattern, @PathVariable("durationinsec") int durationInSec) {
        final List<RedisData> values = new ArrayList<>();

        return Flux.interval(Duration.ofSeconds(durationInSec))
                .flatMap(tik -> Flux.just(this.getValues(pattern)));

    }


    private List<RedisData> getValues(String pattern) {
        Set<String> keys = this.crudService.listKeys(pattern);
        final List<RedisData> values = new ArrayList<>();
        keys.forEach(rediskey -> {
            values.add(new RedisData(rediskey, this.crudService.getKeyValue(rediskey)));
        });

        return values;
    }
}
