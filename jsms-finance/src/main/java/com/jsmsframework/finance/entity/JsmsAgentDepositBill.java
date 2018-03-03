package com.jsmsframework.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 代理商押金账户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsAgentDepositBill {
    
    // 业务单号,自增序列id
    private Long id;
    // 代理商id
    private Integer agentId;
    // 业务类型,0:押金充值,1押金扣减,2:押金退款
    private Integer paymentType;
    // 财务类型,0:入账,1:出账
    private Integer financialType;
    // 金额,单位:元
    private BigDecimal amount;
    // 押金剩余,单位:元
    private BigDecimal balance;
    // 生成时间
    private Date createTime;
    // 操作管理员id
    private Long adminId;
    // 备注
    private String remark;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
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
    
    public Integer getFinancialType() {
        return financialType;
    }
    
    public void setFinancialType(Integer financialType) {
        this.financialType = financialType ;
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
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}