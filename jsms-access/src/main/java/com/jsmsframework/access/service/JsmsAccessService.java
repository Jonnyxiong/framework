package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsAccess;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.List;
import java.util.Map;

/**
 * @description Access流水表
 * @author huangwenjie
 * @date 2017-10-20
 */
public interface JsmsAccessService {

    int insert(JsmsAccess model);
    
    int insertBatch(List<JsmsAccess> modelList);

    int update(JsmsAccess model);
    
    int updateSelective(JsmsAccess model);

    JsmsAccess getById(String id);

    /**
     *
     * @param identify client标识
     * @param date 流水表后缀 日期格式(yyyyMMdd)
     * @param jsmsPage 分页page对象
     * @return
     */
    JsmsPage queryOneDayList(String identify,String suffixDate,JsmsPage jsmsPage);

    int count(Map<String, Object> params);
    
}
