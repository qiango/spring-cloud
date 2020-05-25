package com.ribbonconsumer.thirdparty.enumutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//    充值类型1：支付宝，2：微信,3：手动后台充值
public enum PayTypeEnum {


    AliPay(1, "支付宝"),

    Wechat(2, "微信"),

    Admin(3, "手动后台充值"),
    ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    PayTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PayTypeEnum getValue(int code) {
        for (PayTypeEnum c : PayTypeEnum.values()) {
            if (c.code == code) {
                return c;
            }
        }
        return Admin;
    }

    public List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PayTypeEnum c : PayTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getCode());
            map.put("value", c.getMessage());
            list.add(map);
        }
        return list;
    }


}
