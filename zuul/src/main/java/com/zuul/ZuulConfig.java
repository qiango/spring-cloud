package com.zuul;

import com.zuul.filter.DemoFilter;
import com.zuul.filter.DemoToFilter;
import com.zuul.filter.ErrorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by qians on 2018/9/28.
 * 必须得注入过滤类的参数
 */
@Configuration
public class ZuulConfig {

    @Bean
    public DemoFilter accessFilter() {
        return new DemoFilter();
    }
    @Bean
    public DemoToFilter accessFilterTo() {
        return new DemoToFilter();
    }
    @Bean
    public ErrorFilter accessFilterToo() {
        return new ErrorFilter();
    }

}