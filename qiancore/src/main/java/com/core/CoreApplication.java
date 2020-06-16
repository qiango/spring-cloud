package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CoreApplication {

    public static void main(String[] args) { //服务调用者

        SpringApplication.run(CoreApplication.class, args);
    }
}
