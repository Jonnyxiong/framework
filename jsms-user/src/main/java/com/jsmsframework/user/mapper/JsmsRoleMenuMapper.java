package com.jsmsframework.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsRoleMenu;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 角色菜单表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsRoleMenuMapper{

	int insert(JsmsRoleMenu model);
	
	int insertBatch(List<JsmsRoleMenu> modelList);

	
	int update(JsmsRoleMenu model);
	
	int updateSelective(JsmsRoleMenu model);

    JsmsRoleMenu getByRoleMenuId(Integer roleMenuId);

	List<JsmsRoleMenu> queryList(JsmsPage<JsmsRoleMenu> page);

	int count(Map<String,Object> params);

}