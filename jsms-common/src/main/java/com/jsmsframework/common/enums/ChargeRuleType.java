package com.jsmsframework.common.enums;

/**
 * Created by dylan on 2017/8/16.
 */
public enum ChargeRuleType {
    /**
     * 计费规则，0：提交量，1：成功量，2：明确成功量
     */
    //计费规则：提交量计费，用户侧：1+3+4+6
    提交量(0, "提交量"),
    //计费规则：成功量计费，用户侧：1+3
    成功量(1, "成功量"),
    //计费规则：明确成功量计费，用户侧：3
    明确成功量(2, "明确成功量");
    private Integer value;
    private String desc;

    ChargeRuleType(Integer value, String desc) {
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
        for (ChargeRuleType s : ChargeRuleType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }


}
