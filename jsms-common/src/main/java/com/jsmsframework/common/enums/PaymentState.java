package com.jsmsframework.common.enums;


/**
 * 订单支付状态
 * @author yeshiyuan
 * @create 2018/1/3
 */
public enum PaymentState {

    WEI_ZHI_FU(0,"待支付"),
    SUBMIT(1,"支付已提交"),
    SUCCESS(2,"支付成功"),
    FAIL(3,"支付失败"),
    CANCEL(4,"支付取消"),
    OVER_TIME(5,"超时支付"),
    EXCEPTION(6,"支付异常");


    private Integer value;
    private String name;

    PaymentState(Integer value, String name){
        this.value = value;
        this.name = name;
    }


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(Integer value){
        if (value == null){
            return null;
        }
        String result = null;
        for (PaymentState p : PaymentState.values()) {
            if (p.getValue()==value){
                result = p.getName();
                break;
            }
        }
        return result;
    }
}
