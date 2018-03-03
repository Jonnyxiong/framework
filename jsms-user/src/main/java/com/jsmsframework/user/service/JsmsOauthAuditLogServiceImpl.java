package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsOauthAuditLogMapper;
import com.jsmsframework.user.entity.JsmsOauthAuditLog;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 认证审核记录表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsOauthAuditLogServiceImpl implements JsmsOauthAuditLogService{

    @Autowired
    private JsmsOauthAuditLogMapper oauthAuditLogMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOauthAuditLog model) {
        return this.oauthAuditLogMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOauthAuditLog> modelList) {
        return this.oauthAuditLogMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOauthAuditLog model) {
		JsmsOauthAuditLog old = this.oauthAuditLogMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.oauthAuditLogMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOauthAuditLog model) {
		JsmsOauthAuditLog old = this.oauthAuditLogMapper.getById(model.getId());
		if(old != null)
        	return this.oauthAuditLogMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOauthAuditLog getById(Integer id) {
        JsmsOauthAuditLog model = this.oauthAuditLogMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOauthAuditLog> list = this.oauthAuditLogMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oauthAuditLogMapper.count(params);
    }
    
}
