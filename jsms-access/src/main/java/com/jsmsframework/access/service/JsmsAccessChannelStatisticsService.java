package com.jsmsframework.access.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.access.access.entity.JsmsAccessChannelStatistics;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 客户发送统计表
 * @author huangwenjie
 * @date 2017-10-16
 */
public interface JsmsAccessChannelStatisticsService {

    int insert(JsmsAccessChannelStatistics model);
    
    int insertBatch(List<JsmsAccessChannelStatistics> modelList);

    int update(JsmsAccessChannelStatistics model);
    
    int updateSelective(JsmsAccessChannelStatistics model);

    JsmsAccessChannelStatistics getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);
    
}
