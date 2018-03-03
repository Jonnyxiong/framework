package com.jsmsframework.sysKeyword.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsmsframework.sysKeyword.entity.JsmsKeywordList;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统关键字列表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Repository
public interface JsmsSysKeywordListMapper {

	int insert(JsmsKeywordList model);
	
	int insertBatch(List<JsmsKeywordList> modelList);

	
	int update(JsmsKeywordList model);
	
	int updateSelective(JsmsKeywordList model);

    JsmsKeywordList getById(Long id);

	@SimpleCountSQL
	List<JsmsKeywordList> queryList(JsmsPage<JsmsKeywordList> page);

	List<JsmsKeywordList> findList(Map params);

	int count(Map<String,Object> params);

	int deleteById(@Param("id") Long id);

	List<JsmsKeywordList> findByKeywordsAndCategoryId(@Param("keywords") Set<String> keywords,@Param("categoryId") Integer categoryId);

	/**
	 * 通过类别找到关键字（只返回关键字）
	 * @param categoryId
	 * @return
	 */
	List<String> queryKeywordByCategoryId(@Param("categoryId") Integer categoryId);
}