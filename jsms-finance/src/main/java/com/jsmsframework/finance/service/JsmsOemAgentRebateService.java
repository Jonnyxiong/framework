package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsOemAgentRebate;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理返点比例配置
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsOemAgentRebateService {

    int insert(JsmsOemAgentRebate model);
    
    int insertBatch(List<JsmsOemAgentRebate> modelList);

    int update(JsmsOemAgentRebate model);
    
    int updateSelective(JsmsOemAgentRebate model);

    JsmsOemAgentRebate getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);
    
}
