package org.springredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springredis.services.CRUDService;

import java.util.Set;

@RestController
public class CRUDController {

    @Autowired
    CRUDService crudService;

    @RequestMapping(value = "/getkeyvalue/{rediskey}")
    public String getKeyValue(@PathVariable("rediskey") String redisKey) {
        return this.crudService.getKeyValue(redisKey);
    }

    @RequestMapping(value = "/setkeyvalue/{rediskey}/{redisvalue}")
    public boolean setKeyValue(@PathVariable("rediskey") String redisKey, @PathVariable("redisvalue") String redisValue) {
        return this.crudService.setKeyValue(redisKey, redisValue);
    }

    @RequestMapping(value = "/listkeys")
    public Set<String> listKeysDefault() {
        final String pattern = "*";
        return this.crudService.listKeys(pattern);
    }

    @RequestMapping(value = "/listkeys/{pattern}")
    public Set<String> listKeysPattern(@PathVariable("pattern") String pattern) {
        return this.crudService.listKeys(pattern);
    }
}
