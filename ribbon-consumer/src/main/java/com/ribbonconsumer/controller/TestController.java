package com.ribbonconsumer.controller;

import com.core.base.controller.BaseController;
import com.core.base.exception.QianException;
import com.core.base.util.ModelUtil;
import com.core.base.util.RedisUtil;
import com.ribbonconsumer.service.HomePageService;
import com.ribbonconsumer.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    protected RedisUtil redisUtil;
    @Autowired
    private HomePageService homePageService;


    @GetMapping("/getCustomer")
    public Object sayHello() {
        //将验证码存入redis
        log.info("redis:" + redisUtil.get("kkk"));
        return toJsonOk(testService.sayHello() + "......");

    }


    @GetMapping("/getDemo")
    public void sayDemo() {
        toError("参数错误");
    }

    @PostMapping("/get")
    public Object exceptionTest(@RequestParam Map<String, Object> params) {
//        try {
        int code = ModelUtil.getInt(params, "a");
        if (code == 0) {
            return toError("参数错误");
        }
        return toJsonOk(testService.getTest());
//        } catch (QianException e) {
//            return toError(e.getMessage());
//        }
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
        long startTime = ModelUtil.getLong(params, "startTime");
        long endTime = ModelUtil.getLong(params, "endTime");
        result.put("data", homePageService.getFinalCount(startTime, endTime));
        setOkResult(result, "成功");
        return result;
    }

    @PostMapping("/activeMq")
    public Object activeMq(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        String queneName = ModelUtil.getStr(params, "queneName");
        String message = ModelUtil.getStr(params, "message");
        testService.sendMessage(queneName, message);
        return toJsonOk("success");
    }

    @PostMapping("/createOrder")
    public Object createOrder(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        try {
            long queneName = ModelUtil.getLong(params, "userid");
            int message = ModelUtil.getInt(params, "num");
            testService.createOrder(queneName);
            return toJsonOk("success");
        } catch (QianException e) {
            return toError(e.getMessage());
        }
    }

    @PostMapping("/createOrderMq")
    public Object createOrderMq(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long queneName = ModelUtil.getLong(params, "userid");
        int message = ModelUtil.getInt(params, "num");
        testService.sendMessageOrder("testorder", queneName);
        return toJsonOk("success");
    }


}
