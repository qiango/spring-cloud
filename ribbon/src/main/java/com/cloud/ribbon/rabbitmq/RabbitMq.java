package com.cloud.ribbon.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qian.wang
 * @description:实例化的队列，与交换机绑定(这里只是Direct模式) //还有其他两种模式，Topic转发模式和Fanout Exchange（广播形式）形式
 * @date 2019/3/4
 */
@Configuration
public class RabbitMq {
    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    public static void main(String[] args) {
        System.out.println("s".compareTo("s"));
    }
}
