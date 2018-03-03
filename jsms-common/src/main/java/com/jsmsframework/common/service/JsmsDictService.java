package com.jsmsframework.common.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.common.entity.JsmsDict;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 数据字典表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsDictService {

    int insert(JsmsDict model);
    
    int insertBatch(List<JsmsDict> modelList);

    int update(JsmsDict model);
    
    int updateSelective(JsmsDict model);

    JsmsDict getByParamId(Integer paramId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsDict> findList(Map<String,Object> params);
    
}
