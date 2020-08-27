package com.websocket.service;

import com.core.base.controller.BaseController;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @GetMapping("/test")
    public Object sayHello() {
        //将验证码存入redis
        return toJsonOk("okokok");

    }


}
