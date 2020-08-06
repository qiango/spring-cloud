package com.ribbonconsumer.service.interfaceService.impl;

import com.ribbonconsumer.mapper.leyile.LeMapper;
import com.ribbonconsumer.service.interfaceService.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Authod: wangqian
 * @Date: 2020-08-06  18:37
 */
public class ProductImpl implements ProductInterface {

    private LeMapper leMapper;

    @Autowired
    public ProductImpl(LeMapper leMapper) {
        this.leMapper = leMapper;
    }

    public void updateRecords(long userid, long classifyId, int preference) {
        long recordId = leMapper.getRecordId(userid, classifyId);
        if (recordId == 0) {
            leMapper.insertRecords(userid, classifyId, preference);
        } else {
            leMapper.updateRecords(preference, recordId);
        }
    }
}
