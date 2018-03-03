package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsOemAgentOrder;
import com.jsmsframework.order.enums.OEMAgentOrderType;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商订单
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsOemAgentOrderService {

    int insert(JsmsOemAgentOrder model);
    
    int insertBatch(List<JsmsOemAgentOrder> modelList);

    int update(JsmsOemAgentOrder model);
    
    int updateSelective(JsmsOemAgentOrder model);

    public JsmsOemAgentOrder getByOrderId(Long orderId);

    public List<JsmsOemAgentOrder> getSumByOrderType(OEMAgentOrderType orderType, Integer agentId);

    JsmsPage queryList(JsmsPage page);

    int count(Map<String,Object> params);

}
