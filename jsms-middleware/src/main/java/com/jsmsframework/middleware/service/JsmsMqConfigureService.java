package com.jsmsframework.middleware.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.middleware.entity.JsmsMqConfigure;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description MQ配置表
 * @author huangwenjie
 * @date 2017-11-04
 */
public interface JsmsMqConfigureService {

    int insert(JsmsMqConfigure model);
    
    int insertBatch(List<JsmsMqConfigure> modelList);

    int update(JsmsMqConfigure model);
    
    int updateSelective(JsmsMqConfigure model);

    JsmsMqConfigure getByMqId(Integer mqId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);
    
}
