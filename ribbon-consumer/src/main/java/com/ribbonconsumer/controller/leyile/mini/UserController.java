package com.ribbonconsumer.controller.leyile.mini;

import com.core.base.controller.BaseController;
import com.core.base.util.ModelUtil;
import com.core.base.util.StrUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ribbonconsumer.config.swagger.DocVer;
import com.ribbonconsumer.service.leyile.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = {DocVer.Z9.MAUSER.USER.KEY})
@RestController
@RequestMapping("/info/user")
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
    @ApiOperation(value = "测试接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "开始页", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", defaultValue = "20", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "[\n" +
                    "        {\n" +
                    "            \"id\": 20,\n" +
                    "            \"hosp_id\": 29714,\n" +
                    "            \"name|名称\": \"上海红房子医院\",\n" +
                    "            \"address|地址\": \"上海市沈阳路128号\",\n" +
                    "            \"phone|电话\": \"33189902\",\n" +
                    "            \"introduce|介绍\": \"全国妇产科医院排名No.5\",\n" +
                    "            \"picture_small|图片\": \"sy20190320124526450772.png\",\n" +
                    "            \"level|等级\": \"三级甲等\",\n" +
                    "            \"yardName|院区\": \"杨浦院区\"\n" +
                    "        }\n" +
                    "    ]")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "微信code", dataType = "String"),
    })
    @PostMapping("/getUserOpen")
    public Object getUserOpen(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        String code = ModelUtil.getStr(params, "code");
        if (StrUtil.isEmpty(code)) {
            toError("code为空");
        }
        return toJsonOk(userService.getUserOpen(code));
    }

    @ApiOperation(value = "根据获取到用户信息去注册或登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sessionKey", value = "sessionKey", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "encryptedData", value = "encryptedData", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "ivStr", value = "ivStr", dataType = "String"),
    })
    @PostMapping("/getUserInfo")
    public Object getUserInfo(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        String sessionKey = ModelUtil.getStr(params, "sessionKey");
        String encryptedData = ModelUtil.getStr(params, "encryptedData");
        String ivStr = ModelUtil.getStr(params, "ivStr");
        return toJsonOk(userService.getUserInfo(sessionKey, encryptedData, ivStr));
    }

    @ApiOperation(value = "我的")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
    })
    @PostMapping("/getUserInfoMation")
    public Object getUserInfoMation(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        if (userId == 0) {
            toError("userId为空");
        }
        return toJsonOk(userService.getUserInfoMation(userId));
    }

    @ApiOperation(value = "我的关注")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
    })
    @PostMapping("/myFocusList")
    public Object myFocusList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        if (userId == 0) {
            toError("userId为空");
        }
        return toJsonOk(userService.myFocusList(userId));
    }

    @ApiOperation(value = "我的粉丝")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
    })
    @PostMapping("/myFanList")
    public Object myFanList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        if (userId == 0) {
            toError("userId为空");
        }
        return toJsonOk(userService.myFanList(userId));
    }

    @ApiOperation(value = "更新自我介绍")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "headpic", value = "头像", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "introduce", value = "介绍", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "birthDay", value = "生日", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "gender", value = "性别1男2女", dataType = "String"),
    })
    @PostMapping("/updateIntroduce")
    public Object updateIntroduce(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        String headpic = ModelUtil.getStr(params, "headpic");
        String name = ModelUtil.getStr(params, "name");
        String introduce = ModelUtil.getStr(params, "introduce");
        long birthDay = ModelUtil.getLong(params, "birthDay");
        int gender = ModelUtil.getInt(params, "gender");
        if (userId == 0) {
            toError("userId为空");
        }
        userService.updateIntroduce(userId, headpic, name, introduce,birthDay,gender);
        return toJsonOk("");
    }


    @ApiOperation(value = "发消息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "sessionId", value = "会话id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "说话内容", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "userList", value = "聊天对象id", dataType = "String"),
    })
    @PostMapping("/sendMessage")
    public Object sendMessage(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        long sessionId = ModelUtil.getLong(params, "sessionId");//会话id
        String content = ModelUtil.getStr(params, "content");
        List<?> userList = ModelUtil.getList(params, "userList", new ArrayList<>());//聊天对象id
        //若有sessionID，则不需userlist
        if (userId == 0) {
            toError("userId为空");
        }
        userService.sendMessage(sessionId, content, userId, userList);
        return toJsonOk("success");
    }


    @ApiOperation(value = "会话列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
    })
    @PostMapping("/getSessionList")
    public Object getSessionList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userId = ModelUtil.getLong(params, "userId");
        if (userId == 0) {
            toError("userId为空");
        }
        return toJsonOk(userService.getSessionList(userId));
    }

    @ApiOperation(value = "聊天记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "sessionId", value = "会话id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageIndex", value = "页码", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "String"),
    })
    @PostMapping("/getContentList")
    public Object getContentList(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long sessionId = ModelUtil.getLong(params, "sessionId");//会话id
        long userId = ModelUtil.getLong(params, "userId");
        int pageIndex = ModelUtil.getInt(params, "pageIndex", 1);
        int pageSize = ModelUtil.getInt(params, "pageSize", 20);
        if (userId == 0) {
            toError("userId为空");
        }
        return toJsonOk(userService.getContentList(sessionId, userId, pageSize, pageIndex));
    }


}
