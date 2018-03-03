package com.jsmsframework.common.enums;

/**
 * 销售授信---业务类型
 * create by Don 2017年10月26日
 */
public enum BusinessType {

    //'业务类型，0：财务给销售授信，1：财务降低销售授信，2：销售给客户授信，3：销售降低客户授信，4：客户回款'

    财务给销售授信(0, "财务给销售授信"), 财务降低销售授信(1, "财务降低销售授信"),销售给客户授信(2, "销售给客户授信"),销售降低客户授信(3, "销售降低客户授信"),客户回款(4, "客户回款");

    private Integer value;
    private String desc;

    BusinessType(Integer value, String desc) {
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
        for (BusinessType s : BusinessType.values()) {
            if (value.equals(s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
