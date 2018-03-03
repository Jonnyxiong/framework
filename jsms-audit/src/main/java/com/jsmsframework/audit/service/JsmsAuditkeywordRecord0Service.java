package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsAuditkeywordRecord0;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 短信审核与关键字记录分表原始表
 * @author fanghaidong
 * @date 2017-12-25
 */
public interface JsmsAuditkeywordRecord0Service {

    int insert(JsmsAuditkeywordRecord0 model);
    
    int insertBatch(List<JsmsAuditkeywordRecord0> modelList);

    int update(JsmsAuditkeywordRecord0 model);
    
    int updateSelective(JsmsAuditkeywordRecord0 model);

    JsmsAuditkeywordRecord0 getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsAuditkeywordRecord0> queryAll(Map<String,Object> params);
}
