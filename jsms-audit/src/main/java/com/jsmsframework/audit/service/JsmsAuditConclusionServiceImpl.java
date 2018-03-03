package com.jsmsframework.audit.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsAuditConclusionMapper;
import com.jsmsframework.audit.entity.JsmsAuditConclusion;
import com.jsmsframework.audit.service.JsmsAuditConclusionService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核原因表
 * @author huangwenjie
 * @date 2017-09-12
 */
@Service
public class JsmsAuditConclusionServiceImpl implements JsmsAuditConclusionService{

    @Autowired
    private JsmsAuditConclusionMapper auditConclusionMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAuditConclusion model) {
        return this.auditConclusionMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAuditConclusion> modelList) {
        return this.auditConclusionMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAuditConclusion model) {
		JsmsAuditConclusion old = this.auditConclusionMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.auditConclusionMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAuditConclusion model) {
		JsmsAuditConclusion old = this.auditConclusionMapper.getById(model.getId());
		if(old != null)
        	return this.auditConclusionMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAuditConclusion getById(Integer id) {
        JsmsAuditConclusion model = this.auditConclusionMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditConclusion> list = this.auditConclusionMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditConclusionMapper.count(params);
    }

    @Override
    public List<JsmsAuditConclusion> findAllList(String remark) {
        return this.auditConclusionMapper.findAllList(remark);
    }
    
}
