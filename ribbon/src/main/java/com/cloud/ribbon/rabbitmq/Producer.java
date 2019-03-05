package com.cloud.ribbon.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qian.wang
 * @description 生产者
 * @date 2019/3/4
 */
@Component
public class Producer {

    @Autowired
    public AmqpTemplate amqpTemplate;

    public  void sendMessage(String queueName,String message ){
        amqpTemplate.convertAndSend(queueName,message);
    }

}
