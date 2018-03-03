package com.jsmsframework.audit.enums;

/**
 * Created by Don on 2017/8/28.
 */
@Deprecated
public enum AuditType {
    //'0：待审核，1：审核通过，2：审核不通过, 3：转审'
    待审核(0,"待审核"),审核通过(1,"审核通过"),审核不通过(2,"审核不通过"),转审(3,"转审");

    private Integer value;
    private String desc;

    AuditType(Integer value, String desc) {
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
