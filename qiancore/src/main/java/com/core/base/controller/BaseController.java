package com.core.base.controller;

import com.core.base.util.ModelUtil;
import com.core.base.util.ValueUtil;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public abstract class BaseController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 接口成功设置返回值
     *
     * @param value 原始数据
     * @return java.com.syhdoctor.webserver.api.util.Map
     */
    protected Map<String, Object> setOkResult(Map<String, Object> value) {
        return setResult(value, "成功", 1);
    }

    /**
     * 接口成功设置返回值
     *
     * @param value 原始数据
     * @param msg   返回message消息
     * @return java.com.syhdoctor.webserver.api.util.Map
     */
    protected Map<String, Object> setOkResult(Map<String, Object> value, String msg) {
        return setResult(value, msg, 1);
    }

    protected Object toJsonOk(Object value) {
        return ValueUtil.toJson(HttpStatus.SC_OK, value);
    }

    protected Object toError(String message) {
        return ValueUtil.isError(message);
    }

    protected Object validString(Map<String, Object> param, String... key) {
        for (String s : key) {
            if (null == ModelUtil.getStr(param, s)) {
                return toError("参数" + s + "不能为空!");
            }
        }
        return null;
    }

    protected Object validLong(Map<String, Object> param, String... key) {
        for (String s : key) {
            if (0 == ModelUtil.getLong(param, s)) {
                return toError("参数" + s + "不能为0!");
            }
        }
        return null;
    }

    /**
     * 接口失败设置返回值
     *
     * @param value 原始数据
     * @param msg   返回message消息
     * @return java.com.syhdoctor.webserver.api.util.Map
     */
    protected Map<String, Object> setErrorResult(Map<String, Object> value, String msg) {
        return setResult(value, msg, -1);
    }

    protected Map<String, Object> setResult(Map<String, Object> value, String msg, int def) {
        value.put("result", def);
        value.put("message", msg);
        return value;
    }

    /**
     * 下载
     *
     * @param file
     * @return
     * @throws IOException
     */
    protected ResponseEntity<InputStreamResource> responseEntity(FileSystemResource file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", StrUtil.formatIso(file.getFilename())));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

}
