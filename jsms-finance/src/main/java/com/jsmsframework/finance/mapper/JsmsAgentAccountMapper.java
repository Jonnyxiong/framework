package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商帐户表
 * @author huangwenjie
 * @date 2017-08-08
 */
@Repository
public interface JsmsAgentAccountMapper {

	int insert(JsmsAgentAccount model);

	int insertBatch(List<JsmsAgentAccount> modelList);

	int update(JsmsAgentAccount model);

	int updateAfterConsume(JsmsAgentAccount model);

	int updateSelective(JsmsAgentAccount model);
	
	JsmsAgentAccount getByAgentId(Integer agentId);

	int reduceBalance(@Param("agentId") Integer agentId, @Param("reduceNum") BigDecimal reduceNum);

	List<JsmsAgentAccount> getByAgentIds(@Param("agentIds") Set agentIds);

	List<JsmsAgentAccount> queryList(JsmsPage<JsmsAgentAccount> page);

	List<JsmsAgentAccount> queryListByMap(Map params);

	List<Map<String,Object>> querySumCreditBySale(Map params);

	int count(Map<String,Object> params);

	int addBalance(@Param("agentAccount") JsmsAgentAccount agentAccount, @Param("balance") BigDecimal balance);

	Map<String,Object> querySumBlance(@Param("agentIds") List agentIds);

	int updateAccoutForRealTime(JsmsAgentAccount model);

	//根据代理商id获取代理商账户表中的账户余额
	JsmsAgentAccount getAgentAccountByAgentId(@Param("agentId") int agentId);

	//不分页使用的list(注意：存风险查全库)
	List<JsmsAgentAccount> findList(Map<String,Object> params);

	/**
	 * @param agentId 代理商id
	 * @param hasTakeInvoice 已开票金额(要减去的值)
	 * @param hasTakeInvoiceInit hasTakeInvoiceInit(已开票初始化金额==0时,该字段不更新)
	 * @Description: 更新代理商已开票金额
	 * @Author: tanjiangqiang
	 * @Date: 2018/1/24 - 16:19
	 */
	int updateHasTakeInvoice(@Param("agentId") Integer agentId, @Param("hasTakeInvoice") BigDecimal hasTakeInvoice, @Param("hasTakeInvoiceInit") BigDecimal hasTakeInvoiceInit);
}