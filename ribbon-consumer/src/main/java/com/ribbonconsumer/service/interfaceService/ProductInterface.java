package com.ribbonconsumer.service.interfaceService;

import com.core.base.util.ModelUtil;
import com.ribbonconsumer.mapper.leyile.LeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Authod: wangqian
 * @Date: 2020-08-06  18:36
 */

public interface ProductInterface {

    default void updateRecords(LeMapper leMapper, long userid, long classifyId, int preference) {
        long recordId = ModelUtil.getLong(leMapper.getRecordId(userid, classifyId), "id");
        if (recordId == 0) {
            leMapper.insertRecords(userid, classifyId, preference);
        } else {
            leMapper.updateRecords(preference, recordId);
        }
    }

    default void initMap(long pid, Map<Long, List<Map<String, Object>>> tempMap, Map<String, Object> map) {
        if (tempMap.containsKey(pid)) {
            tempMap.get(pid).add(map);
        } else {
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(map);
            tempMap.put(pid, list);
        }
    }

}
