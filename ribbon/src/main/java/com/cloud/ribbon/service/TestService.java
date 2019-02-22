package com.cloud.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class TestService {

    @Autowired
    private RestTemplate restTemplate;//需要再启动类里面加载这个参数

    //断路打开后，可用避免连锁故障，如果远程调用失败则，fallback方法可以直接返回一个固定值。(Hystrix断路器)
    @HystrixCommand(fallbackMethod = "hiError")//该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法，熔断方法直接返回了一个字符串
    public String sayHello(){
        System.out.print("开始远程调用用户服务。。。");
       return  restTemplate.getForObject("http://ribbon-consumer/qian/nullTest",String.class);
    }

//    @HystrixCommand(fallbackMethod = "hiError")重写一个断路器方法
//    这样就会出现如上所述的异常，这是因为指定的 备用方法 和 原方法 的参数个数，类型不同造成的所以需要统一参数的个数，类型：
//    public String getUser(Long id){
//        System.out.print("http://ribbon-consumer/simple/"+id);
//        return  restTemplate.getForObject("http://ribbon-consumer/simple/"+id,String.class);
//    }



    public String hiError() {
        return "sorry,error!";
    }
}
