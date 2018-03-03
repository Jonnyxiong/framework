package com.jsmsframework.common.enums;

/**
 * Created by dylan on 2017/11/25.
 * 付费类型，0：预付费，1：后付费
 */
public enum PayType {

    预付费(0,"预付费"),
    后付费(1,"后付费");
    private Integer value;
    private String desc;

    PayType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByValue(Integer value) {
        if(value == null){ return null;}
        String result = null;
        for (PayType s : PayType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

    public static PayType getInstanceByValue(Integer value) {
        for (PayType productType : PayType.values()) {
            if(productType.getValue().equals(value)){
                return productType;
            }
        }
        return null;
    }


}
