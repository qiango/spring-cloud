package com.feign.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ribbon-consumer")
public interface GetHello {//feign远程调用方式

    @RequestMapping(value = "/getCustomer?name=feign",method = RequestMethod.GET)
    public String sayHello();
}
