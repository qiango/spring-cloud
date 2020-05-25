package com.feign.rpc.rpcImpl;

import com.feign.rpc.GetHello;
import org.springframework.stereotype.Component;


/**
 * 当UserFeignService中的Feign调用失败或超时时，会调用该实现类的方法
 * 需要注意的是fallback指定的类一定要添加@Component将其加入到Spring容器
 */
@Component
public class GetHelloImpl implements GetHello {

    @Override
    public String sayHello() {
        System.out.println("读取超时熔断降级");
        return "erro";
    }

    @Override
    public String getUser(long userId) {
        return null;
    }
}
