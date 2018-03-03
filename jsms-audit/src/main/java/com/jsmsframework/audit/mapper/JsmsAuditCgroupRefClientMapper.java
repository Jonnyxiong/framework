package com.jsmsframework.audit.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsAuditCgroupRefClient;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核用户组内用户表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Repository
public interface JsmsAuditCgroupRefClientMapper{

	int insert(JsmsAuditCgroupRefClient model);
	
	int insertBatch(List<JsmsAuditCgroupRefClient> modelList);

	
	int update(JsmsAuditCgroupRefClient model);
	
	int updateSelective(JsmsAuditCgroupRefClient model);

    JsmsAuditCgroupRefClient getById(Integer id);

	List<JsmsAuditCgroupRefClient> queryList(JsmsPage<JsmsAuditCgroupRefClient> page);

	int count(Map<String,Object> params);

	List<JsmsAuditCgroupRefClient> getCgroupId(Integer cgroupId);

	int deteleJsmsAuditCgroupRefClient(Integer cgroupId);

	JsmsAuditCgroupRefClient getByClientId(@Param("clientId") String clientId);
}