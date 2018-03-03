package com.jsmsframework.channel.enums;

/**
 * Created by Don on 2017/10/11.
 */
public enum ConfigExValueEnum {
    /**
     * 0：全国，1：国际
     */
    验证码(0, "短信类型-验证码"),
    通知(1, "短信类型-通知"),
    营销(2, "短信类型-营销"),
    告警(3, "短信类型-告警"),
    移动(4, "发送号码所属运营商-移动"),
    联通(5, "发送号码所属运营商-联通"),
    电信(6, "发送号码所属运营商-电信");
    private Integer value;
    private String desc;

    ConfigExValueEnum(Integer value, String desc) {
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
        for (ConfigExValueEnum s : ConfigExValueEnum.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }


}
