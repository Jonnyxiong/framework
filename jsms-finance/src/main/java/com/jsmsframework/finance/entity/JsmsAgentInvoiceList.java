package com.jsmsframework.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huangwenjie
 * @since 2018-01-23
 */
public class JsmsAgentInvoiceList {

    // 序号ID，自增长，主键
    private Integer id;
    // 申请id：I + 年月日 + webid（占4个数字，用户中心为4，运营平台为3，例：0004，0003）+ 0000（流水，每天从1开始）,例如：I2017121800030001
    private String invoiceId;
    // 代理商id，关联t_sms_agent_info表中agent_id字段
    private Integer agentId;
    // 发票金额
    private BigDecimal invoiceAmount;
    // 开票主体：1：个人2：企业
    private Integer invoiceBody;
    // 发票类型，1：普通发票（电子）2：增值税专票
    private Integer invoiceType;
    // 来源方式：1. 发票申请 2.未开票客户返还发票
    private Integer source;
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
    // 申请人，关联t_sms_user表中id字段
    private Long applicant;
    // 审核者, 关联t_sms_user表中id字段
    private Long auditor;
    // 快递公司，关联t_sms_dict表中的参数类型为“express”的键值
    private Integer expressCompany;
    // 快递单号
    private String expressOrder;
    // 状态，0：待审核，1：已取消，2：审核不通过，3：开票中，4：已邮寄，5：已返还
    private Integer status;
    // 审核不通过原因
    private String auditFailCause;
    // web应用ID,1：短信调度系统2：代理商平台
    private Integer webId;
    // 备注
    private String remark;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
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

    public Long getAuditor() {
        return auditor;
    }

    public void setAuditor(Long auditor) {
        this.auditor = auditor;
    }

    public Integer getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(Integer expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressOrder() {
        return expressOrder;
    }

    public void setExpressOrder(String expressOrder) {
        this.expressOrder = expressOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditFailCause() {
        return auditFailCause;
    }

    public void setAuditFailCause(String auditFailCause) {
        this.auditFailCause = auditFailCause;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getApplicant() {
        return applicant;
    }

    public void setApplicant(Long applicant) {
        this.applicant = applicant;
    }

    public Integer getWebId() {
        return webId;
    }

    public void setWebId(Integer webId) {
        this.webId = webId;
    }
}