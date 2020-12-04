package com.ribbonconsumer.controller.leyile.mini;

import com.core.base.controller.BaseController;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.config.swagger.DocVer;
import com.ribbonconsumer.service.leyile.LeService;
import com.ribbonconsumer.thirdparty.mq.MsgProducer;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {DocVer.Z9.MAUSER.USER.KEY})
@RestController
@RequestMapping("/user")
public class LeController extends BaseController {

    private LeService leService;
    private MsgProducer msgProducer;

    @Autowired
    public LeController(LeService leService,MsgProducer msgProducer) {
        this.leService = leService;
        this.msgProducer = msgProducer;
    }

//
    @PostMapping("/get")
    public Object exceptionTest(@RequestParam Map<String, Object> params) {
        int code = ModelUtil.getInt(params, "a");
        if (code == 0) {
            return toError("参数错误");
        }
        msgProducer.sendMsg("测试单发");
        msgProducer.sendAll("测试多发");
        return toJsonOk("");
    }

    @ApiOperation(value = "关注取关")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "focusUserId", value = "被关注取关用户id", defaultValue = "20", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "1取关，2关注", defaultValue = "20", dataType = "String"),
    })
    @PostMapping("/focusUser")
    public Object focusUser(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        long focusUserId = ModelUtil.getLong(params, "focusUserId");
        int type = ModelUtil.getInt(params, "type");
        if (userid == 0 || focusUserId == 0) {
            return toError("参数错误");
        }
        leService.focusUser(userid, focusUserId, type);
        return toJsonOk("success");
    }

    @ApiOperation(value = "点赞取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "contentId", value = "被点赞作品id", defaultValue = "20", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "1取消点赞，2点赞", defaultValue = "20", dataType = "String"),
    })
    @PostMapping("/praise")
    public Object praise(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        long contentId = ModelUtil.getLong(params, "contentId");
        int type = ModelUtil.getInt(params, "type");
        if (userid == 0 || contentId == 0) {
            return toError("参数错误");
        }
        leService.praise(userid, contentId, type);
        return toJsonOk(true);
    }

    @ApiOperation(value = "评价")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id",  dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "contentId", value = "被评价作品id",  dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pid", value = "评价父id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "评价内容", dataType = "String"),
    })
    @PostMapping("/insertEvaluation")
    public Object insertEvaluation(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userId");
        long contentId = ModelUtil.getLong(params, "contentId");
        long pid = ModelUtil.getLong(params, "pid");
        String content = ModelUtil.getStr(params, "content");
        if (userid == 0 || contentId == 0) {
            return toError("参数错误");
        }
        leService.insertEvaluation(userid, contentId, content, pid);
        return toJsonOk("success");
    }

    @ApiOperation(value = "点赞评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "evaluationId", value = "被点赞评价id", defaultValue = "20", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "classifyId", value = "作品分类id", defaultValue = "20", dataType = "String"),
    })
    @PostMapping("/updateEvaluation")
    public Object updateEvaluation(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long evaluationId = ModelUtil.getLong(params, "evaluationId");
        long userid = ModelUtil.getLong(params, "userId");
        long classifyId = ModelUtil.getLong(params, "classifyId");
        if (evaluationId == 0) {
            return toError("参数错误");
        }
        leService.updateEvaluation(evaluationId,classifyId,userid);
        return toJsonOk("success");
    }


}
