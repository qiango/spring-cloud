package com.ribbonconsumer.controller;

import com.ribbonconsumer.base.controller.BaseController;
import com.ribbonconsumer.base.util.ModelUtil;
import com.ribbonconsumer.service.HomePageService;
import com.ribbonconsumer.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Api(value = "/Admin/Article swagger测试")
@RestController
@RequestMapping("/qian")
public class TestController extends BaseController {

    @Autowired
    private TestService testService;
    @Autowired
    protected RedisTemplate redisTemplate;
    @Autowired
    private HomePageService homePageService;


    @GetMapping("/getCustomer")
    public Object sayHello() {
        //将验证码存入redis
        this.redisTemplate.opsForValue().set("d", "");
        return toJsonOk(testService.sayHello() + "......");

    }

    @GetMapping("/getDemo")
    public Object sayDemo() {
//        return toError("参数错误");
        return "";
    }

    @PostMapping("/get")
    public Object exceptionTest() {
        return toJsonOk(testService.getTest());
    }

    @GetMapping("/nullTest")
    public Object nullTest() {
        return toJsonOk(testService.nullTest());
    }


    @ApiOperation(value = "获取实例")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", dataType = "String")
    })
    @PostMapping("/getFinalCount")
    public Map<String, Object> getFinalCount(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        long startTime=ModelUtil.getLong(params,"startTime");
        long endTime= ModelUtil.getLong(params,"endTime");
        result.put("data", homePageService.getFinalCount(startTime, endTime));
        setOkResult(result, "成功");
        return result;
    }


}
