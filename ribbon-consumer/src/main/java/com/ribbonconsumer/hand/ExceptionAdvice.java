package com.ribbonconsumer.hand;

import com.core.base.exception.QianException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice implements ResponseBodyAdvice<Object> {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }


    /**
     * 拦截异常
     *
     * @param e 异常信息
     * @param m 方法信息
     */
    @ExceptionHandler(value = {QianException.class})
    @ResponseBody
    public Map<String, Object> handleException(QianException e, HandlerMethod m) {
        Map<String, Object> error = new HashMap<>();
        log.info(m.getMethod().getDeclaringClass() + ">" + m.getMethod().getName(), e);
        error.put("code", e.getCode());
        error.put("msg", "customException");
        error.put("result", e.getMessage());
        return error;
    }

    /**
     * 拦截异常
     *
     * @param e 异常信息
     * @param m 方法信息
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Map<String, Object> handle(Exception e, HandlerMethod m) {
        Map<String, Object> error = new HashMap<>();
        log.error(m.getMethod().getDeclaringClass() + ">" + m.getMethod().getName(), e);
        error.put("code", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        error.put("msg", "exception");
        error.put("result", "系统异常，请联系Q");
        return error;
    }
}
