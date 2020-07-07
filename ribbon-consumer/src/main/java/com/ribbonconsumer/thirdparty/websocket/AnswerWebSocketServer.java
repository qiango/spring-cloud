package com.ribbonconsumer.thirdparty.websocket;


import com.core.base.util.JsonUtil;
import com.core.base.util.MD5Encrypt;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint(value = "/AnswerSocket/{happyNo}/{sessionId}/{timespan}/{sign}")
@Component
public class AnswerWebSocketServer {
    private static Logger log = LoggerFactory.getLogger(AnswerWebSocketServer.class);
    private static final String SECRETKEY = "1w2q3c4d";
    private static AtomicInteger onlineCount = new AtomicInteger();
    private static ConcurrentHashMap<String, Session> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "happyNo") String happyNo, @PathParam(value = "timespan") long timespan, @PathParam(value = "sign") String sign
            , @PathParam(value = "sessionId") long sessionId, Session session) {
        addOnlineCount();           //在线数加1
        log.info("有新连接加入,参数为：happyNo:" + happyNo + ",timespan:" + timespan + ",sessionId:" + sessionId + ",sign:" + sign + ",当前在线人数为：" + getOnlineCount());
        String secretContent = String.format("%s|%s", happyNo, timespan);
        String cipherMd5 = MD5Encrypt.encrypt(String.format("%s|%s", SECRETKEY, secretContent));
        boolean result = sign.equals(cipherMd5);
//        if (!result) {
//            log.info("不合法连接，请检查>>>>>>");
//            try {
//                session.close();
//                return;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        String sessionKey = String.format("%s-%s", happyNo, sessionId);
        concurrentHashMap.put(sessionKey, session);
        try {
            sendMessage(session, "连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "happyNo") String happyNo) {
        concurrentHashMap.remove(happyNo);
        subOnlineCount();           //在线数减1
        log.info("用户为：" + happyNo + "的连接关闭！当前在线人数为" + getOnlineCount());
    }


    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        sendToUser(message);
    }


    //MQ收发消息
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.QUEUE_WEBSOCKET)  //监听器监听指定的Queue
    public void processToUser(String message) {
        log.info("MQ>>>>>收到来自客户端的消息:" + message);
        sendToUser(message);
    }


    /**
     * 给指定的人发送消息
     */
    private static void sendToUser(String message) {
        Map result = JsonUtil.getInstance().fromJson(message, Map.class);
        List<?> userList = ModelUtil.getList(result, "userList", new ArrayList<>());//发送消息用户列表
        String content = ModelUtil.getStr(result, "content");
        long sessionId = ModelUtil.getLong(result, "sessionId");
        log.info("会话id为：" + sessionId + ",发送给用户：" + userList.toString() + ",消息内容为：" + content);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (Object o : userList) {
            executorService.execute(() -> {
                try {
                    Session session = concurrentHashMap.get(String.format("%s-%s", o.toString(), sessionId));
                    if (null != session) {
                        sendMessage(session, content);
                    }
                } catch (IOException e) {
                    log.info("消息发送异常：" + e.getMessage());
                }
            });
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.info("AppAnswerWebSocketServer 发生错误" + session.getPathParameters(), error);
    }


    private static void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }


    private static AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    private static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    private static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

}