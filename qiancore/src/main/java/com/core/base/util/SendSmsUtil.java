package com.core.base.util;

import com.alibaba.fastjson.JSONObject;
import com.core.base.ConfigModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SendSmsUtil {




    //创蓝变量短信

    /**
     * @param msg    您的验证码为{$var}请在{$var}分钟内输入。感谢您对{$var}的支持，祝您生活愉快！
     * @param params 参数组的具体格式：参数组从左往右第一个为发送的手机号码，第二个为第一个变量，第三个为第二个变量，以此类推，变量的数目需要和内容里变量的数目（msg里的{$var}）一一对应。
     */
    public static Map<String, Object> sendCLVariableSms(String phone, String msg, String params) {
        //短信下发
        Map<String, String> map = new HashMap<>();
        map.put("account", ConfigModel.ACCOUNT);//API账号
        map.put("password", ConfigModel.PASSWORD);//API密码
        map.put("msg", String.format("%s%s", ConfigModel.FREESIGNNAME, msg));//短信内容
        map.put("params", String.format("%s,%s", phone, params));//手机号
        map.put("report", "true");//是否需要状态报告
        JSONObject js = (JSONObject) JSONObject.toJSON(map);
        return sendSmsByPost(ConfigModel.VARIABLEURL, js.toString());
    }

    private static Map<String, Object> sendSmsByPost(String path, String postContent) {
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(postContent.getBytes(StandardCharsets.UTF_8));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return JsonUtil.getInstance().jsonToMap(JSONObject.parseObject(sb.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, Object> map = sendCLVariableSms("13437194372", "动态验证码：{$var}，您正在登录，若非您本人操作，请忽略本短信", "7678");
        System.out.println("结果为:"+map);
    }


}
