package com.core.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SendSms {

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    //调用方式
    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        // 2019-12-31
        calendar.set(2019, Calendar.DECEMBER, 31);
        Date strDate1 = calendar.getTime();
        // 2020-01-01
        calendar.set(2020, Calendar.JANUARY, 1);
        Date strDate2 = calendar.getTime();
        // 大写 YYYY
        SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println("2019-12-31 转 YYYY/MM/dd 格式: " + formatYYYY.format(strDate1));
        System.out.println("2020-01-01 转 YYYY/MM/dd 格式: " + formatYYYY.format(strDate2));
        // 小写 YYYY
        SimpleDateFormat formatyyyy = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("2019-12-31 转 yyyy/MM/dd 格式: " + formatyyyy.format(strDate1));
        System.out.println("2020-01-01 转 yyyy/MM/dd 格式: " + formatyyyy.format(strDate2));
    }
}
