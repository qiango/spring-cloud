package com.feign.service;

import com.core.base.exception.QianException;
import com.feign.rpc.GetHello;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Service
public class HelloService {

    private final GetHello getHello; //注入rpc

    @Autowired
    public HelloService(GetHello getHello) {
        this.getHello = getHello;
    }

    public String sayHello() {
        int i = 1 / 0;
        throw new QianException("抛错测试啦");
//        return getHello.sayHello(); // 提供一个hello World
    }

    public void create(HttpServletResponse response) {
        setHeader(response);

        Captcha captcha = createCaptcha("arithmetic");
        System.out.println("text:" + StringUtils.lowerCase(captcha.text()));
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHeader(HttpServletResponse response) {
        if (StringUtils.equalsIgnoreCase("arithmetic", "gif")) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

    private Captcha createCaptcha(String type) {
        Captcha captcha = null;
        if (StringUtils.equalsIgnoreCase(type, "gif")) {
            captcha = new GifCaptcha(115, 42, 4);
        } else if (StringUtils.equalsIgnoreCase(type, "png")) {
            captcha = new SpecCaptcha(115, 42, 4);
        } else if (StringUtils.equalsIgnoreCase(type, "arithmetic")) {
            captcha = new ArithmeticCaptcha(115, 42);
        } else if (StringUtils.equalsIgnoreCase(type, "chinese")) {
            captcha = new ChineseCaptcha(115, 42);
        }
        captcha.setCharType(2);
        return captcha;
    }
}
