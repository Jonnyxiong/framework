package com.jsmsframework.audit.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsAuditkeywordRecordMapper;
import com.jsmsframework.audit.entity.JsmsAuditkeywordRecord;
import com.jsmsframework.audit.service.JsmsAuditkeywordRecordService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 短信审核与关键字记录表
 * @author huangwenjie
 * @date 2017-09-12
 */
@Service
public class JsmsAuditkeywordRecordServiceImpl implements JsmsAuditkeywordRecordService{

    @Autowired
    private JsmsAuditkeywordRecordMapper auditkeywordRecordMapper;
    
    @Override
    @CachePut(value ="recordCache")
    public int insert(JsmsAuditkeywordRecord model) {
        return this.auditkeywordRecordMapper.insert(model);
    }

    @Override
    @CachePut(value ="recordCache")
    public int insertBatch(List<JsmsAuditkeywordRecord> modelList) {
        return this.auditkeywordRecordMapper.insertBatch(modelList);
    }

	@Override
    @CachePut(value ="recordCache")
    public int update(JsmsAuditkeywordRecord model) {
		JsmsAuditkeywordRecord old = this.auditkeywordRecordMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.auditkeywordRecordMapper.update(model); 
    }

    @Override
    @CachePut(value ="recordCache")
    public int updateSelective(JsmsAuditkeywordRecord model) {
		JsmsAuditkeywordRecord old = this.auditkeywordRecordMapper.getById(model.getId());
		if(old != null)
        	return this.auditkeywordRecordMapper.updateSelective(model);
		return 0;
    }

    @Override
    @Cacheable(value="recordCache")
    public JsmsAuditkeywordRecord getById(Integer id) {
        JsmsAuditkeywordRecord model = this.auditkeywordRecordMapper.getById(id);
		return model;
    }

    @Override
    @Cacheable(value="recordCache")
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditkeywordRecord> list = this.auditkeywordRecordMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.auditkeywordRecordMapper.count(params);
    }

    @Override
    @Cacheable(value="recordCache")
    public List<JsmsAuditkeywordRecord> queryAll(Map<String, Object> params) {
        return this.auditkeywordRecordMapper.queryAll(params);
    }

    @Override
    @Transactional
    public List<JsmsAuditkeywordRecord> queryAllRemoveRecordAndCreatetime(Date dateDate) {
        return this.auditkeywordRecordMapper.queryAllRemoveRecordAndCreatetime(dateDate);
    }

    @Override
    @Transactional
    public JsmsAuditkeywordRecord queryByIdAndCreateTime(Long id, Date createtime) {
        if (id == null && createtime == null)
            return null;
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("createtime", createtime);
        return this.auditkeywordRecordMapper.queryByIdAndCreateTime(params);
    }

    @Override
    @Transactional
    public int delByIdAndCreateTime(Long id, Date createtime) {
        if (id == null && createtime == null)
            return 0;
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("createtime", createtime);
        return this.auditkeywordRecordMapper.deleteByIdAndCreatetime(params);
    }

    @Override
    @Transactional
    public int insertWithTableName(JsmsAuditkeywordRecord jsmsAuditkeywordRecord, String tableName) {
        Map params = new HashMap();
        params.put("tableName",tableName);
        params.put("model",jsmsAuditkeywordRecord);
        return this.auditkeywordRecordMapper.insertWithTableName(params);
    }

}
