package com.ribbonconsumer.mapper.leyile;

import com.core.base.baseenum.CheckStatusEnum;
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
public class ProductMapper extends BaseMapper {

    //发布作品
    public void insertContent(long userid, String title, String content, long meterialId) {
        String sql = "insert into user_content (userid, title,create_time, check_status,content, material_id) values (?,?,?,?,?) ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(title);
        param.add(UnixUtil.getNowTimeStamp());
        param.add(CheckStatusEnum.Success.getCode());
        param.add(content);
        param.add(meterialId);
        insert(sql, param);
    }

    //推荐作品列表
    public List<Map<String, Object>> getContentList(long userid, int pageSize, int pageIndex) {
        String sql = "select uc.create_time    time," +
                "       uc.id," +
                "       uc.content," +
                "       uc.evaluation_num evaluationNum," +
                "       uc.praise_num     praiseNum," +
                "       uc.forwarding_num forwardingNum," +
                "       cm.meterial_url   meterialUrl," +
                "       u.name," +
                "       u.headpic," +
                "       uc.music_url      musicUrl," +
                "       ifnull(uf.id,0)   isFriend  " +
                " from user_content uc" +
                "       left join user u on uc.userid = u.id" +
                "       left join user_focus uf on u.id=uf.focus_userid and uf.userid=:userid " +
                "       left join content_material cm on uc.material_id = cm.id" +
                "       left join user_behavior_records ubr on uc.classify_id=ubr.classfy_id and ubr.userid=:userid" +
                "       left join user_footprint uf2 on uc.id=uf2.productid and uf2.userid=:userid " +
                " where check_status = :status" +
                "   and uf2.id is null ";
        Map<String, Object> param = new HashMap<>();
        param.put("status", CheckStatusEnum.Success.getCode());
        param.put("userid", userid);
        List<Map<String, Object>> list = queryForList(pageNameSql(sql, " order by ubr.degree_of_preference desc,rand() "), pageParams(param, pageIndex, pageSize));
        for (Map<String, Object> map : list) {
            map.put("meterialUrl", ConfigModel.IMAGEURL + ModelUtil.getStr(map, "meterialUrl"));
            map.put("musicUrl", ConfigModel.MUSIC + ModelUtil.getStr(map, "musicUrl"));
            map.put("headpic", ConfigModel.IMAGEURL + ModelUtil.getStr(map, "headpic"));
        }
        return list;
    }

    public void insertFootprint(long userid, long productid) {
        String sql = "insert into user_footprint (userid, productid, create_time) values (?,?,?) ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        param.add(productid);
        param.add(UnixUtil.getNowTimeStamp());
        insert(sql, param);
    }

    public long getClassfyList(long id) {
        String sql = "select classify_id classfyId from user_content where id=? ";
        List<Object> list = new ArrayList<>();
        list.add(id);
        return jdbcTemplate.queryForObject(sql, list.toArray(), Long.class);
    }

    public List<Map<String, Object>> getClassfyMap(long userid) {
        String sql = "select classfy_id classfyId,degree_of_preference preference from user_behavior_records where userid=? ";
        return queryForList(sql, userid);
    }

    public Integer getContentCount(List<Long> classfyIds) {
        String sql = "select count(uc.id) count " +
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
        return namedJdbcTemplate.queryForObject(sql, param, Integer.class);
    }

    //作品评价列表
    public List<Map<String, Object>> getEvaluationList(long contentId) {
        String sql = "select ue.evaluation_content evaluationContent," +
                "       ue.id," +
                "       u.name," +
                "       u.headpic," +
                "       ue.create_time        createTime," +
                "       ue.pid," +
                "       appoint_num           num" +
                " from user_evaluation ue" +
                "       left join user u on ue.userid = u.id" +
                " where content_id = ?" +
                "  and check_status = 1" +
                "  order by appoint_num desc ,u.create_time desc ";
        List<Object> param = new ArrayList<>();
        param.add(contentId);
        return queryForList(sql, param);
    }

    public List<Map<String, Object>> getClassifyList(long userid) {
        String sql = "select cc.id, cc.name, cc.image, cc.pid, if(ufc.id is null, 0, 1) isFocus" +
                " from content_classify cc" +
                "       left join user_focus_classfy ufc on cc.id = ufc.classfy_id and ufc.userid = ? and cc.delflag = 0" +
                " where cc.delflag = 0  ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        return queryForList(sql, param);
    }

    public List<Map<String, Object>> getFocusList(long userid) {
        String sql = "select cc.id, cc.name, cc.image" +
                " from user_focus_classfy ufc" +
                "       left join content_classify cc on ufc.classfy_id = cc.id" +
                " where ufc.userid = ?" +
                "  and cc.delflag = 0 ";
        List<Object> param = new ArrayList<>();
        param.add(userid);
        return queryForList(sql, param);
    }


}
