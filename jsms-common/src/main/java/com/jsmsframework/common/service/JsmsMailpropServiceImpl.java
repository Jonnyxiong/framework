package com.jsmsframework.common.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.common.mapper.JsmsMailpropMapper;
import com.jsmsframework.common.entity.JsmsMailprop;
import com.jsmsframework.common.service.JsmsMailpropService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 邮件配置表
 * @author huangwenjie
 * @date 2017-11-27
 */
@Service
public class JsmsMailpropServiceImpl implements JsmsMailpropService{

    @Autowired
    private JsmsMailpropMapper mailpropMapper;
    
    @Override
	@Transactional
    public int insert(JsmsMailprop model) {
        return this.mailpropMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsMailprop> modelList) {
        return this.mailpropMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsMailprop model) {
		JsmsMailprop old = this.mailpropMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.mailpropMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsMailprop model) {
		JsmsMailprop old = this.mailpropMapper.getById(model.getId());
		if(old != null)
        	return this.mailpropMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsMailprop getById(Long id) {
        JsmsMailprop model = this.mailpropMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsMailprop> list = this.mailpropMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.mailpropMapper.count(params);
    }

    @Override
    public JsmsMailprop querySmsMailprop(Integer id) {
        return mailpropMapper.querySmsMailprop(id);
    }
}
