package com.jsmsframework.common.enums;

/**
 * Created by huangwenjie on 2017/10/16.
 */
public enum ClientFailReturnRefundStatus {
    /**
     * 退费状态，
     0：未退费，
     1：已退费
     */
    未退费(0, "未退费"),
    已退费(1, "已退费");
    private Integer value;
    private String desc;

    ClientFailReturnRefundStatus(Integer value, String desc) {
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
        for (ClientFailReturnRefundStatus s : ClientFailReturnRefundStatus.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }


}
