package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsOemAgentOrder;
import com.jsmsframework.order.enums.OEMAgentOrderType;
import com.jsmsframework.order.mapper.JsmsOemAgentOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商订单
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsOemAgentOrderServiceImpl implements JsmsOemAgentOrderService{

    @Autowired
    private JsmsOemAgentOrderMapper oemAgentOrderMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOemAgentOrder model) {
        return this.oemAgentOrderMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOemAgentOrder> modelList) {
        return this.oemAgentOrderMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOemAgentOrder model) {
		JsmsOemAgentOrder old = this.oemAgentOrderMapper.getByOrderId(model.getOrderId());
		if(old == null){
			return 0;
		}
		return this.oemAgentOrderMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOemAgentOrder model) {
		JsmsOemAgentOrder old = this.oemAgentOrderMapper.getByOrderId(model.getOrderId());
		if(old != null)
        	return this.oemAgentOrderMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOemAgentOrder getByOrderId(Long orderId) {
        JsmsOemAgentOrder model = this.oemAgentOrderMapper.getByOrderId(orderId);
		return model;
    }

    @Override
	@Transactional
    public List<JsmsOemAgentOrder> getSumByOrderType(OEMAgentOrderType orderType,Integer agentId) {
        List<JsmsOemAgentOrder> modelList = this.oemAgentOrderMapper.getSumByOrderType(orderType.getValue(),agentId);
		return modelList;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOemAgentOrder> list = this.oemAgentOrderMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oemAgentOrderMapper.count(params);
    }
    
}
