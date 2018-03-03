package com.jsmsframework.audit.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.audit.entity.JsmsAutoTemplate;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 智能模板表
 * @author huangwenjie
 * @date 2017-08-28
 */
@Repository
public interface JsmsAutoTemplateMapper{

	int insert(JsmsAutoTemplate model);
	
	int insertBatch(List<JsmsAutoTemplate> modelList);

	int update(JsmsAutoTemplate model);
	
	int updateSelective(JsmsAutoTemplate model);

    JsmsAutoTemplate getByTemplateId(Integer templateId);

	List<JsmsAutoTemplate> queryList(JsmsPage<JsmsAutoTemplate> page);

	List<JsmsAutoTemplate> findList(JsmsPage<JsmsAutoTemplate> page);

	int count(Map<String,Object> params);

	int isExist(JsmsAutoTemplate autoTemplate);

	List<JsmsAutoTemplate> findListOfOperation(JsmsPage<JsmsAutoTemplate> page);

	@SimpleCountSQL
	List<JsmsAutoTemplate> queryPageList(JsmsPage<JsmsAutoTemplate> page);

	int  updateAdminId(JsmsAutoTemplate model);

	int countOfOperation(Map<String,String> params);
	

//	int getNeedSendNum();
//
//	int getNeedLockNum();

	int getNeedAuditNum( @Param("submit_type") String submit_type);

	int updatestate(JsmsAutoTemplate model);
}