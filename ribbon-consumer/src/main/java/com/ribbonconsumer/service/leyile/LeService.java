package com.ribbonconsumer.service.leyile;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.core.base.service.BaseService;
import com.core.base.util.ModelUtil;
import com.core.base.util.UnixUtil;
//import com.ribbonconsumer.config.RabbitMqConfig;
import com.ribbonconsumer.mapper.leyile.LeMapper;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class LeService extends BaseService {

    private WxMaService wxMaService;
    private LeMapper leMapper;
    private final Object obj = new Object();


    @Autowired
    public LeService(WxMaService wxMaService, LeMapper leMapper) {
        this.wxMaService = wxMaService;
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
        long contentUserid = ModelUtil.getLong(leMapper.getUserId(contentId), "userid");
        if (type == 1) {//取消点赞
            leMapper.canclePraise(userid, contentId);
            leMapper.updateSubNum(contentUserid, 3);
        } else {
            leMapper.praise(userid, contentId);
            leMapper.updateSubNum(contentUserid, 3);
        }
    }


//    @RabbitHandler
//    @RabbitListener(queues = RabbitMqConfig.QUEUE_A)  //监听器监听指定的Queue
//    public void processC(String str) {
//        System.out.println("Receive>>>>>>>>>>>>>" + str);
//    }

    public void insertEvaluation(long userid, long contentId, String content, long pid) {
        leMapper.insertEvaluation(userid, contentId, content, pid);
    }

    public void updateEvaluation(long evaluationId) {
        leMapper.updateEvaluation(evaluationId);
    }


}
