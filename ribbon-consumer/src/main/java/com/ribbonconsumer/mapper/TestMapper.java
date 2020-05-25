package com.ribbonconsumer.mapper;

import com.core.base.mapper.BaseMapper;
import com.core.base.util.ModelUtil;
import org.springframework.stereotype.Repository;

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
