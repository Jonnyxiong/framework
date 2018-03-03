package com.jsmsframework.common.enums;

/**
  * @Description: 状态枚举
  * @Author: tanjiangqiang
  * @Date: 2017/11/29 - 14:31
  *
  */
public enum StatusEnum {
    STATUS_DISABLE(0, "禁用"), STATUS_ENABLE(1, "启用");

    private Integer value;
    private String desc;

    StatusEnum(Integer value, String desc) {
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
