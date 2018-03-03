package com.jsmsframework.common.enums.balckAndWhiteTemplate;

import java.util.Objects;

/**
 * Created by Don on 2017/12/6.
 */
public enum TemplateLevel {

    全局级别(0, "通用模板"),用户级别(1, "用户模板");

    private Integer value;
    private String desc;

    TemplateLevel(Integer value, String desc) {
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
        if (value == null) {
            return null;
        }
        String result = null;
        for (TemplateLevel s : TemplateLevel.values()) {
            if (Objects.equals(value, s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

    public static TemplateLevel getInstanceByValue(Integer value) {
        if(value == null){ return null;}
        for (TemplateLevel s : TemplateLevel.values()) {
            if (Objects.equals(value,s.getValue())) {
                return s;
            }
        }
        return null;
    }
}
