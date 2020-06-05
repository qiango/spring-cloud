package com.ribbonconsumer.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatConfig {

    @Value("${ma.maAppId}")
    private String maAppId;

    @Value("${ma.maSecret}")
    private String maSecret;


    //初始化微信小程序参数
    @Bean
    public WxMaService wxMaService() {
        WxMaService wxMaService = new WxMaServiceImpl();
        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(maAppId);
        wxMaConfig.setSecret(maSecret);
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }


}

