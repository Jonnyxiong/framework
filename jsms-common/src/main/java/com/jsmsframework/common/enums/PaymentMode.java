package com.jsmsframework.common.enums;

/**
 * 支付方式
 * @author yeshiyuan
 * @create 2018/1/2
 */
public enum PaymentMode {

    ZHI_FU_BAO(0,"支付宝"),
    WEI_XIN(1,"微信支付");

    private int key;

    private String value;


    PaymentMode(int key,String value){
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getDesc(Integer key){
        if (key == null){
            return null;
        }
        String result = null;
        for (PaymentMode p : PaymentMode.values()) {
            if (p.getKey()==key){
                result = p.getValue();
                break;
            }
        }
        return result;
    }
}
