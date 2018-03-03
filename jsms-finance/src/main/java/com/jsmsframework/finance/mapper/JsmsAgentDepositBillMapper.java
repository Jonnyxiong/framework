package com.jsmsframework.finance.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsAgentDepositBill;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商押金账户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsAgentDepositBillMapper{

	int insert(JsmsAgentDepositBill model);
	
	int insertBatch(List<JsmsAgentDepositBill> modelList);

	
	int update(JsmsAgentDepositBill model);
	
	int updateSelective(JsmsAgentDepositBill model);

    JsmsAgentDepositBill getById(Long id);

	List<JsmsAgentDepositBill> queryList(JsmsPage<JsmsAgentDepositBill> page);

	int count(Map<String, Object> params);

}