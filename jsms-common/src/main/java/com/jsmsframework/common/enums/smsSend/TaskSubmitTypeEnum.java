package com.jsmsframework.common.enums.smsSend;

/**
 * Created by xiongfenglin on 2018/1/8.
 *
 * @author: xiongfenglin
 */

import com.jsmsframework.common.enums.TaskStatusEnum;

/**
 * 提交类型：
 0：子账户
 1：代理商
 */
public enum TaskSubmitTypeEnum {
    子账户(0,"子账户"),
    代理商(1,"代理商");
    private Integer value;
    private String desc;


    TaskSubmitTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
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
