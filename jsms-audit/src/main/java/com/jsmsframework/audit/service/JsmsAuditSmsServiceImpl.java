package com.jsmsframework.audit.service;

import com.jsmsframework.audit.dto.JsmsIdAndCreatetime;
import com.jsmsframework.audit.entity.JsmsAuditSms;
import com.jsmsframework.audit.mapper.JsmsAuditSmsMapper;
import com.jsmsframework.common.dto.JsmsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 审核明细表
 * @author huangwenjie
 * @date 2017-08-29
 */
@Service
public class JsmsAuditSmsServiceImpl implements JsmsAuditSmsService{

    @Autowired
    private JsmsAuditSmsMapper auditSmsMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAuditSms model) {
        return this.auditSmsMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAuditSms> modelList) {
        return this.auditSmsMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return this.auditSmsMapper.delete(id);
    }

    @Override
	@Transactional
    public int update(JsmsAuditSms model) {
		JsmsAuditSms old = this.auditSmsMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.auditSmsMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAuditSms model) {
		JsmsAuditSms old = this.auditSmsMapper.getById(model.getId());
		if(old != null)
        	return this.auditSmsMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAuditSms getById(Long id) {
        JsmsAuditSms model = this.auditSmsMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditSms> list = this.auditSmsMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditSmsMapper.count(params);
    }

    @Override
    public Map<String, Object> getNeedBakCount(int day) {
        return this.auditSmsMapper.getNeedBakCount(day);
    }

    @Override
    public List<JsmsAuditSms> findNeedBakList(int min, int max) {
        return this.auditSmsMapper.findNeedBakList(min, max);
    }

    @Override
    public int batchDeleteAuditSms(int[] auditSmsIdList) {
        return this.auditSmsMapper.batchDeleteAuditSms(auditSmsIdList);
    }

    @Override
    public List<Long> hasBakButNotDel() {
        return this.auditSmsMapper.hasBakButNotDel();
    }


    @Override
    public List<JsmsIdAndCreatetime> queryAllRemoveIdAndCreatetime(Date dataDate) {
        return this.auditSmsMapper.queryAllRemoveIdAndCreatetime(dataDate);
    }

    @Override
    public JsmsAuditSms getByIdAndCreatetime(Long id, Date createtime) {
        if(id==null&&createtime==null)
            return null;
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        params.put("createtime",createtime);
        return this.auditSmsMapper.getByIdAndCreatetime(params);
    }

    @Override
    public int deleteByIdAndCreatetime(Long id, Date createtime) {
        if(id==null&&createtime==null)
            return 0;
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        params.put("createtime",createtime);
        return this.auditSmsMapper.deleteByIdAndCreatetime(params);
    }
}
