package com.websocket.service;


import com.core.base.util.JsonUtil;
import com.core.base.util.MD5Encrypt;
import com.core.base.util.ModelUtil;
import com.core.base.util.QianThread;
import com.websocket.config.ConfigModel;
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
import java.util.concurrent.atomic.AtomicInteger;

//高版本的websocket在第一次http请求后，使用的是更快速的tcp连接
//zuul网关只能管理http请求，并且不支持tcp以及udp请求
//websocket在经过zuul以后，就会降级会http请求（轮询的方式）
//结论
//最好是不要通过zuul来管理websocket连接，降级为轮询后，效率会降低很多
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
        if (!result && "1".equals(ConfigModel.ISONLINE)) {
            log.info("不合法连接，请检查>>>>>>");
            try {
                session.close();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String sessionKey = String.format("%s-%s", happyNo, sessionId);
        concurrentHashMap.put(sessionKey, session);
        try {
            sendMessage(session, "pong");
        } catch (IOException e) {
            log.error("service IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "happyNo") String happyNo, @PathParam(value = "sessionId") String sessionId) {
        concurrentHashMap.remove(String.format("%s-%s", happyNo, sessionId));
        subOnlineCount();           //在线数减1
        log.info("happyNo:" + happyNo + ",sessionId:" + sessionId + "的连接关闭！当前在线人数为" + getOnlineCount());
    }


    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        if ("ping".equals(message)) {
            try {
                sendMessage(session, "pong");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sendToUser(message);
        }
    }


    //MQ收发消息
    @RabbitHandler
    @RabbitListener(queues = "QUEUE_WEBSOCKET")  //监听器监听指定的Queue
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
        ExecutorService executorService = QianThread.getIntance();
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
        log.info("WebSocketServer 发生错误" + session.getPathParameters(), error);
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