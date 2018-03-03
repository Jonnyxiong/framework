package com.jsmsframework.common.enums;

/**
 * 计费规则
 */
public enum ChargeRule {

    // 用户侧：1+3+4+6
    提交量计费(0, "提交量计费"),

    // 用户侧：1+3
    成功量计费(1, "成功量计费"),

    // 用户侧：3
    明确成功量计费(2, "明确成功量计费");


    private Integer value;
    private String desc;

    ChargeRule(Integer value, String desc) {
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
        for (ChargeRule s : ChargeRule.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
