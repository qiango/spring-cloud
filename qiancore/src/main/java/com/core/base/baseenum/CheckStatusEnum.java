package com.core.base.baseenum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CheckStatusEnum implements CodeEnum {

    NoCheck(0, "未审核"),

    Success(1, "审核成功"),

    Fail(2, "审核失败");

    private Integer code;

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    CheckStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CheckStatusEnum getValue(int code) {
        for (CheckStatusEnum c : CheckStatusEnum.values()) {
            if (c.code == code) {
                return c;
            }
        }
        return NoCheck;
    }

    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CheckStatusEnum c : CheckStatusEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getCode());
            map.put("value", c.getMessage());
            list.add(map);
        }
        return list;
    }

    public static String getName(int code) {
        for (CheckStatusEnum c : CheckStatusEnum.values()) {
            if (c.code == code) {
                return c.getMessage();
            }
        }
        return null;
    }


}
