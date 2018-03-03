package com.jsmsframework.sysConfig.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.sysConfig.entity.JsmsWhiteList;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 黑名单表
 * @author Don
 * @date 2018-01-10
 */
public interface JsmsWhiteListService {

    int insert(JsmsWhiteList model);
    
    int insertBatch(List<JsmsWhiteList> modelList);

    int update(JsmsWhiteList model);
    
    int updateSelective(JsmsWhiteList model);

    JsmsWhiteList getById(Long id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsWhiteList> findList(Map params);

    int count(Map<String,Object> params);

    JsmsWhiteList getByPhone(String phone);

    int deleteWhiteList(Long id);
}
