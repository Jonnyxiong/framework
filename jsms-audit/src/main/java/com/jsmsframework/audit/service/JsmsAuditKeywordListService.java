package com.jsmsframework.audit.service;

import com.jsmsframework.audit.dto.JsmsAuditKeywordListAndCategoryDTO;
import com.jsmsframework.audit.entity.JsmsAuditKeywordList;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 审核关键字列表
 * @author huangwenjie
 * @date 2017-10-31
 */
public interface JsmsAuditKeywordListService {

    int insert(JsmsAuditKeywordList model);
    
    int insertBatch(List<JsmsAuditKeywordList> modelList);

    int update(JsmsAuditKeywordList model);

    int updateIdempotent(JsmsAuditKeywordList oldModel, JsmsAuditKeywordList newModel);

    int delete(Long id);

    int updateSelective(JsmsAuditKeywordList model);

    JsmsAuditKeywordList getById(Long id);

    JsmsAuditKeywordList getByKeyword(String keyword);

    JsmsAuditKeywordList getByKeywordAndCategoryId(String keyword,Integer categoryId);

    List<JsmsAuditKeywordList> getByKeywords(Set<String> keywords);

    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);


    List<JsmsAuditKeywordList> getByClientId(String clientId);

    List<JsmsAuditKeywordList> getByCategoryIds(List<Integer> categoryIds);


    List<JsmsAuditKeywordListAndCategoryDTO> getListAndCategoryByClientId(String clientId);


    List<Map<String,Object>>   queryByKgroupId(Integer kgroupId);
}
