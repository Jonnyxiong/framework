package com.jsmsframework.sysKeyword.service;

import java.util.*;

import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.enums.Code;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.sysKeyword.dto.JsmsSysKeywordCategoryDTO;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.sysKeyword.mapper.JsmsSysKeywordCategoryMapper;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordCategory;
import com.jsmsframework.sysKeyword.service.JsmsSysKeywordCategoryService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统关键字分类表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Service
public class JsmsSysKeywordCategoryServiceImpl implements JsmsSysKeywordCategoryService{

    @Autowired
    private JsmsSysKeywordCategoryMapper sysKeywordCategoryMapper;
    
    @Override
    public int insert(JsmsSysKeywordCategory model) {
        return this.sysKeywordCategoryMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsSysKeywordCategory> modelList) {
        return this.sysKeywordCategoryMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsSysKeywordCategory model) {
		JsmsSysKeywordCategory old = this.sysKeywordCategoryMapper.getByCategoryId(model.getCategoryId());
		if(old == null){
			return 0;
		}
		return this.sysKeywordCategoryMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsSysKeywordCategory model) {
		JsmsSysKeywordCategory old = this.sysKeywordCategoryMapper.getByCategoryId(model.getCategoryId());
		if(old != null)
        	return this.sysKeywordCategoryMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsSysKeywordCategory getByCategoryId(Integer categoryId) {
        JsmsSysKeywordCategory model = this.sysKeywordCategoryMapper.getByCategoryId(categoryId);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSysKeywordCategory> list = this.sysKeywordCategoryMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsSysKeywordCategory> findList(Map params) {
        List<JsmsSysKeywordCategory> list = this.sysKeywordCategoryMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.sysKeywordCategoryMapper.count(params);
    }

    @Override
    public JsmsPage<JsmsSysKeywordCategory> queryKeywordCategoryPage(JsmsPage jsmsPage) {
        jsmsPage.setOrderByClause("update_date desc,category_id desc");
        List<JsmsSysKeywordCategory> list = this.sysKeywordCategoryMapper.queryList(jsmsPage);
        List<JsmsSysKeywordCategoryDTO> result = new ArrayList<>();
        if (list!=null && list.size()>0){
            int rownum = 1;
            for (JsmsSysKeywordCategory jsmsSysKeywordCategory : list) {
                JsmsSysKeywordCategoryDTO jsmsSysKeywordCategoryDTO = new JsmsSysKeywordCategoryDTO();
                BeanUtils.copyProperties(jsmsSysKeywordCategory,jsmsSysKeywordCategoryDTO);
                jsmsSysKeywordCategoryDTO.setRownum(jsmsPage.getRows() * (jsmsPage.getPage() - 1) + rownum);
                rownum++;
                result.add(jsmsSysKeywordCategoryDTO);
            }
        }
        jsmsPage.setData(result);
        return jsmsPage;
    }



    @Override
    public List<JsmsSysKeywordCategory> getByCategoryName(String categoryName) {
        List<JsmsSysKeywordCategory> categoryList = this.sysKeywordCategoryMapper.getByCategoryName(categoryName);
        return categoryList;
    }

    @Override
    public int deleteByCategoryId(Integer categoryId) {
        return this.sysKeywordCategoryMapper.deleteByCategoryId(categoryId);
    }

    @Override
    public List<JsmsSysKeywordCategory> getByCategoryIds(Set<Integer> categoryIds) {
        return this.sysKeywordCategoryMapper.getByCategoryIds(categoryIds);
    }

    @Override
    public List<JsmsSysKeywordCategory> findByCategoryName(Map<String,Object> params) {
        return this.sysKeywordCategoryMapper.findByCategoryName(params);
    }
}
