package com.jsmsframework.common.enums;

import java.util.Objects;

public enum AgentType {

    // 代理商类型,1:销售代理商,2:品牌代理商,3:资源合作商,4:代理商和资源合作,5:OEM代理商 配置tb_sms_params.param_type=agent_type

    销售代理商(1, "销售代理商"),
    品牌代理商(2, "品牌代理商"),
    资源合作商(3, "资源合作商"),
    代理商和资源合作(4, "代理商和资源合作"),
    OEM代理商(5, "OEM代理商");

    private Integer value;
    private String desc;

    AgentType(Integer value, String desc) {
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
        for (AgentType s : AgentType.values()) {
            if (Objects.equals(value, s.getValue())){
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

}
