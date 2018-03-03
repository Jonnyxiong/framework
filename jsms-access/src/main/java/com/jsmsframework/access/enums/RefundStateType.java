package com.jsmsframework.access.enums;

import com.jsmsframework.common.enums.AreaCodeEnum;

/**
 * Created by dylan on 2017/10/16.
 */
public enum RefundStateType {

    未退费(0,"未退费"),
    已退费(1,"已退费");
    private Integer value;
    private String desc;

    RefundStateType(Integer value, String desc) {
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
        for (RefundStateType s : RefundStateType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }




}
