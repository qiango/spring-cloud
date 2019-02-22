package com.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ZuulApplication {//网关，类似于过滤器的功能

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}


}
