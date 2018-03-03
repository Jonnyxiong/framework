package com.jsmsframework.sysKeyword.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordCategory;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统关键字分类表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Repository
public interface JsmsSysKeywordCategoryMapper{

	int insert(JsmsSysKeywordCategory model);
	
	int insertBatch(List<JsmsSysKeywordCategory> modelList);

	
	int update(JsmsSysKeywordCategory model);
	
	int updateSelective(JsmsSysKeywordCategory model);

    JsmsSysKeywordCategory getByCategoryId(Integer categoryId);

	@SimpleCountSQL
	List<JsmsSysKeywordCategory> queryList(JsmsPage<JsmsSysKeywordCategory> page);

	List<JsmsSysKeywordCategory> findList(Map params);

	int count(Map<String,Object> params);

	/**
	 * 通过类别名称查找数据
	 * @param categoryName
	 * @return
	 */
	List<JsmsSysKeywordCategory> getByCategoryName(@Param("categoryName") String categoryName);

	/**
	 * 通过id删除类别
	 * @param categoryId
	 * @return
	 */
	int deleteByCategoryId(@Param("categoryId") Integer categoryId);

	/**
	 * 通过类别id查找
	 * @param categoryIds
	 * @return
	 */
	List<JsmsSysKeywordCategory> getByCategoryIds(@Param(value = "categoryIds") Set<Integer> categoryIds);

	List<JsmsSysKeywordCategory> findByCategoryName(Map<String,Object> params);
}