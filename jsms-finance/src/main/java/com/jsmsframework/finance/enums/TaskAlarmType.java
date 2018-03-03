package com.jsmsframework.finance.enums;

@Deprecated
public enum TaskAlarmType {


    NUM_ALARM(1, "短信余额提醒"), AMOUNT_ALARM(2, "可用额度提醒");

    private Integer value;
    private String desc;

    TaskAlarmType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }

}
