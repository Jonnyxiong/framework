package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentCommissionIncomeBill;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商佣金帐户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAgentCommissionIncomeBillService {

    int insert(JsmsAgentCommissionIncomeBill model);
    
    int insertBatch(List<JsmsAgentCommissionIncomeBill> modelList);

    int update(JsmsAgentCommissionIncomeBill model);
    
    int updateSelective(JsmsAgentCommissionIncomeBill model);

    JsmsAgentCommissionIncomeBill getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);
    
}
