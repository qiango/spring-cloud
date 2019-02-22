package com.ribbonconsumer.controller;

import com.ribbonconsumer.base.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

/**
 * @author qian.wang
 * @description
 * @date 2019/1/25
 */
public class FileDownLoadController extends BaseController {


    @GetMapping(value = "/testPaths")
//    @RequestMapping(method = RequestMethod.GET, value = "/testPaths/{filePath:.+}")
    public void downLoad(HttpServletResponse response) throws IOException {
//        String filePath="D:\\home\\qwq\\file\\apk\\17411d4db5cbcd6a3209cb6415a5a6778de81cf2.apk";
        String filePath = "D:\\logs\\bzz_server_java.2018-09-14.log";
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;
        response.reset(); // 非常重要
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
        if ("jpg".equals(suffix) || "png".equals(suffix)) { // 在线打开方式//若是jpg，png格式的文件则是打开，否则都是直接下载文件到本地
            URL u = new URL("file:///" + filePath);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
            // 文件名应该编码成UTF-8
        } else { // 纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }


}
