package com.jsmsframework.common.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.common.entity.JsmsMailprop;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 邮件配置表
 * @author huangwenjie
 * @date 2017-11-27
 */
public interface JsmsMailpropService {

    int insert(JsmsMailprop model);
    
    int insertBatch(List<JsmsMailprop> modelList);

    int update(JsmsMailprop model);
    
    int updateSelective(JsmsMailprop model);

    JsmsMailprop getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsMailprop querySmsMailprop(Integer id);
}
