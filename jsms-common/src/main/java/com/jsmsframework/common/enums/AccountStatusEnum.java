package com.jsmsframework.common.enums;

/**
 * 子账号状态 1：注册完成，5：冻结，6：注销，7：锁定
 */
public enum AccountStatusEnum {

    注册完成(1, "注册完成"),

    冻结(5, "冻结"),

    注销(6, "注销"),

    锁定(7, "锁定");

    private Integer value;
    private String desc;

    AccountStatusEnum(Integer value, String desc) {
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
        for (AccountStatusEnum s : AccountStatusEnum.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
