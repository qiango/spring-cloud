package com.zuul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class TestZuulController {

    @Value("${server.port}")
    private String port;

    public String sayHello(){
       return "my is customer"+port;
    }

}
