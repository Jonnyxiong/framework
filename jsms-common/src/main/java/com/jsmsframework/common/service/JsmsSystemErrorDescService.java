package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsSystemErrorDesc;

import java.util.List;
import java.util.Map;

/**
 * @description 平台错误码对应表
 * @author huangwenjie
 * @date 2017-11-25
 */
public interface JsmsSystemErrorDescService {

    int insert(JsmsSystemErrorDesc model);
    
    int insertBatch(List<JsmsSystemErrorDesc> modelList);

    int update(JsmsSystemErrorDesc model);
    
    int updateSelective(JsmsSystemErrorDesc model);

    JsmsSystemErrorDesc getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsSystemErrorDesc getBySyscode(String syscode);

    List<JsmsSystemErrorDesc> queryAllList(Map<String,Object> params);
}
