package com.jsmsframework.common.enums;

/**
 * Created by dylan on 2017/8/16.
 */
public enum ProductType {
    /**
     * 0：行业，
     * 1：营销，
     * 2：国际，
     * 3：验证码，
     * 4：通知，
     * 7：USSD，
     * 8：闪信，
     * 9：挂机短信
     * 其中0、1、3、4为普通短信，2为国际短信
     */
    行业(0,"行业"),
    营销(1,"营销"),
    国际(2,"国际"),
    验证码(3,"验证码"),
    通知(4,"通知"),
    USSD(7,"USSD"),
    闪信(8,"闪信"),
    挂机短信(9,"挂机短信");
    private Integer value;
    private String desc;

    ProductType(Integer value, String desc) {
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
        for (ProductType s : ProductType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

    public static ProductType getInstanceByValue(Integer value) {
        for (ProductType productType : ProductType.values()) {
            if(productType.getValue().equals(value)){
                return productType;
            }
        }
        return null;
    }


}
