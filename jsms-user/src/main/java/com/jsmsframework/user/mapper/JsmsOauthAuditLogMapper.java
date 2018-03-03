package com.jsmsframework.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsOauthAuditLog;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 认证审核记录表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsOauthAuditLogMapper{

	int insert(JsmsOauthAuditLog model);
	
	int insertBatch(List<JsmsOauthAuditLog> modelList);

	
	int update(JsmsOauthAuditLog model);
	
	int updateSelective(JsmsOauthAuditLog model);

    JsmsOauthAuditLog getById(Integer id);

	List<JsmsOauthAuditLog> queryList(JsmsPage<JsmsOauthAuditLog> page);

	int count(Map<String,Object> params);

}