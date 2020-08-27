package com.ribbonconsumer.service.leyile.abstra;

import com.core.base.service.BaseService;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.config.RabbitMqConfig;
import com.ribbonconsumer.mapper.leyile.LeMapper;
import com.ribbonconsumer.service.interfaceService.ProductInterface;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public abstract class LeAbstraService extends BaseService implements ProductInterface {

    private LeMapper leMapper;

    public LeAbstraService(LeMapper leMapper) {
        this.leMapper = leMapper;
    }

    @Transactional
    public void focusUser(long userid, long focusUserId, int type) {
        if (type == 1) {//取关
            leMapper.cancleFocuse(userid, focusUserId);
            leMapper.updateSubNum(focusUserId, 1);
            leMapper.updateSubNum(userid, 2);
        } else {
            leMapper.focusUser(userid, focusUserId);
            leMapper.updateAddNum(focusUserId, 1);
            leMapper.updateAddNum(userid, 2);
        }
    }


    @Transactional
    public void praise(long userid, long contentId, int type) {
        Map<String, Object> userId = leMapper.getUserId(contentId);
        long contentUserid = ModelUtil.getLong(userId, "userid");
        long classifyId = ModelUtil.getLong(userId, "classifyId");
        if (type == 1) {//取消点赞
            leMapper.canclePraise(userid, contentId);
            leMapper.updateSubNum(contentUserid, 3);
            updateRecords(leMapper,userid, classifyId, -50);
        } else {
            leMapper.praise(userid, contentId);
            leMapper.updateSubNum(contentUserid, 3);
            updateRecords(leMapper,userid, classifyId, 100);
        }
    }


    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.QUEUE_A)  //监听器监听指定的Queue
    public void processA(String str) {
        System.out.println("Receive>>>>>>>>>>>>>AAA" + str);
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.QUEUE_B)  //监听器监听指定的Queue
    public void processB(String str) {
        System.out.println("Receive>>>>>>>>>>>>>BBB" + str);
    }


    public void insertEvaluation(long userid, long contentId, String content, long pid) {
        leMapper.insertEvaluation(userid, contentId, content, pid);
        Map<String, Object> userId = leMapper.getUserId(contentId);
        long classifyId = ModelUtil.getLong(userId, "classifyId");
        updateRecords(leMapper,userid, classifyId, 150);
    }

    public void updateEvaluation(long evaluationId, long classifyId, long userid) {
        leMapper.updateEvaluation(evaluationId);
        updateRecords(leMapper,userid, classifyId, 120);
    }


}
