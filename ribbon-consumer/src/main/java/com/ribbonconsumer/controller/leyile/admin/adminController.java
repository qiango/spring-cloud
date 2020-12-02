package com.ribbonconsumer.controller.leyile.admin;

import com.core.base.controller.BaseController;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.service.leyile.LeService;
import com.ribbonconsumer.thirdparty.mq.MsgProducer;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/User")
public class adminController extends BaseController {

    private LeService leService;
    private MsgProducer msgProducer;

    @Autowired
    public adminController(LeService leService, MsgProducer msgProducer) {
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

    @PostMapping("/focusUser")
    public Object focusUser(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userid");
        long focusUserId = ModelUtil.getLong(params, "focusUserId");
        int type = ModelUtil.getInt(params, "type");
        if (userid == 0 || focusUserId == 0) {
            return toError("参数错误");
        }
        leService.focusUser(userid, focusUserId, type);
        return toJsonOk("success");
    }

    @PostMapping("/praise")
    public Object praise(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userid");
        long contentId = ModelUtil.getLong(params, "contentId");
        int type = ModelUtil.getInt(params, "type");
        if (userid == 0 || contentId == 0) {
            return toError("参数错误");
        }
        leService.praise(userid, contentId, type);
        return toJsonOk("success");
    }

    @PostMapping("/insertEvaluation")
    public Object insertEvaluation(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long userid = ModelUtil.getLong(params, "userid");
        long contentId = ModelUtil.getLong(params, "contentId");
        long pid = ModelUtil.getLong(params, "pid");
        int type = ModelUtil.getInt(params, "type");
        String content = ModelUtil.getStr(params, "content");
        if (userid == 0 || contentId == 0) {
            return toError("参数错误");
        }
        leService.insertEvaluation(userid, contentId, content, pid);
        return toJsonOk("success");
    }

    @PostMapping("/updateEvaluation")
    public Object updateEvaluation(@ApiParam(hidden = true) @RequestParam Map<String, Object> params) {
        long evaluationId = ModelUtil.getLong(params, "evaluationId");
        long userid = ModelUtil.getLong(params, "userid");
        long classifyId = ModelUtil.getLong(params, "classifyId");
        if (evaluationId == 0) {
            return toError("参数错误");
        }
        leService.updateEvaluation(evaluationId,classifyId,userid);
        return toJsonOk("success");
    }


}
