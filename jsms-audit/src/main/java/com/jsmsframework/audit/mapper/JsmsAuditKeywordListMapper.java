package com.jsmsframework.audit.mapper;

import com.jsmsframework.audit.entity.JsmsAuditKeywordList;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 审核关键字列表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Repository
public interface JsmsAuditKeywordListMapper{

	int insert(JsmsAuditKeywordList model);
	
	int insertBatch(List<JsmsAuditKeywordList> modelList);
	/**
	 * 幂等更新
	 * @param idempotentParams
	 * @return
	 */
	int updateIdempotent(Map<String,JsmsAuditKeywordList> idempotentParams);
	
	int update(JsmsAuditKeywordList model);
	
	int updateSelective(JsmsAuditKeywordList model);

    JsmsAuditKeywordList getById(Long id);

    JsmsAuditKeywordList getByKeyword(String keyword);

    JsmsAuditKeywordList getByKeywordAndCategoryId(@Param("keyword")String keyword,@Param("categoryId")Integer categoryId) ;

	List<JsmsAuditKeywordList> getByKeywords(@Param("keywords") Set<String> keywords);

	List<JsmsAuditKeywordList> queryList(JsmsPage<JsmsAuditKeywordList> page);

	int count(Map<String,Object> params);

	int delete(Long id);

	List<Map<String, Object>> queryExportManageExcel(JsmsAuditKeywordList model);

	int saveCheck(Map<String, Object> oneRow);


	List<JsmsAuditKeywordList> getByCategoryIds(List<Integer> categoryIds);

	List<Map<String,Object>>   queryByKgroupId(@Param("kgroupId")Integer kgroupId);
}