package com.jsmsframework.sms.send.dto;

import com.jsmsframework.common.enums.SmsTypeEnum;
import com.jsmsframework.common.enums.smsSend.SmsSendStatus;
import com.jsmsframework.sms.send.entity.JsmsTimerSendTask;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Created by Don on 2018/1/4.
 */
public class TimerSendTaskDTO extends JsmsTimerSendTask{

   private String  smstypeStr;

   private String  submitTimeStr;

   private String  setSendTimeStr;

   private String  doSendTimeStr;

   private String statusStr;

   private String creator;

   //子账户名称
   private String username;
   //组件名称
   private String componentName;

   private String chargeNumTotal;

   private Integer rowNum;

   private Integer getAllPhoneFlag;

   private Integer cancelFlag;

   private Integer updateFlag;

   private String agentName;

   private String phoneStr;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSmstypeStr() {
        return smstypeStr= SmsTypeEnum.getDescByValue(getSmstype());
    }

    public void setSmstypeStr(String smstypeStr) {
        this.smstypeStr=smstypeStr;
    }

    public String getSubmitTimeStr() {
        if(getSubmitTime()!=null){
            submitTimeStr= DateFormatUtils.format(getSubmitTime(), "yyyy-MM-dd HH:mm:ss");
        }else {
            submitTimeStr="-";
        }

        return submitTimeStr;
    }

    public void setSubmitTimeStr(String submitTimeStr) {
        this.submitTimeStr = submitTimeStr;
    }

    public String getSetSendTimeStr() {
        if(getSetSendTime()!=null){
            setSendTimeStr=DateFormatUtils.format(getSetSendTime(), "yyyy-MM-dd HH:mm:ss");
        }else {
            setSendTimeStr="-";
        }

        return setSendTimeStr;
    }

    public void setSetSendTimeStr(String setSendTimeStr) {
        this.setSendTimeStr = setSendTimeStr;
    }

    public String getDoSendTimeStr() {
        if(getDoSendTime()!=null){
            doSendTimeStr =DateFormatUtils.format(getDoSendTime(), "yyyy-MM-dd HH:mm:ss");
        }else {
            doSendTimeStr="-";
        }
        return doSendTimeStr;
    }

    public void setDoSendTimeStr(String doSendTimeStr) {
        this.doSendTimeStr = doSendTimeStr;
    }

    public String getStatusStr() {
        return statusStr= SmsSendStatus.getDescByValue(getStatus());
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getChargeNumTotal() {
        return chargeNumTotal;
    }

    public void setChargeNumTotal(String chargeNumTotal) {
        this.chargeNumTotal = chargeNumTotal;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getGetAllPhoneFlag() {
        return getAllPhoneFlag;
    }

    public void setGetAllPhoneFlag(Integer getAllPhoneFlag) {
        this.getAllPhoneFlag = getAllPhoneFlag;
    }

    public Integer getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(Integer cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public Integer getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPhoneStr() {
        return phoneStr;
    }

    public void setPhoneStr(String phoneStr) {
        this.phoneStr = phoneStr;
    }
}
