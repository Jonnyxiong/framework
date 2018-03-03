package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentDepositBill;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商押金账户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAgentDepositBillService {

    int insert(JsmsAgentDepositBill model);
    
    int insertBatch(List<JsmsAgentDepositBill> modelList);

    int update(JsmsAgentDepositBill model);
    
    int updateSelective(JsmsAgentDepositBill model);

    JsmsAgentDepositBill getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);
    
}
