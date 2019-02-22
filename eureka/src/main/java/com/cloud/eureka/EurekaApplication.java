package com.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer  //此服务即是eureka注册中心又是服务的提供者
public class EurekaApplication {//注册中心

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}
}
