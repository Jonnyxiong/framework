package com.jsmsframework.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsDepartment;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 部门表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsDepartmentMapper{

	int insert(JsmsDepartment model);
	
	int insertBatch(List<JsmsDepartment> modelList);

	
	int update(JsmsDepartment model);
	
	int updateSelective(JsmsDepartment model);

    JsmsDepartment getByDepartmentId(Integer departmentId);

	List<JsmsDepartment> queryList(JsmsPage<JsmsDepartment> page);

	int count(Map<String,Object> params);

}