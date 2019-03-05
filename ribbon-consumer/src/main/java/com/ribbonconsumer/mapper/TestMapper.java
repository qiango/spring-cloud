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
public class TestMapper extends BaseMapper {


    public int getNum(){
        String sql="select num from testmq where id=1";
        return ModelUtil.getInt(queryForMap(sql),"num");
    }

    public void updateNum(){
        String sql="update testmq set num=num-1 where id=1";
        update(sql);
    }

    public void updateNumStatus(){
        String sql="update testmq set num=100 where id=1";
        update(sql);
    }


}
