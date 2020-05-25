package com.core.base.controller;//package com.ribbonconsumer.base.controller;
//
//import com.wangqian.basecore.util.ValueUtil;
//import org.apache.http.HttpStatus;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.method.HandlerMethod;
//
///**
// * @author qian.wang
// * @description
// * @date 2019/1/17
// */
//@RestController
//public class ErrorHandlerController implements ErrorController{
//
//    protected Logger log = LoggerFactory.getLogger(this.getClass());
//    /**
//     * 出异常后进入该方法，交由下面的方法处理
//     */
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//
//
//
//    @RequestMapping("/error")
//    @ExceptionHandler(value = {Exception.class})
//    public Object error(Exception e) {
//        log.error("异常为"+e.getMessage());
//        return ValueUtil.toError(HttpStatus.SC_INTERNAL_SERVER_ERROR,"系统异常，请检查") ;
//    }
//}
