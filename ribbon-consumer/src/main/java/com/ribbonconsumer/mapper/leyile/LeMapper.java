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


    //    fans_num    bigint default 0 not null comment '粉丝数量',
//    focus_num   bigint default 0 not null comment '关注数量',
//    praise_num  bigint default 0 not null comment '获赞数量',
    //关注
    public void focusUser(long userid, long focusUserId) {
        String sql = "insert into user_focus (userid, focus_userid, create_time) values (?,?,?) ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(focusUserId);
        param.add(UnixUtil.getNowTimeStamp());
        insert(sql, param);
    }

    //取关
    public void cancleFocuse(long userid, long focusUserId) {
        String sql = "delete from user_focus where userid=? and focus_userid=? ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(focusUserId);
        update(sql, param);
    }

    public void updateAddNum(long userid, int type) {
        String sqlFans = "update user set fans_num=fans_num+1 where id=? ";
        String sqlFocus = "update user set focus_num=focus_num+1 where id=? ";
        String sqlPraise = "update user set praise_num=praise_num+1 where id=? ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        if (type == 2) {
            sqlFans = sqlFocus;
        } else if (type == 3) {
            sqlFans = sqlPraise;
        }
        update(sqlFans, param);
    }

    public void updateSubNum(long userid, int type) {
        String sqlFans = "update user set fans_num=fans_num-1 where id=? ";
        String sqlFocus = "update user set focus_num=focus_num-1 where id=? ";
        String sqlPraise = "update user set praise_num=praise_num-1 where id=? ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        if (type == 2) {
            sqlFans = sqlFocus;
        } else if (type == 3) {
            sqlFans = sqlPraise;
        }
        update(sqlFans, param);
    }



    //点赞作品
    public void praise(long userid, long contentId) {
        String sql = "insert into user_praise (userid, create_time, content_id) values (?,?,?) ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(UnixUtil.getNowTimeStamp());
        param.add(contentId);
        insert(sql, param);
    }

    //取消点赞
    public void canclePraise(long userid, long contentId) {
        String sql = "delete from user_praise where userid=? and content_id=?";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(contentId);
        update(sql, param);
    }

    public Map<String, Object> getUserId(long contentId) {
        String sql = "select userid from user_content where id=? ";
        List<Object> param = new ArrayList<>();
        param.add(contentId);
        return queryForMap(sql, param);
    }

    //作品评价
    public void insertEvaluation(long userid, long contentId, String content, long pid) {
        String sql = "insert into user_evaluation (userid, create_time, content_id, evaluation_content, pid,check_status) values (?,?,?,?,?,?)";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(UnixUtil.getNowTimeStamp());
        param.add(contentId);
        param.add(content);
        param.add(pid);
        param.add(CheckStatusEnum.Success.getCode());
        insert(sql, param);
    }

    //点赞评论
    public void updateEvaluation(long evaluationId) {
        String sql = "update user_evaluation set appoint_num=appoint_num+1 where id=? ";
        List<Object> param = new ArrayList<>();
        param.add(evaluationId);
        update(sql, param);
    }

}
