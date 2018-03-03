package com.jsmsframework.common.enums;

/**
 * Created by dylan on 2017/11/25.
 * 客户接入使用协议类型，2为SMPP协议，3为CMPP协议，4为SGIP协议，5为SMGP协议，6为HTTPS协议,
 * SMPP协议、CMPP协议、SGIP协议、SMGP协议为标准协议
 */
public enum SmsFrom {
    SMPP(2, "SMPP协议"),
    CMPP(3, "CMPP协议"),
    SGIP(4, "SGIP协议"),
    SMGP(5, "SMGP协议"),
    HTTPS(6, "HTTPS协议");
    private Integer value;
    private String desc;


    SmsFrom(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SmsFrom getInstanceByValue(Integer value) {
        for (SmsFrom smsFromType : SmsFrom.values()) {
            if (smsFromType.getValue().equals(value)) {
                return smsFromType;
            }
        }
        return null;
    }


}
