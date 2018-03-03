package com.jsmsframework.sysKeyword.service;

import java.util.Map;
import java.util.List;
import java.util.Set;

import com.jsmsframework.sysKeyword.entity.JsmsKeywordList;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 系统关键字列表
 * @author huangwenjie
 * @date 2018-01-15
 */
public interface JsmsSysKeywordListService {

    int insert(JsmsKeywordList model);
    
    int insertBatch(List<JsmsKeywordList> modelList);

    int update(JsmsKeywordList model);
    
    int updateSelective(JsmsKeywordList model);

    JsmsKeywordList getById(Long id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsKeywordList> findList(Map params);

    int count(Map<String,Object> params);

    int deleteById(Long id);

    /**
     * 通过关键字名称、类型id查找系统关键字
     * @param keywords
     * @return
     */
    List<JsmsKeywordList> findByKeywordsAndCategoryId(Set<String> keywords,Integer categoryId);

    /**
     * 通过类别找到关键字（只返回关键字）
     * @param categoryId
     * @return
     */
    List<String> queryKeywordByCategoryId(Integer categoryId);

    /**
     * 查找对应账号的系统关键字
     * @param clientId
     * @return
     */
    public List<String> queryKeywordByClientId(String clientId);
}
