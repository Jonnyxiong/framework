package com.jsmsframework.product.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.product.exception.JsmsClientProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.product.mapper.JsmsClientProductMapper;
import com.jsmsframework.product.entity.JsmsClientProduct;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 客户产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsClientProductServiceImpl implements JsmsClientProductService{

    @Autowired
    private JsmsClientProductMapper clientProductMapper;
    
    @Override
	@Transactional
    public int insert(JsmsClientProduct model) {
        return this.clientProductMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsClientProduct> modelList) {
        return this.clientProductMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsClientProduct model) {
		JsmsClientProduct old = this.clientProductMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.clientProductMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsClientProduct model) {
		JsmsClientProduct old = this.clientProductMapper.getById(model.getId());
		if(old != null)
        	return this.clientProductMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsClientProduct getById(Integer id) {
        JsmsClientProduct model = this.clientProductMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientProduct> list = this.clientProductMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.clientProductMapper.count(params);
    }

    @Override
    public JsmsClientProduct getByClientidAndProductId(Integer agentId, String clientid, Integer productId) {
        JsmsPage page = new JsmsPage();
        page.getParams().put("clientId",clientid);
        page.getParams().put("productId",productId);
        page.getParams().put("agentId",agentId);
        List<JsmsClientProduct> list = this.clientProductMapper.queryList(page);
        if(list==null||list.size()==0){
            return null;
        }else if(list.size()>1){
            throw  new JsmsClientProductException("数据异常，请联系管理员处理");
        }

        return list.get(0);
    }
}
