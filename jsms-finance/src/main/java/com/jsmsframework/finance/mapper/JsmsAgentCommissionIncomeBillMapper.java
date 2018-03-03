package com.jsmsframework.finance.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsAgentCommissionIncomeBill;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商佣金帐户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsAgentCommissionIncomeBillMapper{

	int insert(JsmsAgentCommissionIncomeBill model);
	
	int insertBatch(List<JsmsAgentCommissionIncomeBill> modelList);

	
	int update(JsmsAgentCommissionIncomeBill model);
	
	int updateSelective(JsmsAgentCommissionIncomeBill model);

    JsmsAgentCommissionIncomeBill getById(Integer id);

	List<JsmsAgentCommissionIncomeBill> queryList(JsmsPage<JsmsAgentCommissionIncomeBill> page);

	int count(Map<String, Object> params);

}