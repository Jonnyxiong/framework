package com.jsmsframework.order.enums;

public enum GroupParamsType {

    // 订单类型，1：OEM代理商分发，2：OEM代理商回退

    产品类型("product_type"), 运营商("operator_code"), 区域("area_code"), 单价("unit_price"), 到期时间("end_time");

    private String value;

    GroupParamsType(String value) {
        this.value = value;
    }


    public String getValue(){
        return value;
    }

}
