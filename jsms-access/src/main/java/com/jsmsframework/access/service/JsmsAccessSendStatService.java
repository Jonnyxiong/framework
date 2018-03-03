package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsAccessSendStat;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.List;
import java.util.Map;

/**
 * @description 客户发送量表
 * @author huangwenjie
 * @date 2017-10-16
 */
public interface JsmsAccessSendStatService {

    int insert(JsmsAccessSendStat model);
    
    int insertBatch(List<JsmsAccessSendStat> modelList);

    int update(JsmsAccessSendStat model);
    
    int updateSelective(JsmsAccessSendStat model);

    JsmsAccessSendStat getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);

    JsmsPage querySumList(JsmsPage page,String groupByClause) throws IllegalAccessException;

    
}
