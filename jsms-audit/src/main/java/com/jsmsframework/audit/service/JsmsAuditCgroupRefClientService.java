package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsAuditCgroupRefClient;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核用户组内用户表
 * @author huangwenjie
 * @date 2017-10-31
 */
public interface JsmsAuditCgroupRefClientService {

    int insert(JsmsAuditCgroupRefClient model);
    
    int insertBatch(List<JsmsAuditCgroupRefClient> modelList);

    int update(JsmsAuditCgroupRefClient model);
    
    int updateSelective(JsmsAuditCgroupRefClient model);

    JsmsAuditCgroupRefClient getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsAuditCgroupRefClient> getCgroupId(Integer cgroupId);

    int deteleJsmsAuditCgroupRefClient(Integer cgroupId);

    JsmsAuditCgroupRefClient getByClientId(String clientId);
}
