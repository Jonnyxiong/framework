package com.jsmsframework.order.product.service;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.enums.ClientOrderType;

import java.util.List;

public interface JsmsClientOrderProductService {

    /**
     * 品牌客户订单创建接口
     * @param productCodes  购买的商品
     * @param purchaseNums  购买数量
     * @param orderIds    订单号
     * @param clientOrderType 品牌订单类型，0：客户购买，1：代理商代买, 2:运营代买
     * @param agentId    代理商ID
     * @param clientid  客户ID
     * @return
     */
    Result createOrder(List<String> productCodes, List<Integer> purchaseNums, List<Long> orderIds, ClientOrderType clientOrderType, Integer agentId, String clientid);


    ResultVO confirmBuy(List<Long> subIds, Integer agentId, Long adminId);

    ResultVO confirmBuy(List<Long> subIds, Long adminId);

    ResultVO confirmBuyOrder(List<Long> orderIds, Integer agentId, Long adminId);

}
