package com.jsmsframework.common.enums;

public enum PaymentType {

    //业务类型，0：充值，1：扣减，2：佣金转余额，3：购买产品包，4：退款，5：赠送，6：后付费客户消耗，7：回退条数, 8：后付费客户失败返还 9：增加授信，10：降低授信


    充值(0, "充值"),
    扣减(1, "扣减"),
    佣金转余额(2, "佣金转余额"),
    购买产品包(3, "购买产品包"),
    退款(4, "退款"),
    赠送(5, "赠送"),
    后付费客户消耗(6, "后付费客户消耗"),
    回退条数(7, "回退条数"),
    后付费客户失败返还(8, "后付费客户失败返还"),
    增加授信(9, "增加授信"),
    降低授信(10, "降低授信"),
    在线充值(11, "在线充值");

    private Integer value;
    private String desc;

    PaymentType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }

    public static String getDescByValue(Integer value) {
        if(value == null){ return null;}
        String result = null;
        for (PaymentType s : PaymentType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
