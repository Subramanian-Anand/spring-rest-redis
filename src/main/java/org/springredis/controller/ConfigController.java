package org.springredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springredis.config.AppProperties;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Config Controller", description = "This allows to pass application properties to client side")
public class ConfigController {

    @Autowired
    private AppProperties appProps;

    @ApiOperation("This will get the application properties and send to client side")
    @GetMapping(value = "/getAppConfig")
    public AppProperties getAppConfig() {
        return this.appProps;
    }
}