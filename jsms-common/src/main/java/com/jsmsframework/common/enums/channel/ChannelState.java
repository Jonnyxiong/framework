package com.jsmsframework.common.enums.channel;

import java.util.Objects;

/**
 * Created by Don on 2018/1/31.
 */
public enum ChannelState {

    关闭(0,"关闭"),
    开启(1,"开启"),
    暂停(2,"暂停"),
    下架(3,"下架");

    private Integer value;
    private String desc;

    ChannelState(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByValue(String value){
        if(value==null){return null;}
        String result=null;
        for (ChannelState channelState : ChannelState.values()) {
            if(Objects.equals(value,channelState.getValue())){
                result=channelState.getDesc();
                break;

            }
        }
        return  result;

    }
}
