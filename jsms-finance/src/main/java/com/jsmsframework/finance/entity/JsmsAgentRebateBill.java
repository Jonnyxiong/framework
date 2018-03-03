package com.jsmsframework.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 代理商返点账户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsAgentRebateBill {
    
    // 业务单号,自增序列id
    private Long id;
    // 代理商id
    private Integer agentId;
    // 业务类型,0:返点收入,1:抵扣
    private Integer paymentType;
    // 财务类型,0:入账,1:出账
    private Integer financialType;
    // 订单id
    private Long orderId;
    // 金额,单位:元
    private BigDecimal amount;
    // 返点剩余,单位:元
    private BigDecimal balance;
    // 
    private Date createTime;
    // 
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
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId ;
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
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}