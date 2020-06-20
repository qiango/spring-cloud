package com.zuul.filter;

import com.core.base.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${base.cross-domain}")
    public void setCROSSDOMAIN(String value) {
        CROSSDOMAIN = StrUtil.isEmpty(value) ? "*" : value;
    }

    private static String CROSSDOMAIN;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(CROSSDOMAIN.split(","))
                .allowCredentials(true)
                .allowedMethods("GET", "POST")
                .maxAge(3600);
    }
}
