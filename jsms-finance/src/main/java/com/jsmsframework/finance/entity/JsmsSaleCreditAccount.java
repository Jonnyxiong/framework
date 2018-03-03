package com.jsmsframework.finance.entity;

import java.math.BigDecimal;

/**
 * @description 销售授信帐户表
 * @author huangwenjie
 * @date 2017-10-25
 */
public class JsmsSaleCreditAccount {
    
    // 销售id，关联t_sms_user表中id字段
    private Long saleId;
    // 财务历史授信额度，单位：元
    private BigDecimal financialHistoryCredit;
    // 当前授信余额，单位：元
    private BigDecimal currentCredit;
    // 销售历史授出额度，单位：元
    private BigDecimal saleHistoryCredit;
    // 客户历史回款，单位：元
    private BigDecimal agentHistoryPayment;
    // 未回款额度，单位：元
    private BigDecimal noBackPayment;

    private  String mobile;

    private  String realName;

    private  String email;
    
    public Long getSaleId() {
        return saleId;
    }
    
    public void setSaleId(Long saleId) {
        this.saleId = saleId ;
    }
    
    public BigDecimal getFinancialHistoryCredit() {
        return financialHistoryCredit;
    }
    
    public void setFinancialHistoryCredit(BigDecimal financialHistoryCredit) {
        this.financialHistoryCredit = financialHistoryCredit ;
    }
    
    public BigDecimal getCurrentCredit() {
        return currentCredit;
    }
    
    public void setCurrentCredit(BigDecimal currentCredit) {
        this.currentCredit = currentCredit ;
    }
    
    public BigDecimal getSaleHistoryCredit() {
        return saleHistoryCredit;
    }
    
    public void setSaleHistoryCredit(BigDecimal saleHistoryCredit) {
        this.saleHistoryCredit = saleHistoryCredit ;
    }
    
    public BigDecimal getAgentHistoryPayment() {
        return agentHistoryPayment;
    }
    
    public void setAgentHistoryPayment(BigDecimal agentHistoryPayment) {
        this.agentHistoryPayment = agentHistoryPayment ;
    }
    
    public BigDecimal getNoBackPayment() {
        return noBackPayment;
    }
    
    public void setNoBackPayment(BigDecimal noBackPayment) {
        this.noBackPayment = noBackPayment ;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}