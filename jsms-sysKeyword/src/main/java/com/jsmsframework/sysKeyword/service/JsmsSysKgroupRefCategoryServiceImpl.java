package com.jsmsframework.sysKeyword.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.sysKeyword.mapper.JsmsSysKgroupRefCategoryMapper;
import com.jsmsframework.sysKeyword.entity.JsmsSysKgroupRefCategory;
import com.jsmsframework.sysKeyword.service.JsmsSysKgroupRefCategoryService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统关键字组内类别表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Service
public class JsmsSysKgroupRefCategoryServiceImpl implements JsmsSysKgroupRefCategoryService{

    @Autowired
    private JsmsSysKgroupRefCategoryMapper sysKgroupRefCategoryMapper;
    
    @Override
    public int insert(JsmsSysKgroupRefCategory model) {
        return this.sysKgroupRefCategoryMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsSysKgroupRefCategory> modelList) {
        return this.sysKgroupRefCategoryMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsSysKgroupRefCategory model) {
		JsmsSysKgroupRefCategory old = this.sysKgroupRefCategoryMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.sysKgroupRefCategoryMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsSysKgroupRefCategory model) {
		JsmsSysKgroupRefCategory old = this.sysKgroupRefCategoryMapper.getById(model.getId());
		if(old != null)
        	return this.sysKgroupRefCategoryMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsSysKgroupRefCategory getById(Integer id) {
        JsmsSysKgroupRefCategory model = this.sysKgroupRefCategoryMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSysKgroupRefCategory> list = this.sysKgroupRefCategoryMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsSysKgroupRefCategory> findList(Map params) {
        List<JsmsSysKgroupRefCategory> list = this.sysKgroupRefCategoryMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.sysKgroupRefCategoryMapper.count(params);
    }

    @Override
    public int deleteByGroupId(Integer kgroupId) {
        return this.sysKgroupRefCategoryMapper.deleteByGroupId(kgroupId);
    }

    @Override
    public List<JsmsSysKgroupRefCategory> getByKgroupId(Integer kgroupId) {
        return this.sysKgroupRefCategoryMapper.getByKgroupId(kgroupId);
    }

    @Override
    public List<JsmsSysKgroupRefCategory> findInCategoryId(Set categoryIds) {
        return this.sysKgroupRefCategoryMapper.findInCategoryId(categoryIds);
    }
}
