package com.jsmsframework.common.enums.balckAndWhiteTemplate;

/**
 * 短信类型
 */
public enum AutoTemplateTypeEnum {


    会员营销(11, "会员营销"),

    行业(10, "行业");


    private Integer value;
    private String desc;

    AutoTemplateTypeEnum(Integer value, String desc) {
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
        for (AutoTemplateTypeEnum s : AutoTemplateTypeEnum.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
