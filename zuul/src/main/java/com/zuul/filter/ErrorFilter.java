package com.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qian.wang
 * @description
 * @date 2019/1/17
 */
@Component
public class ErrorFilter extends ZuulFilter {

    Logger log = LoggerFactory.getLogger(this.getClass());


    private static final int FILTER_ORDER = 10;

    public String filterType() {
        return "error";
    }

    public int filterOrder() {
        return FILTER_ORDER;
    }

    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        return null;
    }
}