package com.ribbonconsumer.controller.leyile;

import com.core.base.controller.BaseController;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Ma/User")
public class UserController extends BaseController {


    @ApolloConfig
    private Config config;

    @Value("${server.port}")
    private String port;

    //阿波罗配置中心获取配置文件
    @GetMapping("/hello")
    public String hello() {
        System.out.println("线程池名称: " + Thread.currentThread().getName());
        System.out.println("时间为: " + config.getProperty("time", "default"));
        return config.getProperty("port", "default");
    }

    @GetMapping("/test")
    public String test() {
        return "此次访问的port:" + port;
    }


}
