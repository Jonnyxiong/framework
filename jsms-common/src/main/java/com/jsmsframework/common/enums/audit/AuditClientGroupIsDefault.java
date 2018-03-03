package com.jsmsframework.common.enums.audit;

public enum AuditClientGroupIsDefault {

    非默认组(0, "非默认组"), 默认组(1, "默认组");

    private Integer value;
    private String desc;

    AuditClientGroupIsDefault(Integer value, String desc) {
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
        for (AuditClientGroupIsDefault s : AuditClientGroupIsDefault.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

    public static AuditClientGroupIsDefault getByValue(Integer value) {
        if(value == null){ return 非默认组;}
        AuditClientGroupIsDefault result = null;
        for (AuditClientGroupIsDefault s : AuditClientGroupIsDefault.values()) {
            if (value == s.getValue()) {
                return s;
            }
        }
        return result;
    }
}
