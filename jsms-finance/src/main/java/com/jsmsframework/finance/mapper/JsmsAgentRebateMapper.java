package com.jsmsframework.finance.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsAgentRebate;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理返点比例配置
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsAgentRebateMapper{

	int insert(JsmsAgentRebate model);
	
	int insertBatch(List<JsmsAgentRebate> modelList);

	
	int update(JsmsAgentRebate model);
	
	int updateSelective(JsmsAgentRebate model);

    JsmsAgentRebate getById(Integer id);

	List<JsmsAgentRebate> queryList(JsmsPage<JsmsAgentRebate> page);

	int count(Map<String, Object> params);

}