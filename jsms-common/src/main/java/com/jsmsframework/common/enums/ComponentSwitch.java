package com.jsmsframework.common.enums;

/**
 * 状态为关闭时，组件不从MQ中消费数据
 */
public enum  ComponentSwitch {
    close(0, "关闭"), open(1, "开启");

    private Integer value;
    private String desc;

    ComponentSwitch(Integer value, String desc) {
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
        for (AutoTemplateStatus s : AutoTemplateStatus.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
