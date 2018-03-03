package com.jsmsframework.audit.service;

import com.jsmsframework.audit.entity.JsmsAuditSms;
import com.jsmsframework.audit.entity.JsmsAuditSmsBak;
import com.jsmsframework.audit.mapper.JsmsAuditSmsBakMapper;
import com.jsmsframework.common.dto.JsmsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 审核明细表（备份）
 * @author huangwenjie
 * @date 2017-08-29
 */
@Service
public class JsmsAuditSmsBakServiceImpl implements JsmsAuditSmsBakService{

    @Autowired
    private JsmsAuditSmsBakMapper auditSmsBakMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAuditSmsBak model) {
        return this.auditSmsBakMapper.insert(model);
    }

    @Override
    @Transactional
    public int insertFromAuditSms(JsmsAuditSms model) {
        return this.auditSmsBakMapper.insertFromAuditSms(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAuditSmsBak> modelList) {
        return this.auditSmsBakMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int insertBatchFromAuditSms(List<JsmsAuditSms> modelList) {
        return this.auditSmsBakMapper.insertBatchFromAuditSms(modelList);
    }

    @Override
    public int delete(Long id) {
        return this.auditSmsBakMapper.delete(id);
    }

    @Override
	@Transactional
    public int update(JsmsAuditSmsBak model) {
		JsmsAuditSmsBak old = this.auditSmsBakMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.auditSmsBakMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAuditSmsBak model) {
		JsmsAuditSmsBak old = this.auditSmsBakMapper.getById(model.getId());
		if(old != null)
        	return this.auditSmsBakMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAuditSmsBak getById(Long id) {
        JsmsAuditSmsBak model = this.auditSmsBakMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditSmsBak> list = this.auditSmsBakMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditSmsBakMapper.count(params);
    }

    @Override
    public List<JsmsAuditSmsBak> findRepeatList() {
        return this.auditSmsBakMapper.findRepeatList();
    }

    @Override
    @Transactional
    public int batchDeleteAuditSms(int[] auditSmsIdList) {
        return this.auditSmsBakMapper.batchDeleteAuditSms(auditSmsIdList);
    }


}
