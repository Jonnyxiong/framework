package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentRebate;

import java.util.List;
import java.util.Map;

/**
 * @description 代理返点比例配置
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAgentRebateService {

    int insert(JsmsAgentRebate model);
    
    int insertBatch(List<JsmsAgentRebate> modelList);

    int update(JsmsAgentRebate model);
    
    int updateSelective(JsmsAgentRebate model);

    JsmsAgentRebate getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);
    
}
