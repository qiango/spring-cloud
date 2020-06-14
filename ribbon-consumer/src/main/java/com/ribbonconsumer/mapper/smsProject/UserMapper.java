//package com.ribbonconsumer.mapper.smsProject;
//
//import com.core.base.mapper.BaseMapper;
//import com.core.base.util.MD5Encrypt;
//import com.core.base.util.UnixUtil;
//import com.ribbonconsumer.thirdparty.enumutil.PayTypeEnum;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class UserMapper extends BaseMapper {
//
//    public long registerer(String phone, String name, String password) {
//        String sql = "insert into user (phone, name,password,create_time) values (?,?,?,?)";
//        List<Object> param = new ArrayList<>();
//        param.add(phone);
//        param.add(name);
//        param.add(MD5Encrypt.encrypt(password));
//        param.add(UnixUtil.getNowTimeStamp());
//        return insert(sql, param, "id");
//    }
//
//    public Map<String, Object> getUserInfo(String phone) {
//        String sql = "select id, phone, user_password password from user where phone=? and ifnull(delflag,0)=0 ";
//        return queryForMap(sql, phone);
//    }
//
//    public void insertWallt(long userid) {
//        String sql = "insert into user_wallet (wallet_money, userid, create_time) " +
//                "values (?, ?, ?) ";
//        List<Object> param = new ArrayList<>();
//        param.add(0);
//        param.add(userid);
//        param.add(UnixUtil.getNowTimeStamp());
//        insert(sql, param);
//    }
//
//    //充值数量
//    public void recharWallet(long num, long userid) {
//        String sql = "update user_wallet set total_num=total_num+?,can_use_num=can_use_num+? where userid=?";
//        List<Object> param = new ArrayList<>();
//        param.add(num);
//        param.add(num);
//        param.add(userid);
//        update(sql, param);
//    }
//
//    public void insertRecharge(long userid, long num) {
//        String sql = "insert into use_recharge (userid, pay_time, pay_type, num, pay_status) values (?,?,?,?,?) ";
//        List<Object> param = new ArrayList<>();
//        param.add(userid);
//        param.add(UnixUtil.getNowTimeStamp());
//        param.add(PayTypeEnum.Admin.getCode());
//        param.add(num);
//        param.add(1);//1成功2失败
//        insert(sql, param);
//    }
//}
