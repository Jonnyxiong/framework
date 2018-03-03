package com.jsmsframework.order.dto;

import com.jsmsframework.order.entity.JsmsOemClientOrder;

/**
 * 我的消费DTO
 */
public class OemClientOrderDTO extends  JsmsOemClientOrder {
    Integer rownum;
    String productTypeName;
    String orderTypeName;
    String number;
    String time;

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
