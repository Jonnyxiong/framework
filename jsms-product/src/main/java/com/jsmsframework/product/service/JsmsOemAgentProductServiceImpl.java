package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsOemAgentProduct;
import com.jsmsframework.product.mapper.JsmsOemAgentProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsOemAgentProductServiceImpl implements JsmsOemAgentProductService{

    @Autowired
    private JsmsOemAgentProductMapper oemAgentProductMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOemAgentProduct model) {
        return this.oemAgentProductMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOemAgentProduct> modelList) {
        return this.oemAgentProductMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOemAgentProduct model) {
		JsmsOemAgentProduct old = this.oemAgentProductMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.oemAgentProductMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOemAgentProduct model) {
		JsmsOemAgentProduct old = this.oemAgentProductMapper.getById(model.getId());
		if(old != null)
        	return this.oemAgentProductMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOemAgentProduct getById(Integer id) {
        JsmsOemAgentProduct model = this.oemAgentProductMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOemAgentProduct> list = this.oemAgentProductMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oemAgentProductMapper.count(params);
    }

    @Override
    public JsmsOemAgentProduct getByAgentIdAndProductId(JsmsOemAgentProduct model) {
        return  this.oemAgentProductMapper.getByAgentIdAndProductId(model);
    }
}
