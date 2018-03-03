package com.jsmsframework.common.enums;

import java.util.Objects;

/**
 * created by Don
 * 代理商状态 2017年11月4日
 */
public enum AgentStatus {
    // 代理商状态，1：注册完成，5：冻结，6：注销
    注册完成("1", "注册完成"),
    冻结("5", "冻结"),
    注销("6", "注销"),
    锁定("7", "锁定");
    private String value;
    private String desc;

    AgentStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByValue(String value) {
        if(value == null){ return null;}
        String result = null;
        for (AreaCodeEnum s : AreaCodeEnum.values()) {
            if (Objects.equals(value,s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
