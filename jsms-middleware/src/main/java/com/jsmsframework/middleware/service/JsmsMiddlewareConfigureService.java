package com.jsmsframework.middleware.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.middleware.entity.JsmsMiddlewareConfigure;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 中间件配置信息表
 * @author huangwenjie
 * @date 2017-11-04
 */
public interface JsmsMiddlewareConfigureService {

    int insert(JsmsMiddlewareConfigure model);
    
    int insertBatch(List<JsmsMiddlewareConfigure> modelList);

    int update(JsmsMiddlewareConfigure model);
    
    int updateSelective(JsmsMiddlewareConfigure model);

    JsmsMiddlewareConfigure getByMiddlewareId(Integer middlewareId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsMiddlewareConfigure getByMiddlewareType(Integer middlewareType);
    
}
