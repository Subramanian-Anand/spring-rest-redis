package org.springredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springredis.services.CRUDService;

@RestController
public class CRUDController {

    @Autowired
    CRUDService crudService;

    @RequestMapping(value = "/getkeyvalue/{rediskey}")
    public String getKeyValue(@PathVariable("rediskey") String redisKey) {
        return this.crudService.getKeyValue(redisKey);
    }

    @RequestMapping(value = "/setkeyvalue/{rediskey}/{redisvalue}")
    public boolean putKeyValue(@PathVariable("rediskey") String redisKey, @PathVariable("redisvalue") String redisValue) {
        return this.crudService.putKeyValue(redisKey, redisValue);
    }
}
