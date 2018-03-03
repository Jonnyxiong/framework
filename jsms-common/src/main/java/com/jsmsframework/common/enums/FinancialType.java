package com.jsmsframework.common.enums;

public enum FinancialType {

    //财务类型，0：入账，1：出账

    入账("0", "入账"), 出账("1", "出账");

    private String value;
    private String desc;

    FinancialType(String value, String desc) {
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
        for (FinancialType s : FinancialType.values()) {
            if (value.equals(s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
