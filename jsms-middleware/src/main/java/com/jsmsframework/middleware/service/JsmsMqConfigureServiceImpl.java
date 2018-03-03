package com.jsmsframework.middleware.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.middleware.mapper.JsmsMqConfigureMapper;
import com.jsmsframework.middleware.entity.JsmsMqConfigure;
import com.jsmsframework.middleware.service.JsmsMqConfigureService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description MQ配置表
 * @author huangwenjie
 * @date 2017-11-04
 */
@Service
public class JsmsMqConfigureServiceImpl implements JsmsMqConfigureService{

    @Autowired
    private JsmsMqConfigureMapper mqConfigureMapper;
    
    @Override
	@Transactional
    public int insert(JsmsMqConfigure model) {
        return this.mqConfigureMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsMqConfigure> modelList) {
        return this.mqConfigureMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsMqConfigure model) {
		JsmsMqConfigure old = this.mqConfigureMapper.getByMqId(model.getMqId());
		if(old == null){
			return 0;
		}
		return this.mqConfigureMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsMqConfigure model) {
		JsmsMqConfigure old = this.mqConfigureMapper.getByMqId(model.getMqId());
		if(old != null)
        	return this.mqConfigureMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsMqConfigure getByMqId(Integer mqId) {
        JsmsMqConfigure model = this.mqConfigureMapper.getByMqId(mqId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsMqConfigure> list = this.mqConfigureMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.mqConfigureMapper.count(params);
    }
    
}
