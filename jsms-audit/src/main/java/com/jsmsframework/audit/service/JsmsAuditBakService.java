package com.jsmsframework.audit.service;

import com.jsmsframework.audit.entity.JsmsAudit;
import com.jsmsframework.audit.entity.JsmsAuditBak;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.List;
import java.util.Map;

/**
 * @description 审核内容表（备份）
 * @author huangwenjie
 * @date 2017-08-29
 */
public interface JsmsAuditBakService {

    int insert(JsmsAuditBak model);

    int delete(Long id);

    int insertFromAudit(JsmsAudit model);

    int insertBatch(List<JsmsAuditBak> modelList);

    int insertBatchFromAudit(List<JsmsAudit> modelList);

    int update(JsmsAuditBak model);
    
    int updateSelective(JsmsAuditBak model);

    JsmsAuditBak getByAuditid(Long auditid);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsAuditBak> findRepeatList();

    /**
     * 批量删除
     *
     * @param auditIdList
     */
    int batchDeleteAudit(int[] auditIdList);

    List<Map<String,Object>> queryhisAll(Map<String,Object> params);

    int insertWithTableName(JsmsAuditBak model,String tableName);
}
