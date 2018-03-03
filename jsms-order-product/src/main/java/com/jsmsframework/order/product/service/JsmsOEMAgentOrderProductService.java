package com.jsmsframework.order.product.service;

import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.order.entity.JsmsOemAgentOrder;

import java.math.BigDecimal;

public interface JsmsOEMAgentOrderProductService {

    /**
     * @param agentId 代理商Id
     * @param rebateUseRadio 代理商返点使用比例: 【t_sms_agent_info】.rebate_use_radio
     * @param productId 购买的产品Id
     * @param purchaseNum 购买金额或数量
     * @param adminId 操作者Id
     * @param jsmsOemAgentOrder 需要包含oderId和oderNo
     * @return
     */
    public ResultVO purchaseOrder(Integer agentId, BigDecimal rebateUseRadio, Integer productId, BigDecimal purchaseNum, Long adminId, JsmsOemAgentOrder jsmsOemAgentOrder);

}
