package com.jsmsframework.finance.dto;

import com.jsmsframework.finance.entity.JsmsAgentCreditRecord;

public class JsmsAgentCreditRecordDTO extends JsmsAgentCreditRecord {


    //开始时间
    private String beginTime;
    //结束时间
    private String endTIme;


    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTIme() {
        return endTIme;
    }

    public void setEndTIme(String endTIme) {
        this.endTIme = endTIme;
    }
}
