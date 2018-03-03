package com.jsmsframework.common.enums;

import java.util.Objects;

public enum ClientAlarmType {

    // 告警类型，1：验证码告警，2：通知告警，3：营销告警，4：国际告警 适用于OEM客户

    验证码(1, "验证码告警"),
    通知(2, "通知告警"),
    营销(3, "营销告警"),
    国际(4, "国际告警");

    private Integer value;
    private String desc;

    ClientAlarmType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }

    public static String getDescByValue(Integer value) {
        if(value == null){ return null;}
        String result = null;
        for (ClientAlarmType s : ClientAlarmType.values()) {
            if (Objects.equals(value, s.getValue())){
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

}
