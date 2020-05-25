package com.core.base.http;



import com.core.base.util.StrUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public abstract class HttpUtil {

    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    protected static Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private OkHttpClient okHttpClient;

    private static class TrustAllHostnameVerifier implements HostnameVerifier {

        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            log.error("ssl error ", e);
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    protected HttpUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //参数一：TimeOut等待链接的的时间TimeOut，参数二：TimeUnit时间单位
        builder.connectTimeout(10, TimeUnit.SECONDS);//点击POST是否有响应的时间
        builder.writeTimeout(30, TimeUnit.SECONDS);//请求POST数据写的时间
        builder.readTimeout(30, TimeUnit.SECONDS);//请求POST数据都的时间
        builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
        builder.hostnameVerifier(new TrustAllHostnameVerifier());

        okHttpClient = builder.build();
    }

    protected ResponseBody get(String url, HttpParamModel params, Headers headers) {
        if (params == null) {
            params = new HttpParamModel();
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (!StrUtil.isEmpty(url) && !url.contains("?")) {
            urlBuilder.append("?");
        }
        for (Map.Entry<String, Object> entry : params.getMap().entrySet()) {
            urlBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        url = urlBuilder.toString();
        return get(url, headers);
    }

    protected ResponseBody get(String url, Headers headers) {
        try {
            log.info("params:url=" + url);


            Request.Builder builder = new Request.Builder()
                    .get()
                    .url(url);
            if (headers != null) {
                builder.headers(headers);
            }

            Request request = builder.build();

            Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            if (response.isSuccessful()) {
                log.info("get:return=" + response.body());
                return response.body();
            } else if (response.code() == 404) {//审核状态错误返回码
                log.info("get:return=" + response.body());
                return response.body();
            } else {
                throw new Exception("网络请求错误 》" + response);
            }
        } catch (Exception e) {
            log.error("get Error > ", e);
            return null;
        }
    }

    protected ResponseBody put(String url, HttpParamModel params, Headers headers) {
        try {
            if (params == null) {
                params = new HttpParamModel();
            }
            log.info("params:url=" + url);
            log.info("params:data=" + params.toString());

            Request request = new Request.Builder()
                    .put(params.getForm().build())
                    .url(url)
                    .headers(headers)
                    .build();

            Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            if (response.isSuccessful()) {
                log.info("get:return=" + response.body());
                return response.body();
            } else {
                throw new Exception("网络请求错误 》" + response);
            }
        } catch (Exception e) {
            log.error("put Error > ", e);
            return null;
        }
    }

    protected ResponseBody post(String url, HttpParamModel params, Headers headers) {
        try {
            if (params == null) {
                params = new HttpParamModel();
            }
            if (headers == null) {
                headers = new Headers.Builder().build();
            }

            log.info("params:url=" + url);
            log.info("params:data=" + params.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(params.getForm().build())
                    .headers(headers)
                    .build();

            Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            if (response.isSuccessful()) {
                log.info("get:return=" + response.body());
                return response.body();
            } else {
                throw new Exception("网络请求错误 》" + response);
            }
        } catch (Exception e) {
            log.error("post Error > ", e);
            return null;
        }
    }

    protected ResponseBody postMutl(String url, HttpMultParamModel params, Headers headers) {
        try {
            if (params == null) {
                params = new HttpMultParamModel();
            }
            if (headers == null) {
                headers = new Headers.Builder().build();
            }

            log.info("params:url=" + url);
            log.info("params:data=" + params.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(params.getForm().build())
                    .headers(headers)
                    .build();

            Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            if (response.isSuccessful()) {
                log.info("get:return=" + response.body());
                return response.body();
            } else {
                throw new Exception("网络请求错误 》" + response);
            }
        } catch (Exception e) {
            log.error("post Error > ", e);
            return null;
        }
    }

    protected ResponseBody post(String url, String params, Headers headers) {
        try {
            if (params == null) {
                params = "";
            }

            log.info("params:url=" + url);
            log.info("params:data=" + params);

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse(HttpUtil.CONTENT_TYPE), params))
                    .headers(headers)
                    .build();

            Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            if (response.isSuccessful()) {
                log.info("get:return=" + response.body());
                return response.body();
            } else {
                throw new Exception("网络请求错误 》" + response);
            }
        } catch (Exception e) {
            log.error("post Error > ", e);
            return null;
        }
    }

    protected ResponseBody delete(String url, HttpParamModel params) {
        try {
            if (params == null) {
                params = new HttpParamModel();
            }

            log.info("params:url=" + url);
            log.info("params:data=" + params.toString());

            Request request = new Request.Builder()
                    .delete()
                    .url(url)
                    .build();

            Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            if (response.isSuccessful() || response.code() == 400) {
                log.info("get:return=" + response.body());
                return response.body();
            } else {
                throw new Exception("网络请求错误 》" + response);
            }
        } catch (Exception e) {
            log.error("post Error > ", e);
            return null;
        }
    }

    protected ResponseBody getStatus(String url) {
        try {
            Request.Builder builder = new Request.Builder()
                    .get()
                    .url(url);
            Request request = builder.build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful() || response.code() == 404) {
                log.info("get:return=" + response.body());
                return response.body();
            } else {
                throw new Exception("网络请求错误 》" + response);
            }
        } catch (Exception e) {
            log.error("get Error > ", e);
            return null;
        }
    }


//    /**
//     * 得到文件的流
//     *
//     * @param url 文件地址
//     */
//    public InputStream getInputStream(String url) {
//        InputStream temp = null;
//        try {
//            log.info("params:url=" + url);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .build();
//            Call call = okHttpClient.newCall(request);
//            Response response = call.execute();
//            if (response.isSuccessful()) {
//                ResponseBody body = response.body();
//                if (body != null) {
//                    temp = body.byteStream();
//                }
//            }
//        } catch (Exception e) {
//            log.error("doGet Error > ", e);
//        }
//        return temp;
//    }

//    public Map<String, Object> get(String url, HttpParamModel params) {
//        return get(url, params, true);
//    }
//
//    public Map<String, Object> get(String url) {
//        return get(url, null, false);
//    }
//
//    private Map<String, Object> get(String url, HttpParamModel params, boolean isput) {
//        Map<String, Object> temp = null;
//        try {
//            if (params == null) {
//                params = new HttpParamModel();
//            }
//
//            log.info("params:url=" + url);
//            log.info("params:data=" + params.toString());
//
//            Request.Builder builder = new Request.Builder();
//            builder.url(url);
//            if (isput) {
//                builder.put(params.getForm().build());
//            }
//
//            Request request = builder.build();
//
//            Call call = okHttpClient.newCall(request);
//
//            Response response = call.execute();
//            if (response.isSuccessful()) {
//                ResponseBody body = response.body();
//                String value = "";
//                if (body != null) {
//                    value = body.string();
//                }
//                log.info("get:return=" + value);
//
//                temp = JsonUtil.getInstance().jsonToMap(JSONObject.parseObject(value));
//            }
//        } catch (Exception e) {
//            log.error("doGet Error > ", e);
//        }
//        return temp;
//    }

//    public void post(String url, HttpParamModel params) {
//        if (params == null) {
//            params = new HttpParamModel();
//        }
//        nPost(url, params);
//    }
//
//    /**
//     * post请求（用于获取微信接口的）
//     *
//     * @param url    接口地址
//     * @param params 接口参数
//     */
//    public Map<String, Object> post(String url, String params) {
//        if (params == null) {
//            params = "";
//        }
//        return nPost(url, params);
//    }
//
//    public Map<String, Object> nPost(String url, Object params) {
//        Map<String, Object> temp = null;
//        try {
//            log.info("params:url=" + url);
//            log.info("params:data=" + params.toString());
//
//            RequestBody rbody = null;
//            if (params instanceof String) {
//                rbody = RequestBody.create(MediaType.parse(HttpUtil.CONTENT_TYPE), (String) params);
//            } else if (params instanceof HttpParamModel) {
//                rbody = ((HttpParamModel) params).getForm().build();
//            }
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(rbody)
//                    .build();
//
//            Call call = okHttpClient.newCall(request);
//
//            Response response = call.execute();
//            if (response.isSuccessful()) {
//                ResponseBody body = response.body();
//                String value = "";
//                if (body != null) {
//                    value = body.string();
//                }
//                log.info("post:return=" + value);
//                temp = JsonUtil.getInstance().jsonToMap(JSONObject.parseObject(value));
//            }
//        } catch (Exception e) {
//            log.error("doGet Error > ", e);
//        }
//        return temp;
//    }

}
