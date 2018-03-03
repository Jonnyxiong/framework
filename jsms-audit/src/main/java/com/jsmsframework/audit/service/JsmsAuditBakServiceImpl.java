package com.jsmsframework.audit.service;

import com.jsmsframework.audit.entity.JsmsAudit;
import com.jsmsframework.audit.entity.JsmsAuditBak;
import com.jsmsframework.audit.mapper.JsmsAuditBakMapper;
import com.jsmsframework.common.dto.JsmsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 审核内容表（备份）
 * @author huangwenjie
 * @date 2017-08-29
 */
@Service
public class JsmsAuditBakServiceImpl implements JsmsAuditBakService{

    @Autowired
    private JsmsAuditBakMapper auditBakMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAuditBak model) {
        return this.auditBakMapper.insert(model);
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return this.auditBakMapper.delete(id);
    }

    @Override
    @Transactional
    public int insertFromAudit(JsmsAudit model) {
        return this.auditBakMapper.insertFromAudit(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAuditBak> modelList) {
        return this.auditBakMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int insertBatchFromAudit(List<JsmsAudit> modelList) {
        return this.auditBakMapper.insertBatchFromAudit(modelList);
    }

    @Override
	@Transactional
    public int update(JsmsAuditBak model) {
		JsmsAuditBak old = this.auditBakMapper.getByAuditid(model.getAuditid());
		if(old == null){
			return 0;
		}
		return this.auditBakMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAuditBak model) {
		JsmsAuditBak old = this.auditBakMapper.getByAuditid(model.getAuditid());
		if(old != null)
        	return this.auditBakMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAuditBak getByAuditid(Long auditid) {
        JsmsAuditBak model = this.auditBakMapper.getByAuditid(auditid);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditBak> list = this.auditBakMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditBakMapper.count(params);
    }

    @Override
    public List<JsmsAuditBak> findRepeatList() {
        return this.auditBakMapper.findRepeatList();
    }

    @Override
    @Transactional
    public int batchDeleteAudit(int[] auditIdList) {
        return this.auditBakMapper.batchDeleteAudit(auditIdList);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> queryhisAll(Map<String, Object> params) {
        return this.auditBakMapper.queryhisAll(params);
    }

    @Override
    public int insertWithTableName(JsmsAuditBak model, String tableName) {
        Map params = new HashMap();
        params.put("tableName",tableName);
        params.put("model",model);
        return this.auditBakMapper.insertWithTableName(params);
    }
}
