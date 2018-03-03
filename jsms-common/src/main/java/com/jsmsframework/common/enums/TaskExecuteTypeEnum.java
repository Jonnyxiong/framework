package com.jsmsframework.common.enums;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * 定时任务扫描类型
 * 1：分钟 yyyyMMddHHmm
 * 2：小时 yyyyMMddHH
 * 3：天  yyyyMMdd
 * 4：周  yyyyMMdd
 * 5：月  yyyyMM
 * 6：季度 yyyyMM
 * 7：年   yyyy
 */
public enum TaskExecuteTypeEnum {
    分钟("1","分钟","yyyyMMddHHmm"),
    小时("2","小时","yyyyMMddHH"),
    天("3","天","yyyyMMdd"),
    周("4","周","yyyyMMdd"),
    月("5","月","yyyyMM"),
    季度("6","季度","yyyyMM"),
    年("7","年","yyyy");
    private String value;
    private String desc;
    private String pattern;


    TaskExecuteTypeEnum(String value, String desc, String pattern) {
        this.value = value;
        this.desc = desc;
        this.pattern = pattern;
    }

    public String getValue() {
        return value;
    }


    public String getPattern() {
        return pattern;
    }

    public String getDesc() {
        return desc;
    }

    public static TaskExecuteTypeEnum getInstanceByValue(String value) {
        for (TaskExecuteTypeEnum executeTypeEnum : TaskExecuteTypeEnum.values()) {
            if (executeTypeEnum.getValue().equals(value)) {
                return executeTypeEnum;
            }
        }
        return null;
    }

    public static void main(String[] args) {

        Date date = new Date();
        System.out.println(date.getTime());
        DateTime dateTime = new DateTime(date);
        DateTime dateTime1 = dateTime.plusSeconds(1);
        System.out.println(dateTime.toDate().getTime());

        System.out.println(dateTime1.toDate().getTime());


    }

}
