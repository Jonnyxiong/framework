package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsAgentClientParam;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商和客户属性配置表
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAgentClientParamService {

    int insert(JsmsAgentClientParam model);
    
    int insertBatch(List<JsmsAgentClientParam> modelList);

    int update(JsmsAgentClientParam model);
    
    int updateSelective(JsmsAgentClientParam model);

    JsmsAgentClientParam getByParamId(Long paramId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsAgentClientParam getByParamKey(String paramKey);
}
