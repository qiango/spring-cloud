package com.core.base.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.base.util.JsonUtil;
import com.core.base.util.StrUtil;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class GoodPharmacistHttpUtil extends HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private String goodsPharmacistUrl;
    private String grantType;
    private String clientId;
    private String clientsecret;
    private String scop;

    public GoodPharmacistHttpUtil(String goodsPharmacistUrl, String grantType, String clientId, String clientsecret, String scop) {
        this.goodsPharmacistUrl = goodsPharmacistUrl;
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientsecret = clientsecret;
        this.scop = scop;
    }


    //get请求返回list
    public List<Map<String, Object>> getArray(String url, HttpParamModel params) {
        List<Map<String, Object>> temp = null;
        try {
            if (params == null) {
                params = new HttpParamModel();
            }
            ResponseBody body = super.get(goodsPharmacistUrl + url, params, null);

            String value;
            if (body != null) {
                value = body.string();
                log.info("get:return=" + value);
                temp = JsonUtil.getInstance().jsonToList(JSONArray.parseArray(value));
            }

        } catch (Exception e) {
            log.error("doGet Error > ", e);
        }
        return temp;
    }

    //put请求返回map
    public Map<String, Object> getMapPut(String url, HttpParamModel params, String values) {
        Map<String, Object> temp = null;
        StringBuilder urlBuilder = new StringBuilder(goodsPharmacistUrl);
        urlBuilder.append(url);
        try {
            if (params == null) {
                params = new HttpParamModel();
            }
            urlBuilder.append("/").append(values);

            ResponseBody body = super.get(urlBuilder.toString(), params, null);

            String value;
            if (body != null) {
                value = body.string();
                log.info("get:return=" + value);
                temp = JsonUtil.getInstance().jsonToMap(JSONObject.parseObject(value));
            }

        } catch (Exception e) {
            log.error("doGet Error > ", e);
        }
        return temp;
    }

    //put请求返回list
    public List<Map<String, Object>> getArrayPut(String url, HttpParamModel params, String values) {
        List<Map<String, Object>> temp = null;
        StringBuilder urlBuilder = new StringBuilder(goodsPharmacistUrl);
        urlBuilder.append(url);
        try {
            if (params == null) {
                params = new HttpParamModel();
            }
            urlBuilder.append("/").append(values);

            ResponseBody body = super.get(urlBuilder.toString(), params, null);

            String value;
            if (body != null) {
                value = body.string();
                log.info("get:return=" + value);
                temp = JsonUtil.getInstance().jsonToList(JSONArray.parseArray(value));
            }

        } catch (Exception e) {
            log.error("doGet Error > ", e);
        }
        return temp;
    }

    //get请求返回map
    public Map<String, Object> getMap(String url, HttpParamModel params) {
        Map<String, Object> temp = null;
        try {
            if (params == null) {
                params = new HttpParamModel();
            }

            ResponseBody body = super.get(goodsPharmacistUrl + url, params, null);

            String value = "";
            if (body != null) {
                value = body.string();
                log.info("get:return=" + value);
                temp = JsonUtil.getInstance().jsonToMap(JSONObject.parseObject(value));
            }
        } catch (Exception e) {
            log.error("doGet Error > ", e);
        }
        return temp;
    }

    //post请求返回map(正常传参)
    public Map<String, Object> postMap(String url, HttpParamModel params) {
        Map<String, Object> temp = null;
        StringBuilder urlBuilder = new StringBuilder(goodsPharmacistUrl);
        urlBuilder.append(url);
        try {
            if (params == null) {
                params = new HttpParamModel();
            }
            if (!StringUtils.isEmpty(url) && !url.contains("?")) {
                urlBuilder.append("?");
            }
            for (Map.Entry<String, Object> entry : params.getMap().entrySet()) {
                urlBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            url = urlBuilder.toString();

            ResponseBody body = super.post(url, params, null);

            String value;
            if (body != null) {
                value = body.string();
                log.info("get:return=" + value);
                temp = JsonUtil.getInstance().jsonToMap(JSONObject.parseObject(value));
            }

        } catch (Exception e) {
            log.error("doGet Error > ", e);
        }
        return temp;
    }

    //post请求返回list(正常传参)
    public List<Map<String, Object>> postArray(String url, HttpParamModel params) {
        List<Map<String, Object>> temp = null;
        StringBuilder urlBuilder = new StringBuilder(goodsPharmacistUrl);
        urlBuilder.append(url);
        try {
            if (params == null) {
                params = new HttpParamModel();
            }
            if (!StringUtils.isEmpty(url) && !url.contains("?")) {
                urlBuilder.append("?");
            }
            for (Map.Entry<String, Object> entry : params.getMap().entrySet()) {
                urlBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            url = urlBuilder.toString();

            ResponseBody body = super.post(url, params, null);

            String value;
            if (body != null) {
                value = body.string();
                log.info("get:return=" + value);
                temp = JsonUtil.getInstance().jsonToList(JSONArray.parseArray(value));
            }

        } catch (Exception e) {
            log.error("doGet Error > ", e);
        }
        return temp;
    }

    //post请求返回map(传json)
    public Map<String, Object> postMapBody(String url, String json, HttpMultParamModel params) {
        Map<String, Object> temp = null;
        StringBuilder urlBuilder = new StringBuilder(goodsPharmacistUrl);
        try {
            if (params == null) {
                params = new HttpMultParamModel();
            }
//            Headers.Builder headers = new Headers.Builder()
//                    .add("Content-Type", "application/json");
            params.add("prescInfo", json);
            params.add("file", "p.png", new File("/Users/qiango/Desktop/a.jpg"));
            ResponseBody body = super.postMutl(urlBuilder.toString(), params, null);
            String value;
            if (body != null) {
                value = body.string();
                log.info("get:return=" + value);
                temp = JsonUtil.getInstance().jsonToMap(JSONObject.parseObject(value));
            }
        } catch (Exception e) {
            log.error("doGet Error > ", e);
        }
        return temp;
    }

    //post请求返回list(传json)
    public List<Map<String, Object>> postArrayBody(String url, String json) {
        List<Map<String, Object>> temp = null;
        StringBuilder urlBuilder = new StringBuilder(goodsPharmacistUrl);
        try {
            url = urlBuilder.toString();

            Headers.Builder headers = new Headers.Builder()
                    .add("Content-Type", "application/json");
            ResponseBody body = super.post(url, json, headers.build());

            String value = "";
            if (body != null) {
                value = body.string();
            }
            log.info("get:return=" + value);
            if (StrUtil.isNotEmpty(value)) {
                temp = JsonUtil.getInstance().jsonToList(JSONArray.parseArray(value));
            }
        } catch (Exception e) {
            log.error("doGet Error > ", e);
        }
        return temp;
    }

}
