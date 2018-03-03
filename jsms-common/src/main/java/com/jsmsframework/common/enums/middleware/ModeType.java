package com.jsmsframework.common.enums.middleware;

import java.util.Objects;

/**
 * Created by Don on 2018/1/2.
 */
public enum ModeType {


    生产者(0, "生产者"), 消费者(1, "消费者");

    private Integer value;
    private String desc;

    ModeType(Integer value, String desc) {
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
        for (ModeType s : ModeType.values()) {
            if (Objects.equals(value,s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
