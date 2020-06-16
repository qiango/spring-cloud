//package com.cloud.ribbon.rabbitmq;
//
//import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * @author qian.wang
// * @description 发送消息测试类
// * @date 2019/3/4
// */
//@SpringBootTest(classes = XMLJAXBElementProvider.App.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//public class MqTest {
//
//    @Autowired
//    private Producer helloSender;
//
//    @Test
//    public void testRabbit() {
//        helloSender.sendMessage("queue","hello,rabbit~");
//    }
//
//}
