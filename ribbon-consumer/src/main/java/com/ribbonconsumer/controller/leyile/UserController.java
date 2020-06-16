package com.ribbonconsumer.controller.leyile;

import com.core.base.controller.BaseController;
import com.core.base.util.ModelUtil;
import com.core.base.util.StrUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ribbonconsumer.service.leyile.ProductService;
import com.ribbonconsumer.service.leyile.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Ma/User")
public class UserController extends BaseController {


    @ApolloConfig
    private Config config;

    @Value("${server.port}")
    private String port;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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


    @ApiOperation(value = "code换取openid")
    @PostMapping("/getUserOpen")
    public Object getUserOpen(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        String code = ModelUtil.getStr(params, "code");
        if (StrUtil.isEmpty(code)) {
            toError("code为空");
        }
        return toJsonOk(userService.getUserOpen(code));
    }

    @ApiOperation(value = "根据获取到用户信息去注册或登陆")
    @PostMapping("/getUserInfo")
    public Object getUserInfo(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long contentId = ModelUtil.getLong(params, "contentId");
        if (contentId == 0) {
            toError("contentId为空");
        }
        String sessionKey = ModelUtil.getStr(params, "sessionKey");
        String encryptedData = ModelUtil.getStr(params, "encryptedData");
        String ivStr = ModelUtil.getStr(params, "ivStr");
        return toJsonOk(userService.getUserInfo(sessionKey, encryptedData, ivStr));
    }

    @ApiOperation(value = "我的")
    @PostMapping("/getUserInfoMation")
    public Object getUserInfoMation(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        if (userId == 0) {
            toError("userId为空");
        }
        return toJsonOk(userService.getUserInfoMation(userId));
    }

    @ApiOperation(value = "我的")
    @PostMapping("/myFocusList")
    public Object myFocusList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        if (userId == 0) {
            toError("userId为空");
        }
        return toJsonOk(userService.myFocusList(userId));
    }

    @ApiOperation(value = "我的")
    @PostMapping("/myFanList")
    public Object myFanList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        if (userId == 0) {
            toError("userId为空");
        }
        return toJsonOk(userService.myFanList(userId));
    }

    @ApiOperation(value = "我的")
    @PostMapping("/updateIntroduce")
    public Object updateIntroduce(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        String headpic = ModelUtil.getStr(params, "headpic");
        String name = ModelUtil.getStr(params, "name");
        String introduce = ModelUtil.getStr(params, "introduce");
        if (userId == 0) {
            toError("userId为空");
        }
        userService.updateIntroduce(userId, headpic, name, introduce);
        return toJsonOk("");
    }

}