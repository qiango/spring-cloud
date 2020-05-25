package com.ribbonconsumer.controller.smsProject;

import com.core.base.controller.BaseController;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User/Login")
public class UserController extends BaseController {


    @ApolloConfig
    private Config config;

    @GetMapping("/hello")
    public String hello() {
        System.out.println("线程池名称: " + Thread.currentThread().getName());
        System.out.println("时间为: " + config.getProperty("time", "default"));
        return config.getProperty("port", "default");
    }

}
