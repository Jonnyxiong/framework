package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.order.entity.po.JsmsClientOrderPo;
import com.jsmsframework.order.entity.po.JsmsOemAgentPoolPo;

import java.util.List;

public interface JsmsOrderFinanceService {

	public R agentOrderReturnQuantity(List<JsmsClientOrderPo> clientOrderPos, Long adminId, String url, String ip);

	public R agentPoolReturnQuantity(List<JsmsOemAgentPoolPo> oemAgentPoolPos, Long adminId, String url, String ip, List<Long> orderIdList);

}
