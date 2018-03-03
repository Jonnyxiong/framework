package com.jsmsframework.audit.mapper;

import com.jsmsframework.audit.entity.JsmsAuditClientGroup;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 审核用户组
 * @author huangwenjie
 * @date 2017-10-31
 */
@Repository
public interface JsmsAuditClientGroupMapper{

	int insert(JsmsAuditClientGroup model);
	
	int insertBatch(List<JsmsAuditClientGroup> modelList);

	
	int update(JsmsAuditClientGroup model);
	
	int updateSelective(JsmsAuditClientGroup model);

    JsmsAuditClientGroup getByCgroupId(Integer cgroupId);

	List<JsmsAuditClientGroup> queryList(JsmsPage<JsmsAuditClientGroup> page);

	int count(Map<String,Object> params);

	int checkCgroupName(@Param(value="cgroupName") String cgroupName, @Param(value="cgroupId") Integer cgroupId);

	int checkcgroupNameOfInsert(String cgroupName);

	int deteleJsmsAuditClientGroup(Integer cgroupId);

	int getKgroupIdToClient(Integer kgroupId);

    List<JsmsAuditClientGroup> getKgroupIdsByClientId(String clientId);

	List<JsmsAuditClientGroup> getDefaultKgroupIds();

    List<String> getClientIdsByKgroupId(Integer kgroupId);

	List<JsmsAuditClientGroup> getByKgroupId(Integer kgroupId);
}