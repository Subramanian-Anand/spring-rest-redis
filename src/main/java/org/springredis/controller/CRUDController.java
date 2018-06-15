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

    @RequestMapping("/")
    public String index() {
        String response = "Redis OPS";
        response += "\n\n\r1./get/rediskey\n2./set/rediskey\n3./delete/rediskey\n4.keys\n5.keys/<pattern>";

        return response;
    }

    @RequestMapping(value = "/get/{rediskey}")
    public String getKeyValue(@PathVariable("rediskey") String redisKey) {
        return this.crudService.getKeyValue(redisKey);
    }

    @RequestMapping(value = "/set/{rediskey}/{redisvalue}")
    public boolean setKeyValue(@PathVariable("rediskey") String redisKey, @PathVariable("redisvalue") String redisValue) {
        return this.crudService.setKeyValue(redisKey, redisValue);
    }

    @RequestMapping(value = "/delete/{rediskey}")
    public boolean deleteKey(@PathVariable("rediskey") String redisKey) {
        return this.crudService.deleteKeyValue(redisKey);
    }

    @RequestMapping(value = "/keys")
    public Set<String> listKeysDefault() {
        final String pattern = "*";
        return this.crudService.listKeys(pattern);
    }

    @RequestMapping(value = "/keys/{pattern}")
    public Set<String> listKeysPattern(@PathVariable("pattern") String pattern) {
        return this.crudService.listKeys(pattern);
    }
}
