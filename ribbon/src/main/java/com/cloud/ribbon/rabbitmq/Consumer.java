package com.cloud.ribbon.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

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

    public static void main(String[] args) {
//        int x=1;
//        float y=2;
//        System.out.println(x/y);

        Integer[] A = { 1 , 3 , -1 ,0 , 2 , 1 , -4 , 2 , 0 ,1 };
        Arrays.sort(A);//默认升序
        Arrays.sort(A, Collections.reverseOrder());//降序
        for (int i : A) {
            System.out.print(i);
        }
    }

}