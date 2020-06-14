package com.ribbonconsumer.service.leyile;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.core.base.service.BaseService;
import com.core.base.util.ModelUtil;
import com.core.base.util.UnixUtil;
import com.ribbonconsumer.mapper.leyile.UserMapper;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService {

    private WxMaService wxMaService;
    private UserMapper userMapper;


    @Autowired
    public UserService(WxMaService wxMaService, UserMapper userMapper) {
        this.wxMaService = wxMaService;
        this.userMapper = userMapper;
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


}
