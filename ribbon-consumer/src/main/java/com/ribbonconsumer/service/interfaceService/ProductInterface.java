package com.ribbonconsumer.service.interfaceService;

import com.core.base.util.ModelUtil;
import com.ribbonconsumer.mapper.leyile.LeMapper;

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

}
