package com.jsmsframework.audit.mapper;

import com.jsmsframework.audit.entity.JsmsAuditKeywordCategory;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 审核关键字分类表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Repository
public interface JsmsAuditKeywordCategoryMapper{

	int insert(JsmsAuditKeywordCategory model);
	
	int insertBatch(List<JsmsAuditKeywordCategory> modelList);

	/**
	 * 幂等更新
	 * @param idempotentParams
	 * @return
	 */
	int updateIdempotent(Map<String,JsmsAuditKeywordCategory> idempotentParams);
	
	int update(JsmsAuditKeywordCategory model);
	
	int updateSelective(JsmsAuditKeywordCategory model);

    JsmsAuditKeywordCategory getByCategoryId(Integer categoryId);

	List<JsmsAuditKeywordCategory> getByCategoryName(String categoryName);

	List<JsmsAuditKeywordCategory> getByCategoryNames(@Param("categoryNames") Set<String> categoryNames);

	List<JsmsAuditKeywordCategory> queryList(JsmsPage<JsmsAuditKeywordCategory> page);

	int count(Map<String,Object> params);

	List<JsmsAuditKeywordCategory> queryEditKeyWord(JsmsAuditKeywordCategory model);

	List<Map<String, Object>> queryExportExcelData(JsmsAuditKeywordCategory model);

	List<JsmsAuditKeywordCategory> getList();

	List<JsmsAuditKeywordCategory> getExitList(Integer kgroupId);

	int delete(Integer categoryId);

	List<JsmsAuditKeywordCategory> getByCategoryIds(List<Integer> categoryIds);
}