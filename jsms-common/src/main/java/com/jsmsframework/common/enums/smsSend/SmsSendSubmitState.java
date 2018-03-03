package com.jsmsframework.common.enums.smsSend;

import java.util.Objects;

/**
 * Created by xiongfenglin on 2018/1/19.
 *
 * @author: xiongfenglin
 */
public enum SmsSendSubmitState {
    //0：未提交(初始状态)，1：提交中，2：提交完成，3：提交失败(文件解析失败、账号状态异常、账户可用额度不足)

    未提交(0, "未提交"),
    提交中(1, "提交中"),
    提交完成(2, "提交完成"),
    提交失败(3, "提交失败");

    private Integer value;
    private String desc;

    SmsSendSubmitState(Integer value, String desc) {
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
