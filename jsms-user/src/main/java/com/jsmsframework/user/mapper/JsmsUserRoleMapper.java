package com.jsmsframework.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsUserRole;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 用户角色关系表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsUserRoleMapper{

	int insert(JsmsUserRole model);
	
	int insertBatch(List<JsmsUserRole> modelList);

	
	int update(JsmsUserRole model);
	
	int updateSelective(JsmsUserRole model);

    JsmsUserRole getByRuId(Integer ruId);

	List<JsmsUserRole> queryList(JsmsPage<JsmsUserRole> page);

	int count(Map<String,Object> params);

	List<JsmsUserRole>  getUserIdFromUserRoleByRoleId(String roleId);
		
	List<Long> getUserIdsByRoleName(String roleName);
}