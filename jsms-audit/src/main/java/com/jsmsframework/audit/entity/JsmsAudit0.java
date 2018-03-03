package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 审核内容表(周更新,原始表)
 * @author huangwenjie
 * @date 2017-11-28
 */
public class JsmsAudit0 {
    
    // 审核ID，由smsp_audit组件统一生成，递增
    private Long auditid;
    // 创建时间
    private Date createtime;
    // 用户帐号（子帐号）
    private String clientid;
    // 
    private String username;
    // 内容
    private String content;
    // 签名
    private String sign;
    // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Integer smstype;
    // client|sign|content|smstype md5值
    private String md5;
    // 0：待审核，1：审核通过，2：审核不通过, 3：转审
    private Integer status;
    // 发送数量（同一审核ID进入审核明细表的总数量）
    private Integer sendnum;
    // 最后发送时间（同一审核ID最后一条提交的时间）
    private Date lastSendtime;
    // 付费类型，0：预付费，1：后付费
    private Integer paytype;
    // 移除标志，0：不移除，1：系统移除，2：人工移除
    private Integer removeflag;
    // 已读标志，0：未读，1：已读
    private Integer readedflag;
    // 被转交审核人
    private String transferperson;
    // 转交审核时间
    private Date transfertime;
    // 审核人
    private String auditperson;
    // 审核时间
    private Date audittime;
    // 操作备注
    private String optRemark;

    private String tableDate;
    
    public Long getAuditid() {
        return auditid;
    }
    
    public void setAuditid(Long auditid) {
        this.auditid = auditid ;
    }
    
    public Date getCreatetime() {
        return createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username ;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content ;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign ;
    }
    
    public Integer getSmstype() {
        return smstype;
    }
    
    public void setSmstype(Integer smstype) {
        this.smstype = smstype ;
    }
    
    public String getMd5() {
        return md5;
    }
    
    public void setMd5(String md5) {
        this.md5 = md5 ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Integer getSendnum() {
        return sendnum;
    }
    
    public void setSendnum(Integer sendnum) {
        this.sendnum = sendnum ;
    }
    
    public Date getLastSendtime() {
        return lastSendtime;
    }
    
    public void setLastSendtime(Date lastSendtime) {
        this.lastSendtime = lastSendtime ;
    }
    
    public Integer getPaytype() {
        return paytype;
    }
    
    public void setPaytype(Integer paytype) {
        this.paytype = paytype ;
    }
    
    public Integer getRemoveflag() {
        return removeflag;
    }
    
    public void setRemoveflag(Integer removeflag) {
        this.removeflag = removeflag ;
    }
    
    public Integer getReadedflag() {
        return readedflag;
    }
    
    public void setReadedflag(Integer readedflag) {
        this.readedflag = readedflag ;
    }
    
    public String getTransferperson() {
        return transferperson;
    }
    
    public void setTransferperson(String transferperson) {
        this.transferperson = transferperson ;
    }
    
    public Date getTransfertime() {
        return transfertime;
    }
    
    public void setTransfertime(Date transfertime) {
        this.transfertime = transfertime ;
    }
    
    public String getAuditperson() {
        return auditperson;
    }
    
    public void setAuditperson(String auditperson) {
        this.auditperson = auditperson ;
    }
    
    public Date getAudittime() {
        return audittime;
    }
    
    public void setAudittime(Date audittime) {
        this.audittime = audittime ;
    }
    
    public String getOptRemark() {
        return optRemark;
    }
    
    public void setOptRemark(String optRemark) {
        this.optRemark = optRemark ;
    }

    public String getTableDate() {
        return tableDate;
    }

    public void setTableDate(String tableDate) {
        this.tableDate = tableDate;
    }
}