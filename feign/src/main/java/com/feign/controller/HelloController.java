package com.feign.controller;

import com.feign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Autowired
    private HelloService testService;

    @RequestMapping("/hello")
    public String sayHello(){
        return testService.sayHello();
    }
}
