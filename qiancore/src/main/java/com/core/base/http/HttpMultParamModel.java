package com.core.base.http;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HttpMultParamModel {
    private MultipartBody.Builder form;
    private HashMap obj;

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    public HttpMultParamModel() {
        form = new MultipartBody.Builder();
        form.setType(MultipartBody.FORM);
        obj = new HashMap();
    }



    MultipartBody.Builder getForm() {
        return form;
    }

    Map<String, Object> getMap() {
        return obj;
    }

    public void add(String name, Object value) {
        if (name == null) {
            name = "";
        }
        if (value == null) {
            value = "";
        }
        if (value instanceof JSONArray) {
            form.addFormDataPart(name, value.toString());
            obj.put(name, value.toString());
        } else if (value instanceof JSONObject) {
            form.addFormDataPart(name, value.toString());
            obj.put(name, value.toString());
        } else {
            form.addFormDataPart(name, String.valueOf(value));
            obj.put(name, String.valueOf(value));
        }
    }

    public void add(String name, String filename, File file) {
        if (name == null) {
            name = "";
        }
        if (filename == null) {
            filename = "";
        }
        obj.put(name, filename);
        form.addFormDataPart(name, filename, RequestBody.create(MEDIA_TYPE_PNG, file));
    }

    @Override
    public String toString() {
        return "HttpMultParamModel{" +
                "params=" + obj.toString() +
                '}';
    }
}
