package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsOemAgentAccountStatistics;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商帐户统计表
 * @author huangwenjie
 * @date 2017-08-20
 */
@Repository
public interface JsmsOemAgentAccountStatisticsMapper{

	int insert(JsmsOemAgentAccountStatistics model);
	
	int insertBatch(List<JsmsOemAgentAccountStatistics> modelList);

	int update(JsmsOemAgentAccountStatistics model);

	int updateForAddPurchaseNumber(JsmsOemAgentAccountStatistics model);

	int updateSelective(JsmsOemAgentAccountStatistics model);

    JsmsOemAgentAccountStatistics getByAgentId(Integer agentId);

	List<JsmsOemAgentAccountStatistics> queryList(JsmsPage<JsmsOemAgentAccountStatistics> jsmsPage);

	int count(Map<String, Object> params);

}