package com.jsmsframework.common.enums.middleware;


/**
 * 消息类型，00：DB消息，01：移动行业，02：移动营销，03：联通行业，
 * 04：联通营销，05：电信行业，06：电信营销，07：行业，08：营销，
 * 11：异常移动行业，12：异常移动营销，13：异常联通行业，14：异常联通营销，
 * 15：异常电信行业，16：异常电信营销，20：通道消息，21：上行消息，22：状态报告缓存',23：审核内容请求，24：内容审核结果
 */
public enum  MessageType {

    DB消息("00","DB消息"),移动行业("01","移动行业"),

    移动营销("02","移动营销"),联通行业("03","联通行业"),

    联通营销("04","联通营销"),电信行业("05","电信行业"),

    电信营销("06","电信营销"),行业("07","行业"),

    营销("08","营销"),异常移动行业("11","异常移动行业"),

    异常移动营销("12","异常移动营销"),异常联通行业("13","异常联通行业"),

    异常联通营销("14","异常联通营销"),异常电信营销("16","异常电信行业"),

    异常电信行业("15","异常电信营销"),通道消息("20","通道消息"),上行消息("21","上行消息"),状态报告缓存("22","状态报告缓存"),审核内容请求("23","审核内容请求"),内容审核结果("24","内容审核结果"),
    smsp_c2s_or_smsp_http监控消息("30","smsp_c2s/smsp_http监控消息"),
    smsp_access监控消息("31","smsp_access监控消息"),
    smsp_send_or_smsp_report监控消息("32","smsp_send/smsp_report监控消息"),
    smsp_reback监控消息("33","smsp_reback监控消息"),
    smsp_charge监控消息("34","smsp_charge监控消息"),
    smsp_audit监控消息("35","smsp_audit监控消息"),
    中间件监控消息("36","中间件监控消息");


    private String value;
    private String desc;

    MessageType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByValue(String value) {
        if (value == null) {
            return null;
        }
        String result = null;
        for (MessageType s : MessageType.values()) {
            if (value.equals(s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
