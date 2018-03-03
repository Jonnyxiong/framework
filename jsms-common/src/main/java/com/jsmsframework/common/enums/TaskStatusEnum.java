package com.jsmsframework.common.enums;

/**
 * 定时任务执行状态
 * 0：关闭
 * 1：启用
 * 2：正在执行
 * 3：删除
 */
public enum TaskStatusEnum {
    关闭("0","关闭"),
    启用("1","启用"),
    正在执行("2","正在执行"),
    删除("3","删除");
    private String value;
    private String desc;


    TaskStatusEnum(String value,String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static TaskStatusEnum getInstanceByValue(String value) {
        for (TaskStatusEnum smsFromType : TaskStatusEnum.values()) {
            if (smsFromType.getValue().equals(value)) {
                return smsFromType;
            }
        }
        return null;
    }


}
