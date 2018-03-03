package com.jsmsframework.audit.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsAudit0Mapper;
import com.jsmsframework.audit.entity.JsmsAudit0;
import com.jsmsframework.audit.service.JsmsAudit0Service;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核内容表(周更新,原始表)
 * @author huangwenjie
 * @date 2017-11-28
 */
@Service
public class JsmsAudit0ServiceImpl implements JsmsAudit0Service{

    @Autowired
    private JsmsAudit0Mapper audit0Mapper;
    
    @Override
	@Transactional
    public int insert(JsmsAudit0 model) {
        return this.audit0Mapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAudit0> modelList) {
        return this.audit0Mapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAudit0 model) {
		JsmsAudit0 old = this.audit0Mapper.getByAuditid(model.getAuditid());
		if(old == null){
			return 0;
		}
		return this.audit0Mapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAudit0 model) {
		JsmsAudit0 old = this.audit0Mapper.getByAuditid(model.getAuditid());
		if(old != null)
        	return this.audit0Mapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAudit0 getByAuditid(Long auditid) {
        JsmsAudit0 model = this.audit0Mapper.getByAuditid(auditid);
		return model;
    }

    @Override
	@Transactional
    public List<JsmsAudit0> queryList(Map<String,Object> params) {
        List<JsmsAudit0> list = this.audit0Mapper.queryList(params);
        return list;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.audit0Mapper.count(params);
    }
    
}
