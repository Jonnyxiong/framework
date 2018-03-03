package com.jsmsframework.common.enums.balckAndWhiteTemplate;

import java.util.Objects;

/**
 * Created by Don on 2017/12/6.
 */
public enum BalckTemplateState {

    失效(0, "失效"),启用(1, "启用"),删除(2, "删除");


    private Integer value;
    private String desc;

    BalckTemplateState(Integer value, String desc) {
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
        for (BalckTemplateState s : BalckTemplateState.values()) {
            if (Objects.equals(value,s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

}
