package com.jsmsframework.access.service;

import java.util.List;
import java.util.Map;

import com.jsmsframework.access.access.entity.JsmsClientOperationStatistics;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 客户运营统计表
 * @author huangwenjie
 * @date 2018-01-09
 */
public interface JsmsClientOperationStatisticsService {

    int insert(JsmsClientOperationStatistics model);
    
    int insertBatch(List<JsmsClientOperationStatistics> modelList);

    int update(JsmsClientOperationStatistics model);
    
    int updateSelective(JsmsClientOperationStatistics model);

    JsmsClientOperationStatistics getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsClientOperationStatistics> findList(Map params);

    int count(Map<String, Object> params);
    
}
