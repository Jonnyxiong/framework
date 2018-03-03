package com.jsmsframework.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsRole;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 角色表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsRoleMapper{

	int insert(JsmsRole model);
	
	int insertBatch(List<JsmsRole> modelList);

	
	int update(JsmsRole model);
	
	int updateSelective(JsmsRole model);

    JsmsRole getById(Integer id);

	List<JsmsRole> queryList(JsmsPage<JsmsRole> page);

	int count(Map<String,Object> params);

	String getSaleRoleId();

	Integer queryOemRoleId();
}