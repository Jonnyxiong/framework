package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsUserRole;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 用户角色关系表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsUserRoleService {

    int insert(JsmsUserRole model);
    
    int insertBatch(List<JsmsUserRole> modelList);

    int update(JsmsUserRole model);
    
    int updateSelective(JsmsUserRole model);

    JsmsUserRole getByRuId(Integer ruId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsUserRole>  getUserIdFromUserRoleByRoleId(String roleId);
}
