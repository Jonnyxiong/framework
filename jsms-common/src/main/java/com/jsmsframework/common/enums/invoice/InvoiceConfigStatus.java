package com.jsmsframework.common.enums.invoice;

import java.util.Objects;

public enum InvoiceConfigStatus {

    正常(0, "正常"),
    删除(1, "删除");
    private Integer value;
    private String desc;

    InvoiceConfigStatus(Integer value, String desc) {
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
        for (InvoiceConfigStatus s : InvoiceConfigStatus.values()) {
            if (Objects.equals(value, s.getValue()) ) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
