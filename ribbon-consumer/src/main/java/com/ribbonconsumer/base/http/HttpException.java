package com.ribbonconsumer.base.http;


public class HttpException extends Exception {

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Integer code;

    private String message;

    public HttpException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public HttpException(String message) {
        super(message);
        this.code = -1;
        this.message = message;
    }
}
