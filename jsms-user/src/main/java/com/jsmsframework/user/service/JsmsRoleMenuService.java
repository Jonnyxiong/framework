package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsRoleMenu;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 角色菜单表
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsRoleMenuService {

    int insert(JsmsRoleMenu model);
    
    int insertBatch(List<JsmsRoleMenu> modelList);

    int update(JsmsRoleMenu model);
    
    int updateSelective(JsmsRoleMenu model);

    JsmsRoleMenu getByRoleMenuId(Integer roleMenuId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);
    
}
