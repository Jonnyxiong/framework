package com.jsmsframework.order.dto;

import java.math.BigDecimal;

/**
 * 我的消费的合计
 */
public class OemClientOrderListTotal {

    Integer orderNumberTotal;

    BigDecimal orderPriceTotal;


    public Integer getOrderNumberTotal() {
        return orderNumberTotal;
    }

    public void setOrderNumberTotal(Integer orderNumberTotal) {
        this.orderNumberTotal = orderNumberTotal;
    }


    public BigDecimal getOrderPriceTotal() {
        return orderPriceTotal;
    }

    public void setOrderPriceTotal(BigDecimal orderPriceTotal) {
        this.orderPriceTotal = orderPriceTotal;
    }
}
