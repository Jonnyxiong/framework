package com.jsmsframework.common.entity;

import java.util.Date;

/**
 * @description 发送邮箱管理表
 * @author tanjiangqiang
 * @date 2017-11-30
 */
public class JsmsSendEmailList {
    
    // 序号ID, 自增长, 主键
    private Integer id;
    // 名称
    private String alarmName;
    // 邮箱类型
    private Integer mailboxType;
    // 收件服务器ip:port
    private String incomingMailServer;
    // 发件服务器ip:port
    private String outgoingMailServer;
    // 电子邮箱地址
    private String emailAddress;
    // 用户名
    private String emailUsername;
    // 密码
    private String emailPassword;
    // 状态，0：禁用，1：启用
    private Integer status;
    // web应用ID，1:短信调度系统,2:代理商平台,3:运营平台,4:OEM代理商平台,5:品牌客户端,5:OEM客户端
    private Integer webId;
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
    
    public String getAlarmName() {
        return alarmName;
    }
    
    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName ;
    }
    
    public Integer getMailboxType() {
        return mailboxType;
    }
    
    public void setMailboxType(Integer mailboxType) {
        this.mailboxType = mailboxType ;
    }
    
    public String getIncomingMailServer() {
        return incomingMailServer;
    }
    
    public void setIncomingMailServer(String incomingMailServer) {
        this.incomingMailServer = incomingMailServer ;
    }
    
    public String getOutgoingMailServer() {
        return outgoingMailServer;
    }
    
    public void setOutgoingMailServer(String outgoingMailServer) {
        this.outgoingMailServer = outgoingMailServer ;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress ;
    }
    
    public String getEmailUsername() {
        return emailUsername;
    }
    
    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername ;
    }
    
    public String getEmailPassword() {
        return emailPassword;
    }
    
    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Integer getWebId() {
        return webId;
    }
    
    public void setWebId(Integer webId) {
        this.webId = webId ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
    
}