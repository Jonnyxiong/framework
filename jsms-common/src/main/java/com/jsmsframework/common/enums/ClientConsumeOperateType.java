package com.jsmsframework.common.enums;

public enum ClientConsumeOperateType {

    //品牌客户消耗记录  t_sms_client_consumer_list 操作类型, 0:短信失败返还

    短信失败返还(0, "短信失败返还");

    private Integer value;
    private String desc;

    ClientConsumeOperateType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }

    public static String getDescByValue(String value) {
        if(value == null){ return null;}
        String result = null;
        for (ClientConsumeOperateType s : ClientConsumeOperateType.values()) {
            if (value.equals(s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
