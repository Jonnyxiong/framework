package com.jsmsframework.common.enums;

public enum ProductStatus {

    待上架(0, "待上架"),
    已上架(1, "已上架"),
    已下架(2, "已下架");

    private Integer value;
    private String desc;

    ProductStatus(Integer value, String desc) {
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
        for (AreaCodeEnum s : AreaCodeEnum.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
