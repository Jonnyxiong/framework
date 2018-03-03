package com.jsmsframework.audit.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsAuditKgroupRefCategory;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核关键字组内类别表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Repository
public interface JsmsAuditKgroupRefCategoryMapper{

	int insert(JsmsAuditKgroupRefCategory model);
	
	int insertBatch(List<JsmsAuditKgroupRefCategory> modelList);

	
	int update(JsmsAuditKgroupRefCategory model);
	
	int updateSelective(JsmsAuditKgroupRefCategory model);

    JsmsAuditKgroupRefCategory getById(Integer id);

	List<JsmsAuditKgroupRefCategory> queryList(JsmsPage<JsmsAuditKgroupRefCategory> page);

	int count(Map<String,Object> params);

	List<JsmsAuditKgroupRefCategory> getBykgroupId(Integer kgroupId);

	int deteleJsmsAuditKgroupRefCategory(Integer kgroupId);

	List<JsmsAuditKgroupRefCategory> getBykgroupIds(List<Integer> keywordGroupIds);

    List<JsmsAuditKgroupRefCategory> findList(Map params);
}