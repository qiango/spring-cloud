package com.core.base.factory.impl;

import com.core.base.factory.TypeInterface;

/**
 * @Authod: wangqian
 * @Date: 2020-07-09  18:22
 */
public class EmojiImpl implements TypeInterface {

    private static volatile EmojiImpl instance;

    private EmojiImpl() {
    }

    public static EmojiImpl getInstance() {
        synchronized (EmojiImpl.class) {
            if (instance == null) {
                instance = new EmojiImpl();
            }
        }
        return instance;
    }


    @Override
    public Object getContent(String content) {
        return "[表情]";
    }
}
