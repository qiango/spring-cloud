package com.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zuul.base.HttpRequestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

//网关中的过滤器，可配置多个，按照filterOrder的值来执行顺序
//filterType：表示过滤类型。 pre表示路由之前， routing表示路由当中， post表示路由之后， error表示路由发生错误。
//filterOrder： 执行时序， 值是0,1,2....N等自然数。 0的优先级最高，即最先执行。
//shouldFilter： 是否需要执行run函数。
//run：拦截器的具体实现；
public class DemoFilter extends ZuulFilter {


    private static Logger logger = LoggerFactory.getLogger(DemoFilter.class);
    private final static String AUTHORITY_ERROR = "{\"result\": -100,\"message\": \"token expire\"}";
    private static List<String> noLoginList = Arrays.asList("/sys/login", "/sys/sendSmsCode");

    @Override
    public String filterType() {
        return "pre";  //枚举值：pre, routing, post, error
    }

    @Override
    public int filterOrder() {
        return 1;    //优先级， 0是最高优先级即最先执行
    }


    public boolean shouldFilter() {
        return true;  //写逻辑，是否需要执行过滤。true会执行run函数，false不执行run函数
    }


    public Object run() {
        logger.info("------------接口校验开始----------");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        String userid = request.getParameter("userid");
        String requestURL = request.getRequestURI();
        if (!noLoginList.contains(requestURL)) {
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userid)) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody(AUTHORITY_ERROR);
            }
        }
        logger.info(String.format("url:----->%s>>>>>>params:------>%s", request.getRequestURL(), HttpRequestUtils.getAllParams(request)));
        return null;
    }
}
