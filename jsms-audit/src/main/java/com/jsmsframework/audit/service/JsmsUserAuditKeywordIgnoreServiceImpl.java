package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmsframework.audit.mapper.JsmsUserAuditKeywordIgnoreMapper;
import com.jsmsframework.audit.entity.JsmsUserAuditKeywordIgnore;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 用户审核关键字忽略表
 * @author Don
 * @date 2018-01-17
 */
@Service
public class JsmsUserAuditKeywordIgnoreServiceImpl implements JsmsUserAuditKeywordIgnoreService{

    @Autowired
    private JsmsUserAuditKeywordIgnoreMapper userAuditKeywordIgnoreMapper;

    
    @Override
    public int insert(JsmsUserAuditKeywordIgnore model) {
        return this.userAuditKeywordIgnoreMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsUserAuditKeywordIgnore> modelList) {
        return this.userAuditKeywordIgnoreMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsUserAuditKeywordIgnore model) {
		JsmsUserAuditKeywordIgnore old = this.userAuditKeywordIgnoreMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.userAuditKeywordIgnoreMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsUserAuditKeywordIgnore model) {
		JsmsUserAuditKeywordIgnore old = this.userAuditKeywordIgnoreMapper.getById(model.getId());
		if(old != null)
        	return this.userAuditKeywordIgnoreMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsUserAuditKeywordIgnore getById(Integer id) {
        JsmsUserAuditKeywordIgnore model = this.userAuditKeywordIgnoreMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsUserAuditKeywordIgnore> list = this.userAuditKeywordIgnoreMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsUserAuditKeywordIgnore> findList(Map params) {
        List<JsmsUserAuditKeywordIgnore> list = this.userAuditKeywordIgnoreMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.userAuditKeywordIgnoreMapper.count(params);
    }

    @Override
    public int updateStatus(JsmsUserAuditKeywordIgnore model) {
        return this.userAuditKeywordIgnoreMapper.updateStatus(model);
    }

    @Override
    public List<JsmsUserAuditKeywordIgnore> findByClientIdAndSmsType(String clientId, Integer smsType) {
        return this.userAuditKeywordIgnoreMapper.findByClientIdAndSmsType(clientId,smsType);
    }

    @Override
    public List<JsmsUserAuditKeywordIgnore> queryExistData(String clientId, Integer smsType, String ignoreKeyword, String status) {
        return this.userAuditKeywordIgnoreMapper.queryExistData(clientId,smsType,ignoreKeyword,status);
    }
}
