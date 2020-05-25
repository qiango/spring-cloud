package com.cloud.ribbon.controller;

import com.cloud.ribbon.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/hello")
    public String sayHello(){
        return testService.sayHello();
    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public String getUser(Long id){
        return testService.getUser(id);
    }

}
