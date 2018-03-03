package com.jsmsframework.audit.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsAuditCgroupRefClientMapper;
import com.jsmsframework.audit.entity.JsmsAuditCgroupRefClient;
import com.jsmsframework.audit.service.JsmsAuditCgroupRefClientService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核用户组内用户表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Service
public class JsmsAuditCgroupRefClientServiceImpl implements JsmsAuditCgroupRefClientService{

    @Autowired
    private JsmsAuditCgroupRefClientMapper auditCgroupRefClientMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAuditCgroupRefClient model) {
        return this.auditCgroupRefClientMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAuditCgroupRefClient> modelList) {
        return this.auditCgroupRefClientMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAuditCgroupRefClient model) {
		JsmsAuditCgroupRefClient old = this.auditCgroupRefClientMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.auditCgroupRefClientMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAuditCgroupRefClient model) {
		JsmsAuditCgroupRefClient old = this.auditCgroupRefClientMapper.getById(model.getId());
		if(old != null)
        	return this.auditCgroupRefClientMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAuditCgroupRefClient getById(Integer id) {
        JsmsAuditCgroupRefClient model = this.auditCgroupRefClientMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditCgroupRefClient> list = this.auditCgroupRefClientMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditCgroupRefClientMapper.count(params);
    }

    @Override
    public List<JsmsAuditCgroupRefClient>  getCgroupId(Integer cgroupId) {
        return auditCgroupRefClientMapper.getCgroupId(cgroupId);
    }

    @Override
    public int deteleJsmsAuditCgroupRefClient(Integer cgroupId) {
        return auditCgroupRefClientMapper.deteleJsmsAuditCgroupRefClient(cgroupId);
    }

    @Override
    public JsmsAuditCgroupRefClient getByClientId(String clientId) {
        return this.auditCgroupRefClientMapper.getByClientId(clientId);
    }

}
