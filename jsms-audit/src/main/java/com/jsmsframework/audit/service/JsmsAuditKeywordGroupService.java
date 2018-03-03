package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsAuditKeywordGroup;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核关键字分组表
 * @author huangwenjie
 * @date 2017-10-31
 */
public interface JsmsAuditKeywordGroupService {

    int insert(JsmsAuditKeywordGroup model,List<String> categoryNameList);
    
    int insertBatch(List<JsmsAuditKeywordGroup> modelList);

    int update(JsmsAuditKeywordGroup model,List<String> categoryNameList);
    
    int updateSelective(JsmsAuditKeywordGroup model);

    JsmsAuditKeywordGroup getByKgroupId(Integer kgroupId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    int checkKgroupName( String kgroupName, Integer kgroupId);

    int checkKgroupNameOfInsert(String kgroupName);

    int deteleJsmsAuditKeywordGroup(Integer kgroupId);

    List<JsmsAuditKeywordGroup> getList();

    List<JsmsAuditKeywordGroup> getByRefCategoryId(Integer categoryId);


    List<String>  getRefClientIdsByGroupId(Integer groupId);
}
