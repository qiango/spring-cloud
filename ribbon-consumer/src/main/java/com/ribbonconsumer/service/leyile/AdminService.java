package com.ribbonconsumer.service.leyile;

import com.core.base.baseenum.CheckStatusEnum;
import com.core.base.util.ModelUtil;
import com.ribbonconsumer.mapper.leyile.AdminMapper;
import com.ribbonconsumer.service.interfaceService.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Authod: wangqian
 * @Date: 2020-12-02  14:00
 */
@Service
public class AdminService implements ProductInterface {

    private AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public List<Map<String, Object>> getClassifyList() {
        List<Map<String, Object>> classify = adminMapper.getClassify();
        Map<Long, List<Map<String, Object>>> tempMap = new HashMap<>();
        classify.forEach(stringObjectMap -> initMap(ModelUtil.getLong(stringObjectMap, "pid"), tempMap, stringObjectMap));
        classify.forEach(cl -> cl.put("childList", tempMap.get(ModelUtil.getLong(cl, "id"))));
        List<Map<String, Object>> maps = tempMap.get(0L);
        return maps == null ? new ArrayList<>() : maps;
    }

    public List<Map<String, Object>> getContentList() {
        List<Map<String, Object>> contentList = adminMapper.getContentList();
        List<Long> id = contentList.stream().map(con -> ModelUtil.getLong(con, "id")).collect(Collectors.toList());
        List<Map<String, Object>> evaluationByContent = adminMapper.getEvaluationByContent(id);
        Map<Long, List<Map<String, Object>>> evaMap = new HashMap<>();
        evaluationByContent.forEach(stringObjectMap -> initMap(ModelUtil.getLong(stringObjectMap, "contentId"), evaMap, stringObjectMap));
        for (Map<String, Object> map : contentList) {
            map.put("evaList", evaMap.get(ModelUtil.getLong(map, "id")));
            map.put("checkStatus", CheckStatusEnum.getValue(ModelUtil.getInt(map, "checkStatus")).getMessage());
        }
        return contentList;
    }

    public List<Map<String, Object>> getUserList() {
        return adminMapper.getUserList();
    }
}
