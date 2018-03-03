package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsClientDict;

import java.util.List;
import java.util.Map;

/**
 * @description 用户数据字典表
 * @author huangwenjie
 * @date 2017-11-07
 */
public interface JsmsClientDictService {

    int insert(JsmsClientDict model);
    
    int insertBatch(List<JsmsClientDict> modelList);

    int update(JsmsClientDict model);
    
    int updateSelective(JsmsClientDict model);

    JsmsClientDict getByParamId(Integer paramId);

    List<JsmsClientDict> getByParamType(String paramType);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);
    
}
