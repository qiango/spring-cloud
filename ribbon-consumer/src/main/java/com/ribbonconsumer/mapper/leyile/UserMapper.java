package com.ribbonconsumer.mapper.leyile;

import com.core.base.mapper.BaseMapper;
import com.core.base.util.ModelUtil;
import com.core.base.util.UnixUtil;
import com.ribbonconsumer.config.ConfigModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserMapper extends BaseMapper {

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
        Map<String, Object> map = queryForMap(sql, param);
        if (null != map) {
            map.put("headpic", ConfigModel.WEBURL + ModelUtil.getStr(map, "headpic"));
        }
        return map;
    }

    //我点赞过的作品
    public List<Map<String, Object>> getMyLoveContentList(long userid) {
        String sql = "select uc.praise_num praiseNum, uc.content" +
                " from user_praise up" +
                "       left join user_content uc on up.content_id = uc.id" +
                " where up.userid = ? and uc.check_status=1 ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        return queryForList(sql, param);
    }

    //我发布过的作品
    public List<Map<String, Object>> getMyCreateContentList(long userid) {
        String sql = "select praise_num praiseNum,content from user_content where userid=? and check_status=1 ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        return queryForList(sql, param);
    }

    //我关注的人
    public List<Map<String, Object>> myFocusList(long userid) {
        String sql = "select u.name, u.headpic" +
                "from user_focus uf" +
                "       left join user u on uf.focus_userid = u.id" +
                "where uf.userid = ?";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        return queryForList(sql, param);
    }

    //我的粉丝
    public List<Map<String, Object>> myFanList(long userid) {
        String sql = "select u.name, u.headpic" +
                "from user_focus uf" +
                "       left join user u on uf.userid = u.id" +
                "where uf.focus_userid = ?";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        return queryForList(sql, param);
    }

    //更新自我介绍
    public void updateIntroduce(long userid, String headpic, String name, String introduce, long birthDay, int gender) {
        String sql = "update user set introduce=?,name=?,headpic=?,birthday=?,gender=? where id=? ";
        List<Object> param = new ArrayList<>();
        param.add(introduce);
        param.add(name);
        param.add(headpic);
        param.add(birthDay);
        param.add(gender);
        param.add(userid);
        update(sql, param);
    }

    public List<Long> getUserIds(long sessionId) {
        String sql = "select userid from user_session_group where sessionid=? and ifnull(delflag,0)=0 ";
        return jdbcTemplate.queryForList(sql, new Object[]{sessionId}, Long.class);
    }

    public void insertAnswer(long userid, long sessionid, String content) {
        String sql = "insert into user_answer (userid, sessionid, content, create_time) values (?,?,?,?) ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(sessionid);
        param.add(content);
        param.add(UnixUtil.getNowTimeStamp());
        insert(sql, param);
    }

    public long insertSession(long userid, String name) {
        String sql = "insert into user_session (userid, groupName, create_time) values (?,?,?) ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(name);
        param.add(UnixUtil.getNowTimeStamp());
        return insert(sql, param, "id");
    }

    public void insertGroup(long sessionid, long userid) {
        String sql = "insert into user_session_group (sessionid, userid, create_time) values (?,?,?)";
        List<Object> param = new ArrayList<>();
        param.add(sessionid);
        param.add(userid);
        param.add(UnixUtil.getNowTimeStamp());
        insert(sql, param);
    }

    public List<Map<String, Object>> getNoList(List<Long> userids) {
        String sql = "select happy_no no from user where id in (:userids) ";
        Map<String, Object> param = new HashMap<>();
        param.put("userids", userids);
        return queryForList(sql, param);
    }

    //bearing为1，消息在右边，为本人发的
    public List<Map<String, Object>> getContentList(long sessionId, long userid, int pageSize, int pageIndex) {
        String sql = "select ua.userid, ua.content,ua.answer_type type, u.headpic," +
                "  u.name, if(ua.userid = ?, 1, 0) bearing,ua.create_time createTime " +
                " from user_answer ua" +
                "            left join user u on ua.userid = u.id" +
                " where ua.sessionid = ?" +
                "  and ifnull(ua.delflag, 0) = 0  ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(sessionId);
        return queryForList(pageSql(sql, " order by ua.create_time desc "), pageParams(param, pageIndex, pageSize));
    }

    public List<Map<String, Object>> getSessionList(long userid) {
        String sql = "select usg.id," +
                "       usg.sessionid," +
                "       us.groupName," +
                "       usg.head_pic      headPic," +
                "       usg.message_count count," +
                "       ua.create_time    createTime," +
                "       ua.content," +
                "       ua.answer_type type " +
                " from user_session_group usg" +
                "            left join user_session us on usg.sessionid = us.id and ifnull(us.delflag, 0) = 0" +
                "            left join user_answer ua on us.last_answer_id = ua.id" +
                " where usg.userid = ?" +
                "  and ifnull(usg.delflag, 0) = 0 ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        return queryForList(sql, param);
    }
}
