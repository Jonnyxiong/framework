package com.jsmsframework.schUserConf.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.schUserConf.entity.JsmsOverrate;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 模板超频表
 * @author huangwenjie
 * @date 2017-09-27
 */
public interface JsmsOverrateService {

    int insert(JsmsOverrate model);
    
    int insertBatch(List<JsmsOverrate> modelList);

    int update(JsmsOverrate model);
    
    int updateSelective(JsmsOverrate model);

    JsmsOverrate getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    R delOverrate(Integer id);

    Map<String, Object> checkExist(Map<String,Object> param);
}
