package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsOemDataConfig;

import java.util.List;
import java.util.Map;

/**
 * @description OEM资料配置
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsOemDataConfigService {

    int insert(JsmsOemDataConfig model);
    
    int insertBatch(List<JsmsOemDataConfig> modelList);

    int update(JsmsOemDataConfig model);
    
    int updateSelective(JsmsOemDataConfig model);

    JsmsOemDataConfig getById(Integer id);

    JsmsOemDataConfig getByAgentId(Integer id);

    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    int updateSelectiveByAgentId(JsmsOemDataConfig model);
}
