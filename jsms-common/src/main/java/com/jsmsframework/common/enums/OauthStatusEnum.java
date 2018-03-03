package com.jsmsframework.common.enums;

/**
 * 代理商认证状态
 *
 * @outhor tanjiangqiang
 * @create 2017-11-21 20:45
 */
public enum OauthStatusEnum {
    待认证(2, "待认证"),
    证件已认证(3, "证件已认证"),
    认证不通过(4, "认证不通过");

    private Integer value;
    private String desc;

    OauthStatusEnum(Integer value, String desc) {
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