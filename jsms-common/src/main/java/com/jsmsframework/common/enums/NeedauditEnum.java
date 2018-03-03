package com.jsmsframework.common.enums;

/**
 * @Description: 状态报告枚举
 * @Author: tanjiangqiang
 * @Date: 2017/11/29 - 14:32
 */
public enum NeedauditEnum {

    NEEDREPORT_NO(0, "不需要"),
    NEEDREPORT_SIMPLE(1, "需要简单状态报告"),
    NEEDREPORT_TRANSPARENT_RANSMISSION(2, "需要透传状态报告"),
    NEEDREPORT_USER(3, "自己拉取");

    private Integer value;
    private String desc;

    NeedauditEnum(Integer value, String desc) {
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
}
