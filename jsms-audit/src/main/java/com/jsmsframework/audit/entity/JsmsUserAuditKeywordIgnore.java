package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 用户审核关键字忽略表
 * @author Don
 * @date 2018-01-17
 */
public class JsmsUserAuditKeywordIgnore {
    
    // 序号ID，自增长，主键
    private Integer id;
    // 用户帐号，关联t_sms_account表中clientid字段必须是具体的客户id，不能以*等符号表示全体客户
    private String clientid;
    // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Integer smstype;
    // 用户忽略的审核关键字
    private String ignoreKeyword;
    // 状态，0：正常1：删除
    private Integer status;
    // 说明
    private String remarks;
    // 修改时间
    private Date updateDate;
    // 操作者ID，关联t_sms_user表中id字段，值为0时，表示由系统自动添加
    private Long operatorId;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public Integer getSmstype() {
        return smstype;
    }
    
    public void setSmstype(Integer smstype) {
        this.smstype = smstype ;
    }
    
    public String getIgnoreKeyword() {
        return ignoreKeyword;
    }
    
    public void setIgnoreKeyword(String ignoreKeyword) {
        this.ignoreKeyword = ignoreKeyword ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
    public Long getOperatorId() {
        return operatorId;
    }
    
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId ;
    }
    
}