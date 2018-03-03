package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsAuditConclusion;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核原因表
 * @author Don
 * @date 2017-09-12
 */
public interface JsmsAuditConclusionService {

    int insert(JsmsAuditConclusion model);
    
    int insertBatch(List<JsmsAuditConclusion> modelList);

    int update(JsmsAuditConclusion model);
    
    int updateSelective(JsmsAuditConclusion model);

    JsmsAuditConclusion getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsAuditConclusion> findAllList(String remark);
    
}
