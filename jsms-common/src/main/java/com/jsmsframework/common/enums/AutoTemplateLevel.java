package com.jsmsframework.common.enums;

/**
 * created by xiaoqingwen on 2017/12/22 10:02
 * 模版级别
 */
@Deprecated
public enum AutoTemplateLevel {

    全局级别(0, "通用模版"), 用户级别(1, "智能模版");

    private Integer value;
    private String desc;

    AutoTemplateLevel(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
