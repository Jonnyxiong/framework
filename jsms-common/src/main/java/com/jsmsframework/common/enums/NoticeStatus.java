package com.jsmsframework.common.enums;

public enum NoticeStatus {

    // 公告类型，0：待发布,1:已发布,2:已取消

    待发布(0, "待发布"), 已发布(1, "已发布"), 已取消(2, "已取消");

    private Integer value;
    private String desc;

    NoticeStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public Integer getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }

    public static String getDescByValue(Integer value){
        String result = null;
        if(value == null){
            return result;
        }
        for (NoticeStatus orderType : NoticeStatus.values()) {
            if(orderType.getValue().equals(value)){
                result = orderType.getDesc();
                break;
            }
        }
        return result;
    }
}
