package com.jsmsframework.sysKeyword.service;

import java.util.Map;
import java.util.List;
import java.util.Set;

import com.jsmsframework.sysKeyword.entity.JsmsSysKgroupRefCategory;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 系统关键字组内类别表
 * @author huangwenjie
 * @date 2018-01-15
 */
public interface JsmsSysKgroupRefCategoryService {

    int insert(JsmsSysKgroupRefCategory model);
    
    int insertBatch(List<JsmsSysKgroupRefCategory> modelList);

    int update(JsmsSysKgroupRefCategory model);
    
    int updateSelective(JsmsSysKgroupRefCategory model);

    JsmsSysKgroupRefCategory getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsSysKgroupRefCategory> findList(Map params);

    int count(Map<String,Object> params);

    int deleteByGroupId(Integer kgroupId);

    List<JsmsSysKgroupRefCategory> getByKgroupId(Integer kgroupId);

    List<JsmsSysKgroupRefCategory> findInCategoryId(Set categoryIds);
}
