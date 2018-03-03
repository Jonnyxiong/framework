package com.jsmsframework.common.enums.record;

import java.util.Objects;

/**
 * Created by Don on 2018/1/11.
 */
public enum LongsmsEnum {
    /*是否长短信
      * 0：否，1：是
     */
    普通短信(0, "否"),
    长短信(1, "是");
    private Integer value;
    private String desc;

    LongsmsEnum(Integer value, String desc) {
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
        for (LongsmsEnum s : LongsmsEnum.values()) {
            if (Objects.equals(value, s.getValue()) ) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

}
