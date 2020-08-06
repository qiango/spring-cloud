package com.ribbonconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableApolloConfig 阿波罗配置中心
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
public class RibbonConsumerApplication {//服务提供者

	public static void main(String[] args) {
		SpringApplication.run(RibbonConsumerApplication.class, args);
	}
}
