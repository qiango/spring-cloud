package com.feign.controller;

import com.core.base.controller.BaseController;
import com.core.base.exception.QianException;
import com.core.base.util.ValueUtil;
import com.feign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class HelloController extends BaseController {

    private final HelloService testService;

    @Autowired
    public HelloController(HelloService testService) {
        this.testService = testService;
    }

    @RequestMapping("/hello")
    public Object sayHello() {
        return testService.sayHello();
    }


    @PostMapping("/verifyCode")
    public void createVerifyCode(HttpServletResponse response) {//登录验证码
        testService.create(response);
    }
}
