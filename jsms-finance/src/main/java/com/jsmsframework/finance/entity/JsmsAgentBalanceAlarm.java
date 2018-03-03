package com.jsmsframework.finance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 代理商余额预警信息表
 * @author huangwenjie
 * @date 2017-08-08
 */
public class JsmsAgentBalanceAlarm {
    
    // 序号ID,自增长,主键
    private Integer id;
    // 代理商id,关联t_sms_agent_info表中agent_id字段
    private Integer agentId;
    // 接收告警手机号,多个手机号以","分隔
    private String alarmPhone;
    // 告警阀值,单位:元,, 0元不警告
    private BigDecimal alarmAmount;
    // 告警可提醒次数
    private Integer reminderNumber;
    // 告警次数充值时间
    private Date resetTime;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public String getAlarmPhone() {
        return alarmPhone;
    }
    
    public void setAlarmPhone(String alarmPhone) {
        this.alarmPhone = alarmPhone ;
    }
    
    public BigDecimal getAlarmAmount() {
        return alarmAmount;
    }
    
    public void setAlarmAmount(BigDecimal alarmAmount) {
        this.alarmAmount = alarmAmount ;
    }

    public Integer getReminderNumber() {
        return reminderNumber;
    }

    public void setReminderNumber(Integer reminderNumber) {
        this.reminderNumber = reminderNumber;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getResetTime() {
        return resetTime;
    }
    
    public void setResetTime(Date resetTime) {
        this.resetTime = resetTime ;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
}