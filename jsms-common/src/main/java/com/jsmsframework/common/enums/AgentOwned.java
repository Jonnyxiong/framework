package com.jsmsframework.common.enums;

/**
 * created by xiaoqingwen on 2017/12/7 14:12
 * 是否代理商自有用户帐号，0：否，1：是
 */
public enum  AgentOwned {

    代理商子客户使用(0,"子客户使用"),
    代理商自己使用(1,"自己使用");
    private Integer value;
    private String desc;
    AgentOwned(Integer value, String desc) {
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
