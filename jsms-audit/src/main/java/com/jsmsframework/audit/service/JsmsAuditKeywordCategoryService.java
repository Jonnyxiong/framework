package com.jsmsframework.audit.service;

import com.jsmsframework.audit.entity.JsmsAuditKeywordCategory;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.ResultVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 审核关键字分类表
 * @author huangwenjie
 * @date 2017-10-31
 */
public interface JsmsAuditKeywordCategoryService {

    int insert(JsmsAuditKeywordCategory model);
    
    int insertBatch(List<JsmsAuditKeywordCategory> modelList);

    int update(JsmsAuditKeywordCategory model);

    int updateIdempotent(JsmsAuditKeywordCategory oldModel, JsmsAuditKeywordCategory newModel);

    int updateSelective(JsmsAuditKeywordCategory model);

    JsmsAuditKeywordCategory getByCategoryId(Integer categoryId);

    List<JsmsAuditKeywordCategory> getByCategoryName(String categoryName);

    List<JsmsAuditKeywordCategory> getByCategoryNames(Set<String> categoryNames);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsAuditKeywordCategory> getList();

    List<JsmsAuditKeywordCategory> getExitList(Integer kgroupId);

    ResultVO delete(Integer categoryId);

    List<JsmsAuditKeywordCategory> getByCategoryIds(List<Integer> categoryIds);

    List<String> getRefClientIdsByCategoryId(Integer categoryId);
}
