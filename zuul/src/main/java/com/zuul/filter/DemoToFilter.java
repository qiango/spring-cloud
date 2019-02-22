package com.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class DemoToFilter extends ZuulFilter {//网关中的过滤器，可配置多个，按照filterOrder的值来执行顺序
    private static Logger logger   = LoggerFactory.getLogger(DemoFilter.class);

    @Override
    public String filterType() {
        return "pre";  //枚举值：pre, routing, post, error
    }

    @Override
    public int filterOrder() {
        return 0;    //优先级, 0是最高优先级即最先执行
    }

    public boolean shouldFilter() {
        return true;  //写逻辑，是否需要执行过滤。true会执行run函数，false不执行run函数
    }


    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        logger.info("进入post过滤器");
//        System.out.println(ctx.getResponseBody());
////        ctx.setResponseBody("post后置数据");
        return null;
    }
}
