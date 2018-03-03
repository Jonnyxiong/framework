package com.jsmsframework.common.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.common.mapper.JsmsDictMapper;
import com.jsmsframework.common.entity.JsmsDict;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 数据字典表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsDictServiceImpl implements JsmsDictService{

    @Autowired
    private JsmsDictMapper dictMapper;
    
    @Override
	@Transactional
    public int insert(JsmsDict model) {
        return this.dictMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsDict> modelList) {
        return this.dictMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsDict model) {
		JsmsDict old = this.dictMapper.getByParamId(model.getParamId());
		if(old == null){
			return 0;
		}
		return this.dictMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsDict model) {
		JsmsDict old = this.dictMapper.getByParamId(model.getParamId());
		if(old != null)
        	return this.dictMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsDict getByParamId(Integer paramId) {
        JsmsDict model = this.dictMapper.getByParamId(paramId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsDict> list = this.dictMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.dictMapper.count(params);
    }

    @Override
    public List<JsmsDict> findList(Map<String, Object> params) {
        return dictMapper.findList(params);
    }
}
