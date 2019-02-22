package com.ribbonconsumer.mapper;

import com.ribbonconsumer.base.mapper.BaseMapper;
import com.ribbonconsumer.base.util.ModelUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qian.wang
 * @description
 * @date 2019/1/18
 */
@Repository
public class HomePageMapper extends BaseMapper {

    //总访问量
    public int getYesterDayNum(long startTime, long endTime) {
        String sql = "select sum(count) sumcount" +
                "      from (select count(*) count" +
                "      from (select ui.userid" +
                "            from user_integral_detailed ui" +
                "            where create_time BETWEEN ?" +
                "                      AND ?" +
                "            GROUP BY userid) a" +
                "      UNION" +
                "      select count(*) count" +
                "      from (select di.doctorid" +
                "            from doctor_integral_detailed di" +
                "            where create_time BETWEEN ?" +
                "                      AND ?" +
                "            GROUP BY doctorid) a) b";
        List<Object> list = new ArrayList<>();
        list.add(startTime);
        list.add(endTime);
        list.add(startTime);
        list.add(endTime);
        return ModelUtil.getInt(queryForMap(sql, list),"sumcount");
    }

    //会员人次
    public int getTimeAddVip(long startTime, long endTime) {
        String sql = "select count(id) count from user_member where ifnull(delflag,0)=0 and is_enabled=1 and is_expire=1 and create_time between ? and ?";
        Map<String, Object> map = queryForMap(sql, startTime, endTime);
        return ModelUtil.getInt(map, "count");
    }

    //付费人次
    public int getUseMoney(long startTime, long endTime) {
        String sql = "SELECT count(*) count " +
                " FROM (SELECT userid" +
                "      FROM doctor_problem_order dpo" +
                "      WHERE create_time BETWEEN ?" +
                "                AND ?" +
                "        AND ifnull(delflag, 0) = 0" +
                "        AND paystatus = 1" +
                "      GROUP BY dpo.userid" +
                "      UNION" +
                "      SELECT userid" +
                "      FROM doctor_phone_order dpo" +
                "      WHERE create_time BETWEEN ?" +
                "                AND ?" +
                "        AND ifnull(delflag, 0) = 0" +
                "        AND paystatus = 1" +
                "      GROUP BY dpo.userid" +
                "      UNION" +
                "      SELECT userid" +
                "      FROM doctor_video_order dpo" +
                "      WHERE create_time BETWEEN ?" +
                "                AND ?" +
                "        AND ifnull(delflag, 0) = 0" +
                "        AND paystatus = 1" +
                "      GROUP BY dpo.userid" +
                "      UNION" +
                "      SELECT userid" +
                "      FROM vip_order dpo" +
                "      WHERE create_time BETWEEN ?" +
                "                AND ?" +
                "        AND ifnull(delflag, 0) = 0" +
                "        AND pay_status = 1" +
                "      GROUP BY dpo.userid" +
                "      UNION" +
                "      SELECT userid" +
                "      FROM green_order dpo" +
                "      WHERE create_time BETWEEN ?" +
                "                AND ?" +
                "        AND ifnull(delflag, 0) = 0" +
                "        AND paystatus = 1" +
                "      GROUP BY dpo.userid) count";
        List<Object> list = new ArrayList<>();
        list.add(startTime);
        list.add(endTime);
        list.add(startTime);
        list.add(endTime);
        list.add(startTime);
        list.add(endTime);
        list.add(startTime);
        list.add(endTime);
        list.add(startTime);
        list.add(endTime);
        Map<String, Object> map = queryForMap(sql, list);
        return ModelUtil.getInt(map, "count");
    }


}
