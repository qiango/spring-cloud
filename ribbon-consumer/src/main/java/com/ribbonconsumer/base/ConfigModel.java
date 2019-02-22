package com.ribbonconsumer.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigModel {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public static String BASEFILEPATH;

    @Value("${base.filepath}")
    public void setBASEFILEPATH(String value) {
        BASEFILEPATH = value;
    }

    public static String ISONLINE;

    @Value("${base.isonline}")
    public void setISONLINE(String value) {
        ISONLINE = value;
    }

    /**
     * 第三方用户注册来源渠道
     */
    public static class USER_CHANNEL {
        public static final int WECHAT = 1;//微信公众号进入
        public static final int ALI = 2;//支付宝生活号进入
        public static final int WECHAT_WEB = 3;//微信网页
        public static final int ALI_WEB = 4;//支付宝网页进入
        public static final int WECHAT_QRCODE = 5;//扫码进入
        public static final int ALI_QRCODE = 6;//扫码进入
    }
}
