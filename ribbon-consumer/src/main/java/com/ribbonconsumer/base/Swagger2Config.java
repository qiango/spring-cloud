package com.ribbonconsumer.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost:8886/swagger-ui.html接口文档访问地址
@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
public class Swagger2Config {
    //扫描接口包的范围
    private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.ribbonconsumer.controller";
    private static final String VERSION = "1.0.0";

    @Value("${swagger.base.apiPath}")
    private String swaggerPath;

    @Bean
    public Docket createRestApi() {
        if ("1".equals(ConfigModel.ISONLINE)) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfoOnline())
                    .select()
                    .paths(PathSelectors.none())//如果是线上环境，添加路径过滤，设置为全部都不符合
                    .build();
        } else {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .pathProvider(new AbstractPathProvider() {
                        @Override
                        protected String applicationPath() {
                            return swaggerPath;
                        }

                        @Override
                        protected String getDocumentationPath() {
                            return swaggerPath;
                        }
                    })
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))//api接口包扫描路径
                    .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                    .build();
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("王迁项目接口文档")//设置文档的标题
                .description("用于管理所有功能模块...")//设置文档的描述->1.Overview
                .version(VERSION)//设置文档的版本信息-> 1.1 Version information
                .contact(new Contact("syh", "https://www.salaheiyo.club", ""))//设置文档的联系方式->1.2 Contact information
                .termsOfServiceUrl("https://www.salaheiyo.club")//设置文档的License信息->1.3 License information
                .build();
    }

    private ApiInfo apiInfoOnline() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("")
                .contact(new Contact("", "", ""))
                .build();
    }
}
