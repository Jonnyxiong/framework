package com.jsmsframework.order.entity.po;

import java.math.BigDecimal;

/**
 * Created by dylan on 2017/10/13.
 */
public class JsmsReturnSendFailPo {

    /**
     * 退费对应的订单id , order_id
     */
    private Long orderId;
    /**
     * 订单子id，
     */
    private Long subId;
    /**
     * 失败返还数量
     */
    private BigDecimal returnNumber;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public BigDecimal getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(BigDecimal returnNumber) {
        this.returnNumber = returnNumber;
    }
}
