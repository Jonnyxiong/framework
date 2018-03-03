package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsOauthAuditLog;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 认证审核记录表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsOauthAuditLogService {

    int insert(JsmsOauthAuditLog model);
    
    int insertBatch(List<JsmsOauthAuditLog> modelList);

    int update(JsmsOauthAuditLog model);
    
    int updateSelective(JsmsOauthAuditLog model);

    JsmsOauthAuditLog getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);
    
}
