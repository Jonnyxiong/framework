package com.jsmsframework.common.enums.complaint;

import java.util.Objects;

/**
 * Created by Don on 2018/1/11.
 */
public enum ComplaintStatus {


    /*投诉明细信息状态
      * 0：正常，1：删除
     */
    正常(0, "正常"),
    删除(1, "删除");
    private Integer value;
    private String desc;

    ComplaintStatus(Integer value, String desc) {
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
        for (ComplaintStatus s : ComplaintStatus.values()) {
            if (Objects.equals(value, s.getValue()) ) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
