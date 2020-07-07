package com.ribbonconsumer.service.leyile;

import com.core.base.service.BaseService;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.mapper.leyile.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService extends BaseService {

    private ProductMapper productMapper;


    @Autowired
    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    public List<Map<String, Object>> getEvaluationList(long contentId) {
        List<Map<String, Object>> evaluationList = productMapper.getEvaluationList(contentId);
        Map<Long, List<Map<String, Object>>> tempMap = new HashMap<>();
        evaluationList.forEach(stringObjectMap -> initMap(ModelUtil.getLong(stringObjectMap, "pid"), tempMap, stringObjectMap));
        for (Map<String, Object> map : evaluationList) {
            map.put("childList", tempMap.get(ModelUtil.getLong(map, "id")));
        }
        List<Map<String, Object>> maps = tempMap.get(0L);
        return maps == null ? new ArrayList<>() : maps;
    }


    private void initMap(long pid, Map<Long, List<Map<String, Object>>> tempMap, Map<String, Object> map) {
        if (tempMap.containsKey(pid)) {
            tempMap.get(pid).add(map);
        } else {
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(map);
            tempMap.put(pid, list);
        }
    }


    public void insertContent(long userid, String content, long meterialId) {
        productMapper.insertContent(userid, content, meterialId);
    }

    public List<Map<String, Object>> getContentList(long userid, int pageSize, int pageIndex) {
        List<Long> classfyIds = productMapper.getClassfyList(userid);
        return productMapper.getContentList(classfyIds, userid, pageSize, pageIndex);
    }

    public long getContentCount(long userid) {
        List<Long> classfyIds = productMapper.getClassfyList(userid);
        return productMapper.getContentCount(classfyIds);
    }


}
