package com.jsmsframework.order.dto;

import java.math.BigDecimal;

/**
 * Created by xiongfenglin on 2017/10/18.
 */
public class ClientConsumerListTotal {
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
