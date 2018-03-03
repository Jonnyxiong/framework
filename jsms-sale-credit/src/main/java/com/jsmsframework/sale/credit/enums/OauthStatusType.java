package com.jsmsframework.sale.credit.enums;

/**
 * Don
 * 认证状态，临时增加
 */

public enum OauthStatusType {

    //校验用
    待认证(2, "待认证"), 证件已认证(3, "证件已认证"),认证不通过(4,"认证不通过");

    private Integer value;
    private String desc;

    OauthStatusType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }

    public static String getDescByValue(Integer value) {
        if(value == null){ return null;}
        String result = null;
        for (OauthStatusType s : OauthStatusType.values()) {
            if (value.equals(s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
