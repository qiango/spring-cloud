package com.core.base.util;

import com.core.base.http.HttpUtil;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import okhttp3.ResponseBody;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.InputStream;

public class QiniuHttpUtil extends HttpUtil {

    private static volatile QiniuHttpUtil instance = null;

    private QiniuHttpUtil() {

    }

    public static QiniuHttpUtil getInstance() {
        synchronized (QiniuHttpUtil.class) {
            if (instance == null) {
                instance = new QiniuHttpUtil();
            }
        }
        return instance;
    }

    private static final String ACCESSKEY = "VRWDT5IyiB09QahiNfiGMlZWv4aqMproC3P58y4V";
    private static final String SECRETKEY = "C3ob2n_o4Wy9YgcWpXNFSgkRbVE9fiWMaTGrgL_8";

    public static String upToken(String bucket) {
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        log.info(bucket + " token:" + auth.uploadToken(bucket));
        return auth.uploadToken(bucket);
    }

    public String uploadFile(String bucket, String key, InputStream inputStream) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
//            StringMap putPolicy = new StringMap();
            String upToken = auth.uploadToken(bucket, key);
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "SUCCESS";
        } catch (Exception ex) {
            log.error("qniu error>>", ex);
        }
        return "FAIL";
    }


    public String uploadFile(String bucket, String key, String base64File) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
            String upToken = auth.uploadToken(bucket, key);
            byte[] bytes = Base64.decodeBase64(base64File);
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "SUCCESS";
        } catch (Exception ex) {
            log.error("qniu error>>", ex);
        }
        return "FAIL";
    }

    public boolean getFile(String fileUrl, String fileLocal) {
        boolean returnValue = false;
        try {
            ResponseBody body = super.get(fileUrl, null);
            returnValue = FileUtil.saveFile(fileLocal, body.byteStream());
        } catch (Exception e) {
            log.error("getFile Error > ", e);
        }
        return returnValue;
    }

    public InputStream getInputStream(String url) {
        ResponseBody body = super.get(url, null);
        return body.byteStream();
    }


}
