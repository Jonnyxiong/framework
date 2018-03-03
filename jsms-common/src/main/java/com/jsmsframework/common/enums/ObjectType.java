package com.jsmsframework.common.enums;

/**
 * 销售授信流水--授信对象类型
 * created by Don 2017年10月26日
 */
public enum ObjectType {

    //'授信对象类型，0：销售，1：代理商

    销售(0, "销售"), 代理商(1, "代理商");

    private Integer value;
    private String desc;

    ObjectType(Integer value, String desc) {
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
        for (ObjectType s : ObjectType.values()) {
            if (value.equals(s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
