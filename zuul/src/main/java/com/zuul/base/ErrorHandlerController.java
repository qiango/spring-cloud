package com.zuul.base;

//import com.core.base.util.ValueUtil;

import org.apache.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qian.wang
 * @description
 * @date 2019/1/17
 */
@RestController
public class ErrorHandlerController implements ErrorController {

    /**
     * 出异常后进入该方法，交由下面的方法处理
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Object error() {
        return ValueUtil.toError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "系统异常，请检查");
    }
}
