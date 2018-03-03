package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.dto.JsmsAgentInvoiceConfigDto;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
* @author huangwenjie
* @since 2018-01-23
*/
@Repository
public interface JsmsAgentInvoiceConfigMapper{

	int insert(JsmsAgentInvoiceConfig model);
	
	int insertBatch(List<JsmsAgentInvoiceConfig> modelList);

	
	int update(JsmsAgentInvoiceConfig model);
	
	int updateSelective(JsmsAgentInvoiceConfig model);

	JsmsAgentInvoiceConfig getById(Integer id);

	//分页使用的list
	List<JsmsAgentInvoiceConfig> queryList(JsmsPage page);

	int count(Map<String, Object> params);

	//不分页使用的list(注意：存风险查全库)
	List<JsmsAgentInvoiceConfig> findList(Map<String, Object> params);

	List<JsmsAgentInvoiceConfigDto> queryListForInvoice(JsmsPage page);
}