package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsClientConsumerList;
import com.jsmsframework.finance.mapper.JsmsClientConsumerListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 品牌客户消耗记录
 * @author lpjLiu
 * @date 2017-10-11
 */
@Service
public class JsmsClientConsumerListServiceImpl implements JsmsClientConsumerListService {

    @Autowired
    private JsmsClientConsumerListMapper clientConsumerListMapper;
    
    @Override
	@Transactional
    public int insert(JsmsClientConsumerList model) {
        return this.clientConsumerListMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsClientConsumerList> modelList) {
        return this.clientConsumerListMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsClientConsumerList model) {
		JsmsClientConsumerList old = this.clientConsumerListMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.clientConsumerListMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsClientConsumerList model) {
		JsmsClientConsumerList old = this.clientConsumerListMapper.getById(model.getId());
		if(old != null)
        	return this.clientConsumerListMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsClientConsumerList getById(Integer id) {
        JsmsClientConsumerList model = this.clientConsumerListMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientConsumerList> list = this.clientConsumerListMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.clientConsumerListMapper.count(params);
    }

    @Override
    public Integer queryBrandConsumeTotal(Map params) {
        return this.clientConsumerListMapper.queryBrandConsumeTotal(params);
    }
}
