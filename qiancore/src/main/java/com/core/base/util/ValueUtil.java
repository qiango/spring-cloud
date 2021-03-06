
package com.core.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.core.base.exception.QianException;
import com.google.gson.*;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taylor on 8/7/16.
 * twitter: @taylorwang789
 */
public class ValueUtil {

    public static String defaultSuccess = "success";
    private static JSONObject jsonObject = new JSONObject();

    public static boolean notEmpity(Object obj) {
        if (null == obj) {
            return false;
        } else if (obj.toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isEmpity(Object obj) {
        return !notEmpity(obj);
    }


    public static <T> T coalesce(T... args) {
        for (int i = 0; i < args.length; i++) {
            if (notEmpity(args[i])) {
                return args[i];
            }
        }
        return (T) "";
    }

    public static Gson gsonExp = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    //    public static Gson gson = new Gson();
    public static Gson gson = new GsonBuilder().serializeNulls().create();

//    public static String toJson(String code, Object obj) {
//        RestJson restJson = new RestJson();
////        restJson.setCode(code);
//        restJson.setMsg(defaultSuccess);
//        restJson.setData(coalesce(obj, ""));
//        return gson.toJson(restJson);
//    }

    public static String toJson(Integer code, Object obj) {
        RestJson restJson = new RestJson();
        restJson.setCode(String.valueOf(code));
        restJson.setMsg(defaultSuccess);
        restJson.setData(coalesce(obj, ""));
        return gson.toJson(restJson);
    }


    public static String toJson(Object obj) {
        try {
            RestJson restJson = new RestJson();
            restJson.setCode(String.valueOf(HttpStatus.SC_OK));
            restJson.setMsg(defaultSuccess);
            restJson.setData(coalesce(obj, ""));
            return gson.toJson(restJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject toJsonObject(Object obj) {
        try {
            return JSON.parseObject(gson.toJson(coalesce(obj, "")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String toString(Object obj) {
        return gson.toJson(obj);
    }


    public static String toJson(Object... objs) {
        RestJson restJson = toRestJson(objs);
        return gson.toJson(restJson);
    }

    public static String toJsonExp(Object... objs) {
        RestJson restJson = toRestJson(objs);
        return gsonExp.toJson(restJson);
    }


    public static JSONObject toError(String code, String message) {
        jsonObject.put("code", code);
        jsonObject.put("data", "");
        jsonObject.put("message", message);
        return jsonObject;
    }

    public static String isError(String message) throws QianException {
        RestJson restJson = new RestJson();
        restJson.setCode(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        restJson.setMsg("erro");
        restJson.setData(coalesce(message, ""));
        return gson.toJson(restJson);
    }

    public static String toError(String code, String message, Object object) {
        RestJson restJson = toRestJson(object);
        restJson.setCode(code);
        restJson.setData(object);
        restJson.setMsg(message);
        return gson.toJson(restJson);
    }

    public static String toError(Integer code, String message) {
        RestJson restJson = new RestJson();
        restJson.setCode(String.valueOf(code));
        restJson.setMsg(message);
        restJson.setData("");
        return toString(restJson);
    }

    public static RestJson toRestJson(Object... objs) {
        RestJson restJson = new RestJson();
        restJson.setCode(String.valueOf(HttpStatus.SC_OK));
        restJson.setMsg("");
        Map<String, Object> map = new HashMap<>();
        boolean isOdd = true;
        String key = "";
        for (int i = 0; i < objs.length; i++) {
            if (isOdd) {
                key = objs[i].toString();
                isOdd = false;
            } else {
                if (notEmpity(objs[i])) {
                    map.put(key, objs[i]);
                } else {
                    map.put(key, new JsonObject());
                }
                isOdd = true;
            }
        }
        restJson.setData(map);
        return restJson;
    }


    private static JsonParser jsonParser = new JsonParser();

    public static JsonElement getFromJson(String json) {
        JsonElement origin = jsonParser.parse(json);
        return origin;
    }

    public static <T> T getFromJson(String json, Class<T> clz) {
        return gson.fromJson(json, clz);
    }

    public static String getFromJson(String json, Object... args) {
        JsonObject origin = jsonParser.parse(json).getAsJsonObject();
        for (int i = 0; i < args.length; i++) {
            if ((i + 1) == args.length) {
                String returnString = origin.get(args[i].toString()).toString();
                if (returnString.startsWith("\"")) {
                    return returnString.substring(1, returnString.length() - 1);
                } else {
                    return returnString;
                }
            } else {
                if (args[i].getClass().equals(Integer.class)) {
                    origin = origin.getAsJsonArray().get(Integer.valueOf(args[i].toString())).getAsJsonObject();
                }
                if (args[i].getClass().equals(String.class)) {
                    origin = origin.getAsJsonObject(args[i].toString());
                }
            }
        }
        return origin.toString();
    }
}

