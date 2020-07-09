package com.core.base.factory;

import com.core.base.baseenum.CodeEnum;

/**
 * @Authod: wangqian
 * @Date: 2020-07-09  18:41
 */
public enum AnswerTypeEnum implements CodeEnum {

    Emoji(1, "审核失败");

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

    AnswerTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AnswerTypeEnum getValue(int code) {
        for (AnswerTypeEnum c : AnswerTypeEnum.values()) {
            if (c.code == code) {
                return c;
            }
        }
        return Emoji;
    }

}
