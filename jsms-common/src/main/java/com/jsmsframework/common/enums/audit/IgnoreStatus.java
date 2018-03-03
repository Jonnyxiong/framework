package com.jsmsframework.common.enums.audit;

import java.util.Objects;

/**
 * Created by Don on 2018/1/22.
 */
public enum IgnoreStatus {

    正常(0, "正常"), 删除(1, "删除");

    private Integer value;
    private String desc;

    IgnoreStatus(Integer value, String desc) {
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
        for (IgnoreStatus s : IgnoreStatus.values()) {
            if (Objects.equals(value,s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
