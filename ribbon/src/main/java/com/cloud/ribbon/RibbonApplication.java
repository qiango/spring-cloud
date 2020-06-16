package com.cloud.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableHystrix   //断路器
public class RibbonApplication {

	public static void main(String[] args) { //服务调用者

		SpringApplication.run(RibbonApplication.class, args);
	}
	@Bean
	@LoadBalanced //负载均衡
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
