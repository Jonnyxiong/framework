package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsOemAgentAccountStatistics;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商帐户统计表
 * @author huangwenjie
 * @date 2017-08-20
 */
public interface JsmsOemAgentAccountStatisticsService {

    int insert(JsmsOemAgentAccountStatistics model);
    
    int insertBatch(List<JsmsOemAgentAccountStatistics> modelList);

    int update(JsmsOemAgentAccountStatistics model);

    int updateForAddPurchaseNumber(JsmsOemAgentAccountStatistics model);

    int updateSelective(JsmsOemAgentAccountStatistics model);

    JsmsOemAgentAccountStatistics getByAgentId(Integer agentId);
    
    JsmsPage queryList(JsmsPage jsmsPage);
    
    int count(Map<String, Object> params);

}
