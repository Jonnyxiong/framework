package com.jsmsframework.audit.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsAuditkeywordRecord0Mapper;
import com.jsmsframework.audit.entity.JsmsAuditkeywordRecord0;
import com.jsmsframework.audit.service.JsmsAuditkeywordRecord0Service;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 短信审核与关键字记录分表原始表
 * @author fanghaidong
 * @date 2017-12-25
 */
@Service
public class JsmsAuditkeywordRecord0ServiceImpl implements JsmsAuditkeywordRecord0Service{

    @Autowired
    private JsmsAuditkeywordRecord0Mapper auditkeywordRecord0Mapper;
    
    @Override
    @CachePut(value ="recordCache")
    public int insert(JsmsAuditkeywordRecord0 model) {
        return this.auditkeywordRecord0Mapper.insert(model);
    }

    @Override
    @CachePut(value ="recordCache")
    public int insertBatch(List<JsmsAuditkeywordRecord0> modelList) {
        return this.auditkeywordRecord0Mapper.insertBatch(modelList);
    }

	@Override
    @CachePut(value ="recordCache")
    public int update(JsmsAuditkeywordRecord0 model) {
		JsmsAuditkeywordRecord0 old = this.auditkeywordRecord0Mapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.auditkeywordRecord0Mapper.update(model); 
    }

    @Override
    @CachePut(value ="recordCache")
    public int updateSelective(JsmsAuditkeywordRecord0 model) {
		JsmsAuditkeywordRecord0 old = this.auditkeywordRecord0Mapper.getById(model.getId());
		if(old != null)
        	return this.auditkeywordRecord0Mapper.updateSelective(model);
		return 0;
    }

    @Override
    @Cacheable(value="recordCache")
    public JsmsAuditkeywordRecord0 getById(Integer id) {
        JsmsAuditkeywordRecord0 model = this.auditkeywordRecord0Mapper.getById(id);
		return model;
    }

    @Override
    @Cacheable(value="recordCache")
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditkeywordRecord0> list = this.auditkeywordRecord0Mapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    @Cacheable(value="recordCache")
    public int count(Map<String,Object> params) {
		return this.auditkeywordRecord0Mapper.count(params);
    }

    @Override
    @Cacheable(value="recordCache")
    public List<JsmsAuditkeywordRecord0> queryAll(Map<String, Object> params) {
        return this.auditkeywordRecord0Mapper.queryAll(params);
    }

}
