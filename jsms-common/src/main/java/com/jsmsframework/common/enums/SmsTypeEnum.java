package com.jsmsframework.common.enums;

/**
 * 短信类型
 */
public enum SmsTypeEnum {

    通知(0, "通知"),

    验证码(4, "验证码"),

    营销(5, "营销"),

    告警(6, "告警"),

    USSD(7, "USSD"),

    行业(10, "行业"),

    会员营销(11, "会员营销"),

    闪信(8, "闪信");

    private Integer value;
    private String desc;

    SmsTypeEnum(Integer value, String desc) {
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
        for (SmsTypeEnum s : SmsTypeEnum.values()) {
            if (s.getValue().equals(value)) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
