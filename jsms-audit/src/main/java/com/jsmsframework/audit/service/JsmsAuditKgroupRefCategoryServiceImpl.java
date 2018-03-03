package com.jsmsframework.audit.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsAuditKgroupRefCategoryMapper;
import com.jsmsframework.audit.entity.JsmsAuditKgroupRefCategory;
import com.jsmsframework.audit.service.JsmsAuditKgroupRefCategoryService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核关键字组内类别表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Service
public class JsmsAuditKgroupRefCategoryServiceImpl implements JsmsAuditKgroupRefCategoryService{

    @Autowired
    private JsmsAuditKgroupRefCategoryMapper auditKgroupRefCategoryMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAuditKgroupRefCategory model) {
        return this.auditKgroupRefCategoryMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAuditKgroupRefCategory> modelList) {
        return this.auditKgroupRefCategoryMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAuditKgroupRefCategory model) {
		JsmsAuditKgroupRefCategory old = this.auditKgroupRefCategoryMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.auditKgroupRefCategoryMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAuditKgroupRefCategory model) {
		JsmsAuditKgroupRefCategory old = this.auditKgroupRefCategoryMapper.getById(model.getId());
		if(old != null)
        	return this.auditKgroupRefCategoryMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAuditKgroupRefCategory getById(Integer id) {
        JsmsAuditKgroupRefCategory model = this.auditKgroupRefCategoryMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditKgroupRefCategory> list = this.auditKgroupRefCategoryMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditKgroupRefCategoryMapper.count(params);
    }

    @Override
    @Transactional
    public List<JsmsAuditKgroupRefCategory> getBykgroupId(Integer kgroupId) {
        List<JsmsAuditKgroupRefCategory> model = this.auditKgroupRefCategoryMapper.getBykgroupId(kgroupId);
        return model;
    }

    @Override
    public List<JsmsAuditKgroupRefCategory> getBykgroupIds(List<Integer> keywordGroupIds) {
        List<JsmsAuditKgroupRefCategory> model = this.auditKgroupRefCategoryMapper.getBykgroupIds(keywordGroupIds);
        return model;
    }

    @Override
    public List<JsmsAuditKgroupRefCategory> getByCategoryId(Integer categoryId) {


        Map params = new HashMap();
        params.put("categoryId",categoryId);
        return this.auditKgroupRefCategoryMapper.findList(params);
    }
}
