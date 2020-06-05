package com.ribbonconsumer.controller.leyile;

import com.core.base.controller.BaseController;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.service.leyile.LeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/User/Login")
public class LeController extends BaseController {

    private  LeService leService;

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
}
