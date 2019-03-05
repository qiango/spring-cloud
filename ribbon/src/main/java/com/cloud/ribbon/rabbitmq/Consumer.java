package com.cloud.ribbon.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author qian.wang
 * @description 消费者
 * @date 2019/3/4
 */
@Component
public class Consumer {

    @RabbitListener(queues="queue")    //监听器监听指定的Queue
    public void processC(String str) {
        System.out.println("Receive:"+str);
    }

}