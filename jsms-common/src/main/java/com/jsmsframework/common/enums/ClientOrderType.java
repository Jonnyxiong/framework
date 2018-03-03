package com.jsmsframework.common.enums;

/**
 * Created by dylan on 2017/8/16.
 */
public enum ClientOrderType {
    /**
     * 品牌订单类型，0：客户购买，1：代理商代买, 2:运营代买
     */
    客户购买(0,"行业"),
    代理商代买(1,"营销"),
    运营代买(2,"国际");
    private Integer value;
    private String desc;

    ClientOrderType(Integer value, String desc) {
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
        for (ClientOrderType s : ClientOrderType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }


}
