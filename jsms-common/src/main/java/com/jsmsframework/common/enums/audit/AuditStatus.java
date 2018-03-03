package com.jsmsframework.common.enums.audit;
 

public enum AuditStatus {


    待审核(0, "待审核"), 审核通过(1, "审核通过"), 审核不通过(2, "审核不通过"), 审核通过转人工过期(3, "审核通过转人工过期"), 审核不通过转人工过期(4, "审核不通过转人工过期");

    private Integer value;
    private String desc;

    AuditStatus(Integer value, String desc) {
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
        for (AuditStatus s : AuditStatus.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
