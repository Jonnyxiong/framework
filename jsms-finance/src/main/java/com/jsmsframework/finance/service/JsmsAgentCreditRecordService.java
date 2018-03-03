package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.dto.JsmsAgentCreditRecordDTO;
import com.jsmsframework.finance.entity.JsmsAgentCreditRecord;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商授信记录
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAgentCreditRecordService {

    int insert(JsmsAgentCreditRecord model);
    
    int insertBatch(List<JsmsAgentCreditRecord> modelList);

    int update(JsmsAgentCreditRecord model);
    
    int updateSelective(JsmsAgentCreditRecord model);

    JsmsAgentCreditRecord getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);

    List<JsmsAgentCreditRecordDTO> creditHistories(Integer agentId);
    
}
