package com.jsmsframework.common.enums;

public enum NoticeTop {

    // 公告是否置顶，0:否,1:是

    否(0, "否"), 是(1, "是");

    private Integer value;
    private String desc;

    NoticeTop(Integer value, String desc) {
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
        for (NoticeTop orderType : NoticeTop.values()) {
            if(orderType.getValue().equals(value)){
                result = orderType.getDesc();
                break;
            }
        }
        return result;
    }
}
