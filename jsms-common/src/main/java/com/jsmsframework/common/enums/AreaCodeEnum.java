package com.jsmsframework.common.enums;

/**
 * Created by dylan on 2017/8/16.
 */
public enum AreaCodeEnum {
    /**
     * 0：全国，1：国际
     */
    全国(0, "全国"),
    国际(1, "国际");
    private Integer value;
    private String desc;

    AreaCodeEnum(Integer value, String desc) {
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
