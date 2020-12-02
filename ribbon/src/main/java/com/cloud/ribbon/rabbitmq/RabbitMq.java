package com.cloud.ribbon.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        List<Integer> list = Arrays.asList(1, 3, 5, 7, 9);
        Collections.sort(list);
//        Arrays.sort(l);
        System.out.println("s".compareTo("s"));
    }
}
