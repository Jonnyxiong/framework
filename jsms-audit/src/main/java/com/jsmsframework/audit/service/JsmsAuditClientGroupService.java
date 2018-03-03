package com.jsmsframework.audit.service;

import com.jsmsframework.audit.entity.JsmsAuditClientGroup;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.List;
import java.util.Map;

/**
 * @description 审核用户组
 * @author huangwenjie
 * @date 2017-10-31
 */
public interface JsmsAuditClientGroupService {

    int insert(JsmsAuditClientGroup model);
    
    int insertBatch(List<JsmsAuditClientGroup> modelList);

    int update(JsmsAuditClientGroup model);
    
    int updateSelective(JsmsAuditClientGroup model);

    JsmsAuditClientGroup getByCgroupId(Integer cgroupId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    int checkcgroupName(String cgroupName,Integer cgroupId);

    int checkcgroupNameOfInsert(String cgroupName);

    int deteleJsmsAuditClientGroup(Integer cgroupId);

    int getKgroupIdToClient(Integer kgroupId);

    /**
     * @attention 根据clientid获取对应的关键字组 , 不存在对应的 关键字组时取默认的关键字组
     * @param clientId
     * @return
     */
    List<JsmsAuditClientGroup> getKgroupIdsByClientId(String clientId);


    List<JsmsAuditClientGroup> getByKgroupId(Integer kgroupId);
}
