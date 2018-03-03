package com.jsmsframework.record.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.record.record.entity.JsmsChannelOperationStatistics;

import java.util.List;
import java.util.Map;

/**
 * @description 通道运营统计表
 * @author huangwenjie
 * @date 2018-01-09
 */
public interface JsmsChannelOperationStatisticsService {

    int insert(JsmsChannelOperationStatistics model);
    
    int insertBatch(List<JsmsChannelOperationStatistics> modelList);

    int update(JsmsChannelOperationStatistics model);
    
    int updateSelective(JsmsChannelOperationStatistics model);

    JsmsChannelOperationStatistics getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsChannelOperationStatistics> findList(Map params);

    int count(Map<String, Object> params);

}
