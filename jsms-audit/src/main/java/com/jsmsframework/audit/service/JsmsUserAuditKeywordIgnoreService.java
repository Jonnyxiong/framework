package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsUserAuditKeywordIgnore;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 用户审核关键字忽略表
 * @author Don
 * @date 2018-01-17
 */
public interface JsmsUserAuditKeywordIgnoreService {

    int insert(JsmsUserAuditKeywordIgnore model);
    
    int insertBatch(List<JsmsUserAuditKeywordIgnore> modelList);

    int update(JsmsUserAuditKeywordIgnore model);
    
    int updateSelective(JsmsUserAuditKeywordIgnore model);

    JsmsUserAuditKeywordIgnore getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsUserAuditKeywordIgnore> findList(Map params);

    int count(Map<String,Object> params);

    int updateStatus(JsmsUserAuditKeywordIgnore model);

    List<JsmsUserAuditKeywordIgnore> findByClientIdAndSmsType(String clientId,Integer smsType);

    List<JsmsUserAuditKeywordIgnore> queryExistData(String clientId,Integer smsType,String ignoreKeyword,String status);

}
