package com.jsmsframework.common.enums.invoice;

import java.util.Objects;

public enum InvoiceBodyEnum {

    个人(1, "个人"),
    企业(2, "企业");
    private Integer value;
    private String desc;

    InvoiceBodyEnum(Integer value, String desc) {
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
        for (InvoiceBodyEnum s : InvoiceBodyEnum.values()) {
            if (Objects.equals(value, s.getValue()) ) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
