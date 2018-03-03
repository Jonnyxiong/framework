package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsAgentProduct;
import com.jsmsframework.product.entity.JsmsClientProduct;
import com.jsmsframework.product.mapper.JsmsAgentProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsAgentProductServiceImpl implements JsmsAgentProductService{

    @Autowired
    private JsmsAgentProductMapper agentProductMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentProduct model) {
        return this.agentProductMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentProduct> modelList) {
        return this.agentProductMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentProduct model) {
		JsmsAgentProduct old = this.agentProductMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.agentProductMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentProduct model) {
		JsmsAgentProduct old = this.agentProductMapper.getById(model.getId());
		if(old != null)
        	return this.agentProductMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentProduct getById(Integer id) {
        JsmsAgentProduct model = this.agentProductMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public List<JsmsAgentProduct> getByAgentId(Integer agentId) {
        List<JsmsAgentProduct> jsmsAgentProducts = this.agentProductMapper.getByAgentId(agentId);
        return jsmsAgentProducts;
    }

    @Override
	@Transactional
    public List<JsmsAgentProduct> getByAgentIds(Set<Integer> agentIds) {
        List<JsmsAgentProduct> jsmsAgentProducts = this.agentProductMapper.getByAgentIds(agentIds);
        return jsmsAgentProducts;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentProduct> list = this.agentProductMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentProductMapper.count(params);
    }


    @Override
    public boolean isNotSaleForThisAgent(Integer agentId, Integer productId) {
        List<JsmsAgentProduct> jsmsAgentProducts = getByAgentId(agentId);
        if(jsmsAgentProducts==null||jsmsAgentProducts.size()==0)
            return false;
        for(JsmsAgentProduct jsmsAgentProduct:jsmsAgentProducts){
            if(jsmsAgentProduct.getId().intValue()==productId.intValue()){
                return true;
            }
        }
        return false;
    }
}
