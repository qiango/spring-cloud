package com.ribbonconsumer.base.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by by on 2017/8/1.
 */
public class HttpRequestUtils {
    public static Map<String, Object> getAllParams(HttpServletRequest request) {
        Enumeration parameterNames = request.getParameterNames();
        Map<String, Object> map = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String key = (String) parameterNames.nextElement();
            String value = request.getParameter(key);
            map.put(key,value);
        }
        return map;
    }
}
