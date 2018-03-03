package com.jsmsframework.common.enums;

/**
 * Created by dylan on 2017/8/16.
 */
public enum OperatorType {
    /**
     * 0：全网，1：移动，2：联通，3：电信，4：国际
     */
    全网(0, "全网"),
    移动(1, "移动"),
    联通(2, "联通"),
    电信(3, "电信"),
    国际(4, "国际");
    private Integer value;
    private String desc;

    OperatorType(Integer value, String desc) {
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
        for (OperatorType s : OperatorType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }


}
