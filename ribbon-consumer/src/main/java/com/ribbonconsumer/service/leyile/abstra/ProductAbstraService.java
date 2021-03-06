package com.ribbonconsumer.service.leyile.abstra;

import com.core.base.service.BaseService;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.config.ConfigModel;
import com.ribbonconsumer.mapper.leyile.LeMapper;
import com.ribbonconsumer.mapper.leyile.ProductMapper;
import com.ribbonconsumer.service.interfaceService.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProductAbstraService extends BaseService implements ProductInterface {

    private ProductMapper productMapper;
    private LeMapper leMapper;


    public ProductAbstraService(ProductMapper productMapper, LeMapper leMapper) {
        this.productMapper = productMapper;
        this.leMapper = leMapper;
    }


    public List<Map<String, Object>> getEvaluationList(long contentId, long userid) {
        updateRecords(leMapper, userid, productMapper.getClassfyList(contentId), 90);
        List<Map<String, Object>> evaluationList = productMapper.getEvaluationList(contentId);
        Map<Long, List<Map<String, Object>>> tempMap = new HashMap<>();
        evaluationList.forEach(stringObjectMap -> initMap(ModelUtil.getLong(stringObjectMap, "pid"), tempMap, stringObjectMap));
        for (Map<String, Object> map : evaluationList) {
            map.put("childList", tempMap.get(ModelUtil.getLong(map, "id")));
            map.put("headpic", ConfigModel.WEBURL + ModelUtil.getStr(map, "headpic"));
        }
        List<Map<String, Object>> maps = tempMap.get(0L);
        return maps == null ? new ArrayList<>() : maps;
    }


    public void insertContent(long userid, String title, String content, String picture, long classId) {
        productMapper.insertContent(userid, title, content, picture, classId);
    }

    public Map<String, Object> getContentList(long userid, int pageSize, int pageIndex) {
        Map<String, Object> result = new HashMap<>();
        result.put("total", productMapper.getContentCount(userid));
        result.put("pageIndex", pageIndex);
        result.put("list", productMapper.getContentList(userid, pageSize, pageIndex));
        return result;
    }

    public Map<String, Object> getContentDetail(long contentId, long userid) {
        return productMapper.getContentDetail(contentId, userid);
    }


    public void insertFootprint(long userid, long productId) {
        productMapper.insertFootprint(userid, productId);
    }

    public List<Map<String, Object>> getClassifyList(long userid) {
        Map<Long, List<Map<String, Object>>> tempMap = new HashMap<>();
        List<Map<String, Object>> classifyList = productMapper.getClassifyList(userid);
        classifyList.forEach(map -> {
            long pids = ModelUtil.getLong(map, "pid");
            initMap(pids, tempMap, map);
        });
        classifyList.forEach(map -> {
            List<Map<String, Object>> mapList = tempMap.get(ModelUtil.getLong(map, "id"));
            map.put("child", mapList);
        });
        List<Map<String, Object>> maps = tempMap.get(0L);
        return maps == null ? new ArrayList<>() : maps;
    }

    public List<Map<String, Object>> getFocusList(long userid) {
        return productMapper.getFocusList(userid);
    }


}
