package com.jsmsframework.common.enums.channel;

import java.util.Objects;

/**
 * Created by Don on 2018/1/13.
 */
public enum ChannelProperty {

    低消条数("low_consume_number", "低消条数"),

    投诉系数("complaint_coefficient", "投诉系数");

    private String value;
    private String desc;

    ChannelProperty(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByValue(String value) {
        if(value == null){ return null;}
        String result = null;
        for (ChannelProperty s : ChannelProperty.values()) {
            if (Objects.equals(value,s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
