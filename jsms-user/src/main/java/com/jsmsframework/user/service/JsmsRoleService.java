package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsRole;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 角色表
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsRoleService {

    int insert(JsmsRole model);
    
    int insertBatch(List<JsmsRole> modelList);

    int update(JsmsRole model);
    
    int updateSelective(JsmsRole model);

    JsmsRole getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    String getSaleRoleId();

    Integer queryOemRoleId();
}
