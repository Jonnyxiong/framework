package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsAuditKgroupRefCategory;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核关键字组内类别表
 * @author huangwenjie
 * @date 2017-10-31
 */
public interface JsmsAuditKgroupRefCategoryService {

    int insert(JsmsAuditKgroupRefCategory model);
    
    int insertBatch(List<JsmsAuditKgroupRefCategory> modelList);

    int update(JsmsAuditKgroupRefCategory model);
    
    int updateSelective(JsmsAuditKgroupRefCategory model);

    JsmsAuditKgroupRefCategory getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsAuditKgroupRefCategory> getBykgroupId(Integer kgroupId);

    List<JsmsAuditKgroupRefCategory> getBykgroupIds(List<Integer> keywordGroupIds);

    List<JsmsAuditKgroupRefCategory> getByCategoryId(Integer categoryId);
}
