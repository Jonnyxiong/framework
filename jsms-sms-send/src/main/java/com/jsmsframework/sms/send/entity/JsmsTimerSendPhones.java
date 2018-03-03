package com.jsmsframework.sms.send.entity;

import java.util.Date;

/**     t_sms_timer_send_phones_xxx每周一表
 * @description 定时短信发送任务关联号码表-原表
 * @author Don
 * @date 2018-01-04
 */
public class JsmsTimerSendPhones {
    
    // 序号ID，自增长，主键
    private Integer id;
    // 定时短信任务ID，关联t_sms_timer_send_task中的task_id字段
    private Integer taskId;
    // 用户帐号，关联t_sms_account表中clientid字段
    private Integer clientid;
    // 目标手机号码
    private String phone;
    // 该号码的处理状态，1：接收成功，2：接收失败，3：已处理
    private Integer status;
    // 同t_sms_timer_send_task表中的submit_time
    private Date submitTime;
    // 更新时间
    private Date updatetime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getTaskId() {
        return taskId;
    }
    
    public void setTaskId(Integer taskId) {
        this.taskId = taskId ;
    }
    
    public Integer getClientid() {
        return clientid;
    }
    
    public void setClientid(Integer clientid) {
        this.clientid = clientid ;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Date getSubmitTime() {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime ;
    }
    
    public Date getUpdatetime() {
        return updatetime;
    }
    
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime ;
    }
    
}