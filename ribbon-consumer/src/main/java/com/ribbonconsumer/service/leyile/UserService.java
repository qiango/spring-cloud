package com.ribbonconsumer.service.leyile;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.core.base.factory.AnswerTypeEnum;
import com.core.base.factory.AnswerTypeFactory;
import com.core.base.service.BaseService;
import com.core.base.util.JsonUtil;
import com.core.base.util.ModelUtil;
import com.core.base.util.QianThread;
import com.core.base.util.UnixUtil;
import com.ribbonconsumer.mapper.leyile.UserMapper;
import com.ribbonconsumer.thirdparty.mq.MsgProducer;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service
public class UserService extends BaseService {

    private WxMaService wxMaService;
    private UserMapper userMapper;
    private MsgProducer msgProducer;


    @Autowired
    public UserService(WxMaService wxMaService, UserMapper userMapper, MsgProducer msgProducer) {
        this.wxMaService = wxMaService;
        this.userMapper = userMapper;
        this.msgProducer = msgProducer;
    }

    public Map<String, Object> getUserOpen(String code) {
        Map<String, Object> result = new HashMap<>();
        try {
            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo(code);
            String openid = wxMaJscode2SessionResult.getOpenid();
            String unionid = wxMaJscode2SessionResult.getUnionid();
            String sessionKey = wxMaJscode2SessionResult.getSessionKey();
            Map<String, Object> userOpen = userMapper.getUserOpen(openid);
            if (null == userOpen) {
                userMapper.insertUserOpen(openid, unionid, sessionKey);
            } else {
                userMapper.updateSessionKey(sessionKey, openid);
            }
            result.put("openid", openid);
            result.put("unionid", unionid);
            result.put("sessionKey", sessionKey);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return result;
    }

    public long getUserInfo(String sessionKey, String encryptedData, String ivStr) {
        //微信用户对象接口
        WxMaUserService userService = wxMaService.getUserService();
        WxMaUserInfo userInfo = userService.getUserInfo(sessionKey, encryptedData, ivStr);
        String openId = userInfo.getOpenId();
        WxMaPhoneNumberInfo phoneNoInfo = userService.getPhoneNoInfo(sessionKey, encryptedData, ivStr);
        String phoneNumber = phoneNoInfo.getPhoneNumber();//获取到到用户手机号
        Map<String, Object> userInfoMap = userMapper.getUserId(phoneNumber);
        long userid;
        if (null == userInfoMap) {
            userid = userMapper.insertUser(userInfo.getAvatarUrl(), phoneNumber, userInfo.getNickName(), UnixUtil.getCustomRandomString(), userInfo.getGender());
            userMapper.updateUserOpen(userid, openId);
        } else {
            userid = ModelUtil.getLong(userInfoMap, "id");
        }
        return userid;
    }

    public Map<String, Object> getUserInfoMation(long userid) {
        Map<String, Object> result = new HashMap<>();
        result.put("userInfoMation", userMapper.getUserInfoMation(userid));
        result.put("myLoveContentList", userMapper.getMyLoveContentList(userid));
        result.put("myCreateContentList", userMapper.getMyCreateContentList(userid));
        return result;
    }

    public List<Map<String, Object>> myFocusList(long userid) {
        return userMapper.myFocusList(userid);
    }

    public List<Map<String, Object>> myFanList(long userid) {
        return userMapper.myFanList(userid);
    }

    public void updateIntroduce(long userid, String headpic, String name, String introduce) {
        userMapper.updateIntroduce(userid, headpic, name, introduce);
    }

    @Transactional
    public void sendMessage(long sessionId, String content, long userid, List<?> userList) {
        Map<String, Object> message = new HashMap<>();
        List<Long> userIds = new ArrayList<>();
        List<String> userIdFinall = new ArrayList<>();
        if (sessionId != 0) {
            userIds = userMapper.getUserIds(sessionId);
        } else {
            sessionId = userMapper.insertSession(userid, "name");
            userMapper.insertGroup(sessionId, userid);
            userIds.add(userid);
            for (Object o : userList) {
                userIds.add(Long.parseLong(o.toString()));
                userMapper.insertGroup(sessionId, Long.parseLong(String.valueOf(o)));
            }
        }
        userMapper.insertAnswer(userid, sessionId, content);

        ExecutorService executorService = QianThread.getIntance();
        long finalSessionId = sessionId;
        List<Long> finalUserIds = userIds;
        executorService.execute(() -> {
            List<Map<String, Object>> noList = userMapper.getNoList(finalUserIds);
            noList.forEach(stringObjectMap -> userIdFinall.add(ModelUtil.getStr(stringObjectMap, "no")));
            message.put("userList", userIdFinall);
            message.put("content", content);
            message.put("sessionId", finalSessionId);
            msgProducer.sendMsgByWebSocket(JsonUtil.getInstance().toJson(message));
        });
    }

    public List<Map<String, Object>> getContentList(long sessionId, long userid, int pageSize, int pageIndex) {
        List<Map<String, Object>> list = userMapper.getContentList(sessionId, userid, pageSize, pageIndex);
        initAnswer(list);
        return list;
    }

    public List<Map<String, Object>> getSessionList(long userid) {
        List<Map<String, Object>> list = userMapper.getSessionList(userid);
        initAnswer(list);
        return list;
    }

    private void initAnswer(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            int type = ModelUtil.getInt(map, "type");
            String content = ModelUtil.getStr(map, "content");
            map.put("content", AnswerTypeFactory.typeInterface(AnswerTypeEnum.getValue(type)).getContent(content));
        }
    }


}
