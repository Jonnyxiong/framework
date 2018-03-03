package com.jsmsframework.finance.entity;

import java.util.Date;

/**
 * @author huangwenjie
 * @since 2018-01-23
 */
public class JsmsAgentInvoiceConfig {
    
    // 序号ID，自增长，主键
    private Integer id;
    // 代理商id，关联t_sms_agent_info表中agent_id字段
    private Integer agingId;
    // 代理商id，关联t_sms_agent_info表中agent_id字段
    private Integer agintName;
    // 发票主体：1：个人，2：企业
    private Integer invoiceBody;
    // 发票类型，1：普通发票（电子），2：增值税专票
    private Integer invoiceType;
    // 电子邮箱
    private String email;
    // 发票抬头
    private String invoiceHead;
    // 统一社会信用代码
    private String creditCode;
    // 开户银行
    private String bank;
    // 开户账号
    private String bankAccount;
    // 公司注册地址
    private String companyRegAddr;
    // 公司固定电话
    private String telphone;
    // 收件人
    private String toName;
    // 收件人手机
    private String toPhone;
    // 收件人地址,省市区以英文逗号“,”分隔
    private String toAddr;
    // 收件人详细地址
    private String toAddrDetail;
    // 收件人qq
    private String toQq;
    // 操作者，关联t_sms_user表中id字段
    private Long operator;
    // 状态，0：正常，1：删除
    private Integer status;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    // 备注
    private String remark;

    public Integer getAgintName() {
        return agintName;
    }

    public void setAgintName(Integer agintName) {
        this.agintName = agintName;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgingId() {
        return agingId;
    }
    
    public void setAgingId(Integer agingId) {
        this.agingId = agingId;
    }

    public Integer getInvoiceBody() {
        return invoiceBody;
    }
    
    public void setInvoiceBody(Integer invoiceBody) {
        this.invoiceBody = invoiceBody;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }
    
    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }
    
    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public String getCreditCode() {
        return creditCode;
    }
    
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getBank() {
        return bank;
    }
    
    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }
    
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCompanyRegAddr() {
        return companyRegAddr;
    }
    
    public void setCompanyRegAddr(String companyRegAddr) {
        this.companyRegAddr = companyRegAddr;
    }

    public String getTelphone() {
        return telphone;
    }
    
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getToName() {
        return toName;
    }
    
    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToPhone() {
        return toPhone;
    }
    
    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public String getToAddr() {
        return toAddr;
    }
    
    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getToAddrDetail() {
        return toAddrDetail;
    }
    
    public void setToAddrDetail(String toAddrDetail) {
        this.toAddrDetail = toAddrDetail;
    }

    public String getToQq() {
        return toQq;
    }
    
    public void setToQq(String toQq) {
        this.toQq = toQq;
    }

    public Long getOperator() {
        return operator;
    }
    
    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

}