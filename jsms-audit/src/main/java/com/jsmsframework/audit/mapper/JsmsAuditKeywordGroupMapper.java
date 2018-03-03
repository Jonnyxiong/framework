package com.jsmsframework.audit.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsAuditKeywordGroup;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核关键字分组表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Repository
public interface JsmsAuditKeywordGroupMapper{

	int insert(JsmsAuditKeywordGroup model);
	
	int insertBatch(List<JsmsAuditKeywordGroup> modelList);

	
	int update(JsmsAuditKeywordGroup model);
	
	int updateSelective(JsmsAuditKeywordGroup model);

    JsmsAuditKeywordGroup getByKgroupId(Integer kgroupId);

	List<JsmsAuditKeywordGroup> queryList(JsmsPage<JsmsAuditKeywordGroup> page);

	int count(Map<String,Object> params);

	JsmsAuditKeywordGroup getJsmsAuditKeywordGroup(String kgroupName);


	int checkKgroupName(@Param(value="kgroupName") String kgroupName, @Param(value="kgroupId") Integer kgroupId);

	int checkKgroupNameOfInsert(String kgroupName);

	int deteleJsmsAuditKeywordGroup(Integer kgroupId);

	List<JsmsAuditKeywordGroup> getList();

    List<JsmsAuditKeywordGroup> findList(Map parmas);
}