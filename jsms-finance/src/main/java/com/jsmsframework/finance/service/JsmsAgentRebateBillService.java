package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentRebateBill;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商返点账户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAgentRebateBillService {

    int insert(JsmsAgentRebateBill model);
    
    int insertBatch(List<JsmsAgentRebateBill> modelList);

    int update(JsmsAgentRebateBill model);
    
    int updateSelective(JsmsAgentRebateBill model);

    JsmsAgentRebateBill getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);


    
}
