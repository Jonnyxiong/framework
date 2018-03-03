package com.jsmsframework.common.enums;

/**
  * @Description: 是否需要上行 0：不需要，1：需要，3：用户拉取上行
  * @Author: tanjiangqiang
  * @Date: 2017/11/29 - 14:36
  *
  */
public enum NeedmoEnum {
    NEEDMO_NO(0, "不需要"),
    NEEDMO_WANT(1, "需要"),//SMSP主动推送
    NEEDMO_USER(3, "自己拉取");

    private Integer value;
    private String desc;

    NeedmoEnum(Integer value, String desc) {
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
