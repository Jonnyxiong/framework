package com.jsmsframework.common.enums.middleware;


public enum MiddlewareType {

    redis(0, "redis"), MQ_c2s_io(1, "MQ_c2s_io"), MQ_send_io(2, "MQ_send_io"), MQ_c2s_db(3, "MQ_c2s_db"), MQ_send_db(4, "MQ_send_db"), kafka(5, "kafka"), redis_over_rate(6, "redis_over_rate"),MQ_monitor(7,"MQ_monitor");

    private Integer value;
    private String desc;

    MiddlewareType(Integer value, String desc) {
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
        for (MiddlewareType s : MiddlewareType.values()) {
            if (value == s.getValue()) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
