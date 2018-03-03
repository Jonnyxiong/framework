package com.jsmsframework.common.enums.smsSend;

import java.util.Objects;

/**
 * Created by Don on 2018/1/4.
 */
public enum SmsSendStatus {
    //0：处理中， 1：等待中，2：进行中，3：已完成，4：已取消，5：已删除

    处理中(0, "处理中"),
    等待中(1, "等待中"),
    进行中(2, "进行中"),
    已完成(3, "已完成"),
    已取消(4, "已取消"),
    已删除(5, "已删除");

    private Integer value;
    private String desc;

    SmsSendStatus(Integer value, String desc) {
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
        for (SmsSendStatus s : SmsSendStatus.values()) {
            if (Objects.equals(value,s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
