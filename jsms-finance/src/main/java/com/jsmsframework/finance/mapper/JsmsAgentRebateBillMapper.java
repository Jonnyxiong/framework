package com.jsmsframework.finance.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsAgentRebateBill;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商返点账户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsAgentRebateBillMapper{

	int insert(JsmsAgentRebateBill model);
	
	int insertBatch(List<JsmsAgentRebateBill> modelList);

	
	int update(JsmsAgentRebateBill model);
	
	int updateSelective(JsmsAgentRebateBill model);

    JsmsAgentRebateBill getById(Long id);

	List<JsmsAgentRebateBill> queryList(JsmsPage<JsmsAgentRebateBill> page);

	int count(Map<String, Object> params);

}