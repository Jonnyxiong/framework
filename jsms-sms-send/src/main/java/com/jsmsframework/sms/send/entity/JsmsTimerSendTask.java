package com.jsmsframework.sms.send.entity;

import java.util.Date;

/**
 * @description 定时短信发送任务信息表
 * @author Don
 * @date 2018-01-04
 */
public class JsmsTimerSendTask {
    
    // 序号ID，自增长，主键 60位
    private String taskId;
    // 用户帐号，关联t_sms_account表中clientid字段
    private String clientid;
    // 代理商id，关联t_sms_agent_info表中agent_id字段
    private Integer agentId;
    // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Integer smstype;
    // 提交类型：0：子账户 1：代理商
    private Integer submittype;
    // 待发送的手机号码总数
    private Integer totalPhonesNum;
    // 客户发送短信进入短信系统的标识，一条（长或短）短信的对应一个ID
    private String smsid;
    // 客户接入使用协议类型: 2为SMPP协议，3为CMPP协议，4为SGIP协议，5为SMGP协议，6为HTTPS协议
    private Integer smsfrom;
    // 处理该请求的组件ID
    private Integer componentId;
    // 扩展端口
    private String extend;
    // 用户透传ID，随状态报告返回给用户
    private String uid;
    // 签名
    private String sign;
    // 短信内容
    private String content;
    // 定时短信提交时间
    private Date submitTime;
    // 定时短信设置的发送时间
    private Date setSendTime;
    // 定时短信实际发送时间
    private Date doSendTime;
    // 定时短信任务当前的状态，0：处理中，1：等待中，2：进行中，3：已完成，4：已取消，5：已删除
    private Integer status;
    // 备注：如状态为“已取消”时备注需注明取消的原因
    private String remark;
    //计费条数，（签名+短信内容）对应的计费条数，小于等于70字算一条，大于70字按67字拆分计算条数
    private Integer chargeNum;
    //显示自签名的方式，1：前置，2：后置
    private Integer showSignType;
    //是否为中文短信：0：否 1：是
    private Integer isChina;

    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public Integer getSmstype() {
        return smstype;
    }
    
    public void setSmstype(Integer smstype) {
        this.smstype = smstype ;
    }

    public Integer getSubmittype() {
        return submittype;
    }

    public void setSubmittype(Integer submittype) {
        this.submittype = submittype;
    }

    public Integer getTotalPhonesNum() {
        return totalPhonesNum;
    }
    
    public void setTotalPhonesNum(Integer totalPhonesNum) {
        this.totalPhonesNum = totalPhonesNum ;
    }
    
    public String getSmsid() {
        return smsid;
    }
    
    public void setSmsid(String smsid) {
        this.smsid = smsid ;
    }
    
    public Integer getSmsfrom() {
        return smsfrom;
    }
    
    public void setSmsfrom(Integer smsfrom) {
        this.smsfrom = smsfrom ;
    }
    
    public Integer getComponentId() {
        return componentId;
    }
    
    public void setComponentId(Integer componentId) {
        this.componentId = componentId ;
    }
    
    public String getExtend() {
        return extend;
    }
    
    public void setExtend(String extend) {
        this.extend = extend ;
    }
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid ;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign ;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content ;
    }
    
    public Date getSubmitTime() {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime ;
    }
    
    public Date getSetSendTime() {
        return setSendTime;
    }
    
    public void setSetSendTime(Date setSendTime) {
        this.setSendTime = setSendTime ;
    }
    
    public Date getDoSendTime() {
        return doSendTime;
    }
    
    public void setDoSendTime(Date doSendTime) {
        this.doSendTime = doSendTime ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }

    public Integer getChargeNum() {
        return chargeNum;
    }

    public void setChargeNum(Integer chargeNum) {
        this.chargeNum = chargeNum;
    }

    public Integer getShowSignType() {
        return showSignType;
    }

    public void setShowSignType(Integer showSignType) {
        this.showSignType = showSignType;
    }

    public Integer getIsChina() {
        return isChina;
    }

    public void setIsChina(Integer isChina) {
        this.isChina = isChina;
    }
}