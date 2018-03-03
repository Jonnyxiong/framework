package com.jsmsframework.common.enums;

/**
 * Created by xiaoqingwen on 2018/01/12.
 */
public enum OwnerType {
    /**
     * 通道所属类型，1：自有，2：直连，3：第三方
     */
    自有(1, "自有"),
    直连(2, "直连"),
    第三方(3, "第三方");
    private Integer value;
    private String desc;

    OwnerType(Integer value, String desc) {
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
        for (OwnerType s : OwnerType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }


}
