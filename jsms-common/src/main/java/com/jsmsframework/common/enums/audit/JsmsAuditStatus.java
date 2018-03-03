package com.jsmsframework.common.enums.audit;
 

public enum JsmsAuditStatus {


    待审核(0, "待审核"), 审核通过(1, "审核通过"), 审核不通过(2, "审核不通过"), 转审(3, "转审");

    private Integer value;
    private String desc;

    JsmsAuditStatus(Integer value, String desc) {
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
        for (JsmsAuditStatus s : JsmsAuditStatus.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
