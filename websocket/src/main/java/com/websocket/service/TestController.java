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


    //1. 三个同样的字母连在一起，一定是拼写错误，去掉一个的就好啦：比如 helllo -> hello
    //2. 两对一样的字母（AABB型）连在一起，一定是拼写错误，去掉第二对的一个字母就好啦：比如 helloo -> hello
    //3. 上面的规则优先“从左到右”匹配，即如果是AABBCC，虽然AABB和BBCC都是错误拼写，应该优先考虑修复AABB，结果为AABCC
    public static void main(String[] args) {

    }


}
