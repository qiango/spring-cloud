package com.ribbonconsumer.controller.leyile;

import com.core.base.controller.BaseController;
import com.core.base.domain.Server;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.service.leyile.LeService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/User/Login")
public class LeController extends BaseController {

    private LeService leService;

    @Autowired
    public LeController(LeService leService) {
        this.leService = leService;
    }


    @PostMapping("/get")
    public Object exceptionTest(@RequestParam Map<String, Object> params) {
        int code = ModelUtil.getInt(params, "a");
        if (code == 0) {
            return toError("参数错误");
        }
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
        if (evaluationId == 0) {
            return toError("参数错误");
        }
        leService.updateEvaluation(evaluationId);
        return toJsonOk("success");
    }


}
