package com.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigModel {

    public static String BASEFILEPATH;

    @Value("${base.filepath}")
    public void setBASEFILEPATH(String value) {
        BASEFILEPATH = value;
    }


    public static String ISONLINE;

    @Value("${base.isonline}")
    public void setISONLINE(String value) {
        ISONLINE = value;
    }

    public static String IMAGEURL;

    @Value("${base.imageurl}")
    public void setIMAGEURL(String value) {
        IMAGEURL = value;
    }




}
