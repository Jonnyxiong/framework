package com.jsmsframework.sysKeyword.service;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Set;

import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.util.PageExportQualifier;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordCategory;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordGroup;
import org.apache.ibatis.annotations.Param;

/**
 * @description 系统关键字分类表
 * @author huangwenjie
 * @date 2018-01-15
 */
public interface JsmsSysKeywordCategoryService {

    int insert(JsmsSysKeywordCategory model);
    
    int insertBatch(List<JsmsSysKeywordCategory> modelList);

    int update(JsmsSysKeywordCategory model);
    
    int updateSelective(JsmsSysKeywordCategory model);

    JsmsSysKeywordCategory getByCategoryId(Integer categoryId);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsSysKeywordCategory> findList(Map params);

    int count(Map<String,Object> params);

    /**
     * 经过处理的系统关键字分类DTO列表
     * @param jsmsPage
     * @return
     */
    @PageExportQualifier("queryKeywordCategoryPage")
    JsmsPage<JsmsSysKeywordCategory> queryKeywordCategoryPage(JsmsPage jsmsPage);


    /**
     * 通过类别名称查找数据
     * @param categoryName
     * @return
     */
    List<JsmsSysKeywordCategory> getByCategoryName(String categoryName);

    /**
     * 通过id删除类别
     * @param categoryId
     * @return
     */
    int deleteByCategoryId(@Param("categoryId") Integer categoryId);

    /**
     * 通过类别id查找
     * @param categoryIds
     * @return
     */
    List<JsmsSysKeywordCategory> getByCategoryIds(Set<Integer> categoryIds);

    List<JsmsSysKeywordCategory> findByCategoryName(Map<String,Object> params);
}
