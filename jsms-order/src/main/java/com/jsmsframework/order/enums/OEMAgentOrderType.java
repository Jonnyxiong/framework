package com.jsmsframework.order.enums;



public enum OEMAgentOrderType {

    // 订单类型，0：OEM代理商购买，1：OEM代理商分发，2：OEM代理商回退

    OEM代理商购买(0, "OEM代理商购买"),OEM代理商分发(1, "OEM代理商分发"), OEM代理商回退(2, "OEM代理商回退");

    private Integer value;
    private String desc;

    OEMAgentOrderType(Integer value, String desc) {
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
        if(value == null) {
            return null;
        }
        String result = null;
        for (OEMAgentOrderType oemAgentOrderType : OEMAgentOrderType.values()) {
            if(oemAgentOrderType.getValue().equals(value)){
                result = oemAgentOrderType.getDesc();
                break;
            }
        }

        return result;
    }



}
