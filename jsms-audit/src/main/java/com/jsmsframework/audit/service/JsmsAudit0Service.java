package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsAudit0;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核内容表(周更新,原始表)
 * @author huangwenjie
 * @date 2017-11-28
 */
public interface JsmsAudit0Service {

    int insert(JsmsAudit0 model);
    
    int insertBatch(List<JsmsAudit0> modelList);

    int update(JsmsAudit0 model);
    
    int updateSelective(JsmsAudit0 model);

    JsmsAudit0 getByAuditid(Long auditid);

    List<JsmsAudit0> queryList(Map<String,Object> params);
    
    int count(Map<String,Object> params);
    
}
