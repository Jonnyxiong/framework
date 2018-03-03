package com.jsmsframework.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 代理商佣金帐户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsAgentCommissionIncomeBill {
    
    // 业务单号,自增序列
    private Integer id;
    // 代理商id
    private Integer agentId;
    // 业务类型，0：佣金收入，1：佣金转余额
    private Integer paymentType;
    // 金额
    private BigDecimal amount;
    // 佣金剩余，单位：元
    private BigDecimal balance;
    // 财务类型，0：入账，1：出账
    private Integer financialType;
    // 支付时间
    private Date createTime;
    // 备注
    private String remark;
    
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
    
    public Integer getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType ;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount ;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance ;
    }
    
    public Integer getFinancialType() {
        return financialType;
    }
    
    public void setFinancialType(Integer financialType) {
        this.financialType = financialType ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}