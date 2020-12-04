package com.core.base.factory.impl;

import com.core.base.factory.TypeInterface;

/**
 * @Authod: wangqian
 * @Date: 2020-07-09  18:22
 */
public class TextImpl implements TypeInterface {

    private static volatile TextImpl instance;

    private TextImpl() {
    }

    public static TextImpl getInstance() {
        synchronized (TextImpl.class) {
            if (instance == null) {
                instance = new TextImpl();
            }
        }
        return instance;
    }


    @Override
    public Object getContent(String content) {
        return content;
    }
}
