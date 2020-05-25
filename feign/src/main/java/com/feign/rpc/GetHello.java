package com.feign.rpc;

import com.feign.rpc.rpcImpl.GetHelloImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "ribbon-consumer",fallback = GetHelloImpl.class)//指定熔断类
@Repository
public interface GetHello {//feign远程调用方式

    @GetMapping(value = "/qian/getCustomer")
    public String sayHello();

    @PostMapping(value = "/App/User/getUser")
    String getUser(@RequestParam("userId") long userId);
}
