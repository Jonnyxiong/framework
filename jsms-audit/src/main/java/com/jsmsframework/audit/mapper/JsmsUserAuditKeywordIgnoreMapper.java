package com.jsmsframework.audit.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsUserAuditKeywordIgnore;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 用户审核关键字忽略表
 * @author Don
 * @date 2018-01-17
 */
@Repository
public interface JsmsUserAuditKeywordIgnoreMapper{

	int insert(JsmsUserAuditKeywordIgnore model);
	
	int insertBatch(List<JsmsUserAuditKeywordIgnore> modelList);

	
	int update(JsmsUserAuditKeywordIgnore model);
	
	int updateSelective(JsmsUserAuditKeywordIgnore model);

    JsmsUserAuditKeywordIgnore getById(Integer id);

	@SimpleCountSQL
	List<JsmsUserAuditKeywordIgnore> queryList(JsmsPage<JsmsUserAuditKeywordIgnore> page);

	List<JsmsUserAuditKeywordIgnore> findList(Map params);

	int count(Map<String,Object> params);

	int updateStatus(JsmsUserAuditKeywordIgnore model);

	List<JsmsUserAuditKeywordIgnore> findByClientIdAndSmsType(@Param("clientId") String clientId,@Param("smsType") Integer smsType);

	List<JsmsUserAuditKeywordIgnore> queryExistData(@Param("clientId") String clientId,@Param("smsType") Integer smsType,@Param("ignoreKeyword") String ignoreKeyword,@Param("status") String status);
}