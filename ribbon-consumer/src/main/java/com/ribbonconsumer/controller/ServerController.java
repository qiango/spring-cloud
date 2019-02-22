package com.ribbonconsumer.controller;

import com.ribbonconsumer.base.controller.BaseController;
import com.ribbonconsumer.base.domain.Server;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/server")
public class ServerController extends BaseController {
    private String prefix = "monitor/server";

    @GetMapping("/getInfo")
    public Object server(ModelMap mmap) throws Exception {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        return toJsonOk(mmap);
    }
}
