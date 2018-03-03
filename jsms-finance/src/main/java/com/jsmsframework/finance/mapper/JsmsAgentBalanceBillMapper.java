package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商帐户余额收支明细
 * @author huangwenjie
 * @date 2017-08-08
 */
@Repository
public interface JsmsAgentBalanceBillMapper {

	int insert(JsmsAgentBalanceBill model);
	
	int insertBatch(List<JsmsAgentBalanceBill> modelList);

	
	int update(JsmsAgentBalanceBill model);
	
	int updateSelective(JsmsAgentBalanceBill model);
	
	JsmsAgentBalanceBill getById(Integer id);
	
	List<JsmsAgentBalanceBill> queryList(JsmsPage<JsmsAgentBalanceBill> page);

	List<JsmsAgentBalanceBill> queryListByMap(Map params);

	int count(Map<String, Object> params);

	/**
	* 获取历史授信数据
	*/
	List<JsmsAgentBalanceBill> queryHisCreditList(JsmsPage page);

	/**
    * 历史授信合计
    */
	JsmsAgentBalanceBill total(@Param(value = "params") Map<String, String> params);

	/**
	 *获取当前代理商最接近当前流水数据
	 */
	JsmsAgentBalanceBill getBill4Max(@Param(value = "agentId")Integer agentId);
}