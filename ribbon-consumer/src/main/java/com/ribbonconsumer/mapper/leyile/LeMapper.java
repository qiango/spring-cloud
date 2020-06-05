package com.ribbonconsumer.mapper.leyile;

import com.core.base.enumutil.CheckStatusEnum;
import com.core.base.mapper.BaseMapper;
import com.core.base.util.UnixUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LeMapper extends BaseMapper {

    public Map<String, Object> getUserOpen(String openid) {
        String sql = "select id,userid from user_open where openid=? ";
        List<Object> param = new ArrayList<>();
        param.add(openid);
        return queryForMap(sql, param);
    }

    public void insertUserOpen(String openid, String unionid, String sessionkey) {
        String sql = "insert into user_open (openid, unionid, sessionkey, create_time) values (?,?,?,?) ";
        List<Object> param = new ArrayList<>();
        param.add(openid);
        param.add(unionid);
        param.add(sessionkey);
        param.add(UnixUtil.getNowTimeStamp());
        insert(sql, param);
    }

    public void updateSessionKey(String sessionKey, String openid) {
        String sql = "update user_open set sessionkey=? where openid=? ";
        List<Object> param = new ArrayList<>();
        param.add(sessionKey);
        param.add(openid);
        update(sql, param);
    }

    public void updateUserOpen(long userid, String openId) {
        String sql = "update user_open set userid=? where openid=?";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(openId);
        update(sql, param);
    }

    public Map<String, Object> getUserId(String phone) {
        String sql = "select id from user where phone=? and ifnull(delflag,0)=0 ";
        List<Object> param = new ArrayList<>();
        param.add(phone);
        return queryForMap(sql, param);
    }

    public long insertUser(String url, String phone, String name, String happyNo, String gender) {
        String sql = "insert into user (phone, name, headpic, happy_no, create_time,gender) values (?,?,?,?,?,?)";
        List<Object> param = new ArrayList<>();
        param.add(phone);
        param.add(name);
        param.add(url);
        param.add(happyNo);
        param.add(UnixUtil.getNowTimeStamp());
        param.add(gender);
        return insert(sql, param, "id");
    }

    //我的信息
    public Map<String, Object> getUserInfoMation(long userid) {
        String sql = "select id,gender,name,headpic,happy_no happyNo,fans_num fansNum,focus_num focusNum,introduce from user where id=? ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        return queryForMap(sql, param);
    }

    //更新自我介绍
    public void updateIntroduce(long userid, String introduce) {
        String sql = "update user set introduce=? where id=? ";
        List<Object> param = new ArrayList<>();
        param.add(introduce);
        param.add(userid);
        update(sql, param);
    }

    //发布作品
    public void insertContent(long userid, String content, long meterialId) {
        String sql = "insert into user_content (userid, create_time, content, material_id) values (?,?,?,?) ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(UnixUtil.getNowTimeStamp());
        param.add(content);
        param.add(meterialId);
        insert(sql, param);
    }

    //推荐作品列表
    public List<Map<String, Object>> getContentList(List<Integer> classfyIds, int pageSize, int pageIndex) {
        String sql = "select uc.create_time    time," +
                "       uc.content," +
                "       uc.evaluation_num evaluationNum," +
                "       uc.praise_num     praiseNum," +
                "       uc.forwarding_num forwardingNum," +
                "       cm.meterial_url   meterialUrl," +
                "       u.name," +
                "       u.headpic" +
                " from user_content uc" +
                "       left join user u on uc.userid = u.id" +
                "       left join content_material cm on uc.material_id = cm.id" +
                " where check_status = :status";
        Map<String, Object> param = new HashMap<>();
        param.put("status", CheckStatusEnum.Success.getCode());
        if (!classfyIds.isEmpty()) {
            param.put("classfyId", classfyIds);
            sql += " and uc.classify_id in (:classfyId)";
        }
        return queryForList(pageNameSql(sql, "order by rand()"), pageParams(param, pageIndex, pageSize));
    }


}
