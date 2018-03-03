package com.jsmsframework.common.enums.smsSend;

import java.util.Objects;

/**
 * Created by xiongfenglin on 2018/1/19.
 *
 * @author: xiongfenglin
 */
public enum SmsSendFileType {
    //0：号码池(页面输入)，1：xlsx或xls格式导入，2：txt格式导入,3：csv格式导入

    号码池(0, "号码池"),
    xlsx或xls格式导入(1, "xlsx或xls格式导入"),
    txt格式导入(2, "txt格式导入"),
    csv格式导入(3, "csv格式导入");

    private Integer value;
    private String desc;

    SmsSendFileType(Integer value, String desc) {
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
        for (SmsSendStatus s : SmsSendStatus.values()) {
            if (Objects.equals(value,s.getValue())) {
                result = s.getDesc();
                break;
            }
        }
        return result;
    }
}
