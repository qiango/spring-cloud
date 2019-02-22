package com.ribbonconsumer.service;

import com.ribbonconsumer.base.exception.QianException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TestService {

    @Value("${server.port}")
    private String port;

    public String sayHello(){
       return "my is customer"+port;
    }

    public Object getTest(){
        String a="";
        if(a.equals("")){
            throw new QianException("测试异常ssssss");
        }
        return null;
    }

    public Object nullTest(){
        List list=null;
        list.get(0);
        return "true";
    }

}
