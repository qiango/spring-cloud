package com.ribbonconsumer.controller;

import com.core.base.controller.BaseController;
import com.core.base.util.FileUtil;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.config.ConfigModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 * @author qian.wang
 * @description
 * @date 2019/1/25
 */
@RestController
@RequestMapping("/file")
public class FileDownLoadController extends BaseController {

    @GetMapping(value = "/readFile/{filePath}")
    public void downLoad(HttpServletResponse response, @PathVariable String filePath) throws IOException {
        String fileName = FileUtil.setFileName(ConfigModel.BASEFILEPATH, FileUtil.getFileName(filePath));
        log.info("文件访问路径为：" + fileName);
        File f = new File(fileName);
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
