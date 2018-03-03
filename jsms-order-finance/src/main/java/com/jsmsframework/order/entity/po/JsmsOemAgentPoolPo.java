package com.jsmsframework.order.entity.po;

import com.jsmsframework.order.entity.JsmsOemAgentPool;
import com.ucpaas.sms.common.util.DateUtils;

import java.util.Map;

public class JsmsOemAgentPoolPo extends JsmsOemAgentPool {

    // 到期时间 年月日
    private Integer ymd;

    // 回退条数
    private Integer returnQuantity;

    private Map<Long, String> multiRecord;

    // 充值条数
    private Integer updateNum;

    public Integer getYmd() {
        if (ymd == null && getDueTime() != null){
            ymd = Integer.parseInt(DateUtils.formatDate(getDueTime(), "yyyyMMdd"));
        }
        return ymd;
    }

    public void setYmd(Integer ymd) {
        this.ymd = ymd;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Map<Long, String> getMultiRecord() {
        return multiRecord;
    }

    public void setMultiRecord(Map<Long, String> multiRecord) {
        this.multiRecord = multiRecord;
    }

    public Integer getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(Integer updateNum) {
        this.updateNum = updateNum;
    }
}
