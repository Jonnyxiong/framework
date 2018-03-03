package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsDepartment;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 部门表
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsDepartmentService {

    int insert(JsmsDepartment model);
    
    int insertBatch(List<JsmsDepartment> modelList);

    int update(JsmsDepartment model);
    
    int updateSelective(JsmsDepartment model);

    JsmsDepartment getByDepartmentId(Integer departmentId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsDepartment getFistLevelDeparment(Integer subDepartmentId);
}
