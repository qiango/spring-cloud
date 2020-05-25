package com.ribbonconsumer.service;

import com.core.base.exception.QianException;
import com.core.base.util.RedisUtil;
import com.ribbonconsumer.mapper.TestMapper;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import java.util.List;


@Service
public class TestService {

    @Value("${server.port}")
    private String port;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;//注入mq发送类

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private RedisUtil redisUtil;

    Logger log = LoggerFactory.getLogger(this.getClass());

    public String sayHello() {
        return "my is customer" + port;
    }

    public Object getTest() {
        String a = "";
        if (a.equals("")) {
            throw new QianException("测试异常ssssss");
        }
//        redisUtil.set("kkk", "哈哈哈", 30 * 1000L);
        return true;
    }

    public Object nullTest() {
        List list = null;
        list.get(0);
        return "true";
    }

    public void sendMessage(String disname, String message) {
        log.info("====================>> 发送queue消息：" + message + ",disname：" + disname);
        Destination destination = new ActiveMQQueue(disname);
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

    public void sendMessageOrder(String disname, long message) {
        log.info("====================>> 发送queue消息：" + message + ",disname：" + disname);
        Destination destination = new ActiveMQQueue(disname);
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

    //只会消费一次，并且是轮训消费
    @JmsListener(destination = "mytest.queue")
    public void receiveQueue(String text) {
        System.out.println("Consumer收到的报文为1:" + text);
    }

    @JmsListener(destination = "testorder")
    @SendTo("mytest.queue")//我们在receiveQueue方法上面多加了一个注解@SendTo("out.queue")，该注解的意思是将return回的值，再发送的"out.queue"队列中
    public String receiveQueues(String text) {
        System.out.println("order收到的报文为:" + text);
        if (testMapper.getNum() > 0) {
            testMapper.updateNum();
            System.out.println("用户编号" + text + "的购买成功");
            return "return message" + text;
        } else {
            throw new QianException("卖完了---------------------");
        }
    }

    @Transactional
    public void createOrder(long userid) {
        if (testMapper.getNum() > 0) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            testMapper.updateNum();
            System.out.println("用户编号" + userid + "的购买成功");
        } else {
            throw new QianException("卖完了---------------------");
        }
        System.out.println("还剩余：" + testMapper.getNum());
    }

    public static void main(String[] args) {

    }

}