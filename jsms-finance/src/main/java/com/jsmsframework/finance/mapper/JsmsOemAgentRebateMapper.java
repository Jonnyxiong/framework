package com.jsmsframework.finance.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsOemAgentRebate;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description OEM代理返点比例配置
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsOemAgentRebateMapper{

	int insert(JsmsOemAgentRebate model);
	
	int insertBatch(List<JsmsOemAgentRebate> modelList);

	
	int update(JsmsOemAgentRebate model);
	
	int updateSelective(JsmsOemAgentRebate model);

    JsmsOemAgentRebate getById(Integer id);

	List<JsmsOemAgentRebate> queryList(JsmsPage<JsmsOemAgentRebate> page);

	int count(Map<String, Object> params);

}