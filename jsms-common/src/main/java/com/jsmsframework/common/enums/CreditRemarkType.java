package com.jsmsframework.common.enums;

/**
 * Don
 * 授信业务授信特殊备注
 */
public enum CreditRemarkType {

    //各个授信资金变化备注

    超频("超频", "后付费子账户超额"), 初始("初始", "授信数据初始化"),返还("返还","后付费客户失败返还-回款"),变更("变更","客户归属销售变更");

    private String value;
    private String desc;

    CreditRemarkType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public String getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }

    public static String getDescByValue(String value) {
        if(value == null){ return null;}
        String result = null;
        for (CreditRemarkType s : CreditRemarkType.values()) {
            if (value.equals(s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
