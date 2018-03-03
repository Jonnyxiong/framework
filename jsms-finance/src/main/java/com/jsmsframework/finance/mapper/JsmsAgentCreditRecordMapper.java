package com.jsmsframework.finance.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsAgentCreditRecord;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商授信记录
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsAgentCreditRecordMapper{

	int insert(JsmsAgentCreditRecord model);
	
	int insertBatch(List<JsmsAgentCreditRecord> modelList);

	
	int update(JsmsAgentCreditRecord model);
	
	int updateSelective(JsmsAgentCreditRecord model);

    JsmsAgentCreditRecord getById(Long id);

	List<JsmsAgentCreditRecord> queryList(JsmsPage<JsmsAgentCreditRecord> page);

	int count(Map<String, Object> params);

}