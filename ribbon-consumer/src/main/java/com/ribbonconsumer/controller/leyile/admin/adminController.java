package com.ribbonconsumer.controller.leyile.admin;

import com.core.base.controller.BaseController;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.service.leyile.AdminService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class adminController extends BaseController {

    private AdminService adminService;

    @Autowired
    public adminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getClassifyList")
    public Object exceptionTest() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", adminService.getClassifyList());
        return toJsonOk(result);
    }

    @GetMapping("/getContentList")
    public Object getContentList() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", adminService.getContentList());
        return toJsonOk(result);
    }

    @GetMapping("/getUserList")
    public Object getUserList() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", adminService.getUserList());
        return toJsonOk(result);
    }
}
