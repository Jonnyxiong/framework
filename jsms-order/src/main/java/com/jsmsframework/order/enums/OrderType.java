package com.jsmsframework.order.enums;

public enum OrderType {

    // 订单类型，1：OEM代理商分发，2：OEM代理商回退

    充值(1, "充值"), 回退(2, "回退"), 条数返还(3, "条数返还");

    private Integer value;
    private String desc;

    OrderType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }

    public static String getDescByValue(Integer value){
        String result = null;
        if(value == null){
            return result;
        }
        for (OrderType orderType : OrderType.values()) {
            if(orderType.getValue().equals(value)){
                result = orderType.getDesc();
                break;
            }
        }
        return result;
    }
}
