package com.ribbonconsumer.mapper.smsProject;

import com.ribbonconsumer.base.mapper.BaseMapper;
import com.ribbonconsumer.base.util.MD5Encrypt;
import com.ribbonconsumer.base.util.UnixUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserMapper extends BaseMapper {

    public long registerer(String phone, String name, String password) {
        String sql = "insert into user (phone, name,password,create_time) values (?,?,?,?)";
        List<Object> param = new ArrayList<>();
        param.add(phone);
        param.add(name);
        param.add(MD5Encrypt.encrypt(password));
        param.add(UnixUtil.getNowTimeStamp());
        return insert(sql, param, "id");
    }

    public Map<String, Object> getUserInfo(String phone) {
        String sql = "select id, phone, user_password password from user where phone=? and ifnull(delflag,0)=0 ";
        return queryForMap(sql, phone);
    }

    public void insertWallt(long userid) {
        String sql = "insert into user_wallet (wallet_money, userid, create_time) " +
                "values (?, ?, ?) ";
        List<Object> param = new ArrayList<>();
        param.add(0);
        param.add(userid);
        param.add(UnixUtil.getNowTimeStamp());
        insert(sql, param);
    }
}
