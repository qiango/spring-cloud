package com.ribbonconsumer.service.leyile;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.core.base.service.BaseService;
import com.core.base.util.ModelUtil;
import com.core.base.util.UnixUtil;
import com.ribbonconsumer.mapper.leyile.LeMapper;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LeService extends BaseService {

    private WxMaService wxMaService;
    private LeMapper leMapper;


    @Autowired
    public LeService(WxMaService wxMaService, LeMapper leMapper) {
        this.wxMaService = wxMaService;
        this.leMapper = leMapper;
    }

    public Map<String, Object> getUserOpen(String code) {
        Map<String, Object> result = new HashMap<>();
        try {
            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo(code);
            String openid = wxMaJscode2SessionResult.getOpenid();
            String unionid = wxMaJscode2SessionResult.getUnionid();
            String sessionKey = wxMaJscode2SessionResult.getSessionKey();
            Map<String, Object> userOpen = leMapper.getUserOpen(openid);
            if (null == userOpen) {
                leMapper.insertUserOpen(openid, unionid, sessionKey);
            } else {
                leMapper.updateSessionKey(sessionKey, openid);
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
        Map<String, Object> userInfoMap = leMapper.getUserId(phoneNumber);
        long userid;
        if (null == userInfoMap) {
            userid = leMapper.insertUser(userInfo.getAvatarUrl(), phoneNumber, userInfo.getNickName(), UnixUtil.getCustomRandomString(), userInfo.getGender());
            leMapper.updateUserOpen(userid, openId);
        } else {
            userid = ModelUtil.getLong(userInfoMap, "id");
        }
        return userid;
    }


}
