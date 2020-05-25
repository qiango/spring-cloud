package com.ribbonconsumer.service;

import com.core.base.service.BaseService;
import com.core.base.util.UnixUtil;
import com.ribbonconsumer.mapper.HomePageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qian.wang
 * @description
 * @date 2019/1/18
 */
@Service
public class HomePageService extends BaseService {


    @Autowired
    private HomePageMapper homePageMapper;

    //底部统计
    public Map<String, Object> getFinalCount(long startTime, long endTime) {
        Map<String, Object> map = new HashMap<>();
        if (startTime == 0 && endTime == 0) {
            startTime = UnixUtil.getBeginDaySeven();
            endTime = UnixUtil.getNowTimeStamp();
        }
        int a = homePageMapper.getYesterDayNum(startTime, endTime);
        int b = homePageMapper.getTimeAddVip(startTime, endTime);
        int c = homePageMapper.getUseMoney(startTime, endTime);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("value", a);
        map1.put("name", "访问总人数");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("value", b);
        map2.put("name", "付费会员人数");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("value", c);
        map3.put("name", "付费人数");
        list.add(map1);
        list.add(map2);
        list.add(map3);
        map.put("allnumPercentage", 100);
        map.put("starttime", startTime);
        map.put("endtime", endTime);
        map.put("listnum", list);
        DecimalFormat df = new DecimalFormat("#.0");
        double d = 0;
        double e = 0;
        if(a!=0){
            e=Double.valueOf(df.format((double) c / a * 100));
            d=Double.valueOf(df.format((double) b / a * 100));
        }
        map.put("paynumPercentage", e);
        map.put("vipnumPercentage", d);
        return map;
    }

}
