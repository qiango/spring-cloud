package com.core.base.factory;

import com.core.base.factory.impl.EmojiImpl;

/**
 * @Authod: wangqian
 * @Date: 2020-07-09  18:18
 */
public class AnswerTypeFactory {

    public static TypeInterface typeInterface(AnswerTypeEnum answerTypeEnum) {

        TypeInterface typeInterface = null;
        switch (answerTypeEnum) {
            case Emoji:
                typeInterface = EmojiImpl.getInstance();
                break;
            default:
                System.out.println("....");
        }
        return typeInterface;
    }


}
