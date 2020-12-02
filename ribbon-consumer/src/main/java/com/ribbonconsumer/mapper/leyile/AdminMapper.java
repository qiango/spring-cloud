package com.ribbonconsumer.mapper.leyile;

import com.core.base.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Authod: wangqian
 * @Date: 2020-12-02  13:54
 */
@Repository
public class AdminMapper extends BaseMapper {

    public List<Map<String, Object>> getClassify() {
        String sql = "select id,name,create_time time,pid from content_classify where ifnull(delflag,0)=0 ";
        return queryForList(sql);
    }

    public List<Map<String, Object>> getEvaluationByContent(List<Long> contentId) {
        String sql = "select ue.id," +
                " ue.evaluation_content    evaluationContent," +
                " ue.create_time           createTime," +
                " ue.check_status          status," +
                " u.name," +
                " uc.title," +
                " uePid.evaluation_content pidContent," +
                " ue.pid," +
                " uc.id contentId " +
                " from user_evaluation ue" +
                " left join user_content uc on ue.content_id = uc.id" +
                " left join user_evaluation uePid on ue.pid = uePid.id" +
                " left join user u on ue.userid = u.id" +
                " where uc.id in (:ids)" +
                " order by ue.create_time desc";
        Map<String, Object> param = new HashMap<>();
        param.put("ids", contentId);
        return queryForList(sql, param);
    }

    public List<Map<String, Object>> getUserList() {
        String sql = "select id," +
                " phone," +
                " gender," +
                " name," +
                " headpic," +
                " happy_no    happyNo," +
                " fans_num    fansNum," +
                " focus_num   focusNum," +
                " introduce," +
                " create_time time" +
                " from user" +
                " where ifnull(delflag, 0) = 0 ";
        return queryForList(sql);
    }

    public List<Map<String, Object>> getContentList() {
        String sql = "select uc.id," +
                " uc.content," +
                " uc.praise_num praiseNum," +
                " uc.forwarding_num forwardingNum," +
                " uc.evaluation_num evaluationNum," +
                " uc.check_status checkStatus," +
                " uc.music_url musicUrl," +
                " uc.title," +
                " uc.picture," +
                " uc.create_time time," +
                " u.name         userName" +
                " from user_content uc" +
                " left join user u on uc.userid = u.id" +
                " order by uc.create_time desc ";
        return queryForList(sql);
    }
}
