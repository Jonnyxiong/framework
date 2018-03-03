package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 审核明细表
 * @author huangwenjie
 * @date 2017-08-29
 */
public class JsmsAuditSms {
    
    // 审核明细ID，由审核模块统一生成，递增
    private Long id;
    // 审核ID
    private Long auditid;
    // 一条（长或短）短信的对应一个ID ；结构类型为uuid；由ACCESS生成，多条拼接而成的长短信对应一个
    private String smsid;
    // 创建时间
    private Date createtime;
    // 0：待发送 1：已发送3：审核未通过
    private Integer status;
    // 手机号码
    private String phone;
    // 客户接入使用协议类型，2为SMPP协议，3为CMPP协议，4为SGIP协议，5为SMGP协议，6为HTTPS协议
    private Integer smsfrom;
    // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Integer smstype;
    // 显示签名的方式，1：前置，2：后置
    private Integer showsigntype;
    // 签名端口
    private String signport;
    // 用户自扩展
    private String userport;
    // smsp access组件id，用于smsp_access组件拉取对应的审核记录
    private Integer accessid;
    // access传来的ids，这些ids的包拼成这条短信的
    private String uuids;
    // smsp_c2s组件id，用于返回状态报告
    private Integer c2sId;
    // smsp_c2s组件接收时间
    private Date csdate;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id ;
    }
    
    public Long getAuditid() {
        return auditid;
    }
    
    public void setAuditid(Long auditid) {
        this.auditid = auditid ;
    }
    
    public String getSmsid() {
        return smsid;
    }
    
    public void setSmsid(String smsid) {
        this.smsid = smsid ;
    }
    
    public Date getCreatetime() {
        return createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone ;
    }
    
    public Integer getSmsfrom() {
        return smsfrom;
    }
    
    public void setSmsfrom(Integer smsfrom) {
        this.smsfrom = smsfrom ;
    }
    
    public Integer getSmstype() {
        return smstype;
    }
    
    public void setSmstype(Integer smstype) {
        this.smstype = smstype ;
    }
    
    public Integer getShowsigntype() {
        return showsigntype;
    }
    
    public void setShowsigntype(Integer showsigntype) {
        this.showsigntype = showsigntype ;
    }
    
    public String getSignport() {
        return signport;
    }
    
    public void setSignport(String signport) {
        this.signport = signport ;
    }
    
    public String getUserport() {
        return userport;
    }
    
    public void setUserport(String userport) {
        this.userport = userport ;
    }
    
    public Integer getAccessid() {
        return accessid;
    }
    
    public void setAccessid(Integer accessid) {
        this.accessid = accessid ;
    }
    
    public String getUuids() {
        return uuids;
    }
    
    public void setUuids(String uuids) {
        this.uuids = uuids ;
    }
    
    public Integer getC2sId() {
        return c2sId;
    }
    
    public void setC2sId(Integer c2sId) {
        this.c2sId = c2sId ;
    }
    
    public Date getCsdate() {
        return csdate;
    }
    
    public void setCsdate(Date csdate) {
        this.csdate = csdate ;
    }
    
}