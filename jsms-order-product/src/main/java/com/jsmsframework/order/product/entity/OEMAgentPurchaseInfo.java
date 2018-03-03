package com.jsmsframework.order.product.entity;

import java.math.BigDecimal;

/**
 * Created by dylan on 2017/8/20.
 */
public class OEMAgentPurchaseInfo {

    /**
     * 订单金额 =  折扣价*数量(折扣之后总价)
     */
    private BigDecimal orderAmount;
    /**
     * 订单实际消耗金额 (折后总价 - 可使用的返点)
     * 普通 : 折扣之后的价格*数量 - 可以使用的返点
     * 国际 : 折扣之后的价格*数量
     */
    private BigDecimal actualOrderPrice;

    /**
     * 是否使用返点
     */
    private boolean isUseRebate;

    public BigDecimal getOrderAmount() {
        if (orderAmount == null){
            orderAmount = BigDecimal.ZERO;
        }
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getActualOrderPrice() {
        if (actualOrderPrice == null){
            actualOrderPrice = BigDecimal.ZERO;
        }
        return actualOrderPrice;
    }

    public void setActualOrderPrice(BigDecimal actualOrderPrice) {
        this.actualOrderPrice = actualOrderPrice;
    }

    public boolean isUseRebate() {
        return isUseRebate;
    }

    public void setUseRebate(boolean useRebate) {
        isUseRebate = useRebate;
    }
}
