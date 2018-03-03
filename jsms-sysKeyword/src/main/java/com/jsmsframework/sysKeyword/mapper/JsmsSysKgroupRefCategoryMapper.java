package com.jsmsframework.sysKeyword.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsmsframework.sysKeyword.entity.JsmsSysKgroupRefCategory;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统关键字组内类别表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Repository
public interface JsmsSysKgroupRefCategoryMapper{

	int insert(JsmsSysKgroupRefCategory model);
	
	int insertBatch(List<JsmsSysKgroupRefCategory> modelList);

	
	int update(JsmsSysKgroupRefCategory model);
	
	int updateSelective(JsmsSysKgroupRefCategory model);

    JsmsSysKgroupRefCategory getById(Integer id);

	@SimpleCountSQL
	List<JsmsSysKgroupRefCategory> queryList(JsmsPage<JsmsSysKgroupRefCategory> page);

	List<JsmsSysKgroupRefCategory> findList(Map params);

	int count(Map<String,Object> params);

	int deleteByGroupId(@Param("kgroupId") Integer kgroupId);

	List<JsmsSysKgroupRefCategory> getByKgroupId(@Param("kgroupId") Integer kgroupId);

	List<JsmsSysKgroupRefCategory> findInCategoryId(@Param("categoryIds")Set categoryIds);
}