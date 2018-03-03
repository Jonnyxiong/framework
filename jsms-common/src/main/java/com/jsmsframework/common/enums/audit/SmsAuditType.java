package com.jsmsframework.common.enums.audit;

import java.util.Objects;


/**短信审核状态
 * Created by Don on 2017/11/28.
 */
public enum SmsAuditType {
    //'0：待审核，1：审核通过，2：审核不通过, 3：转审'
    待审核(0,"待审核"),审核通过(1,"审核通过"),审核不通过(2,"审核不通过"),转审(3,"转审");

    private Integer value;
    private String desc;

    SmsAuditType(Integer value, String desc) {
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
        for (SmsAuditType s : SmsAuditType.values()) {
            if (Objects.equals(value,s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }

}
