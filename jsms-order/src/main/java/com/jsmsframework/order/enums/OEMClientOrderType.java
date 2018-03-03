package com.jsmsframework.order.enums;



public enum OEMClientOrderType {

    // 订单类型，1：OEM代理商分发，2：OEM代理商回退，3：短信失败返还, 0：OEM代理商购买

    OEM代理商分发(1, "OEM代理商分发"), OEM代理商回退(2, "OEM代理商回退"), 短信失败返还(3, "短信失败返还"),OEM代理商购买(0, "OEM代理商购买");

    private Integer value;
    private String desc;

    OEMClientOrderType(Integer value, String desc) {
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
        for (OEMClientOrderType oemClientOrderType : OEMClientOrderType.values()) {
            if(oemClientOrderType.getValue().equals(value)){
                result = oemClientOrderType.getDesc();
                break;
            }
        }

        return result;
    }



}
