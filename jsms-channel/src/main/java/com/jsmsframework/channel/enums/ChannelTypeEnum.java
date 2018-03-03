package com.jsmsframework.channel.enums;


/**
 * 通道类型
 * @author yeshiyuan
 * @create 2018/1/19
 */
public enum ChannelTypeEnum {

    自签平台用户端口(0,"自签平台用户端口"),
    固签有自扩展(2,"固签有自扩展"),
    自签通道用户端口(3,"自签通道用户端口");

    private Integer value;
    private String desc;

    ChannelTypeEnum(Integer value,String desc){
        this.value = value;
        this.desc = desc;
    }

    public static String getDescByValue(Integer value) {
        if(value == null){ return null;}
        String result = null;
        for (ChannelTypeEnum s : ChannelTypeEnum.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
