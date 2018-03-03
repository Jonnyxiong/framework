package com.jsmsframework.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 代理商帐户余额收支明细
 * @author huangwenjie
 * @date 2017-12-29
 */
public class JsmsAgentBalanceBill {
    
    // 业务单号,自增序列
    private Integer id;
    // 代理商id
    private Integer agentId;
    // 业务类型：0：线下充值，1：扣减，2：佣金转余额，3：购买产品包，4：退款，5：赠送，6：后付费客户消耗，7：回退条数, 8：后付费客户失败返还, 9：增加授信，10：降低授信，11：在线充值
    private Integer paymentType;
    // 财务类型，0：入账，1：出账
    private String financialType;
    // 金额
    private BigDecimal amount;
    // 余额剩余，单位：元
    private BigDecimal balance;
    // 历史授信额度，单位：元，执行授信操作时间点对应值
    private BigDecimal creditBalance;
    // 历史授信回款额度，单位：元，执行授信操作时间点对应值
    private BigDecimal historyPayment;
    // 授信余额，单位：元，执行授信操作时间点对应值
    private BigDecimal currentCredit;
    // 未回款授信额度，单位：元，执行授信操作时间点对应值
    private BigDecimal noBackPayment;
    // 生成时间
    private Date createTime;
    // 订单id，1.品牌代理和销售代理\r\n对应t_sms_client_order表中order_id字段\r\n2.OEM代理\r\n对应t_sms_oem_agent_order表中order_no字段
    private Long orderId;
    // 操作管理员id
    private Long adminId;
    // 用户账号：payment_type为3时记录订单的用户账号
    private String clientId;
    // 备注
    private String remark;
    // 在线支付id，关联t_sms_online_payment表payment_id字段，当业务类型payment_type = 11时必填
    private String paymentId;
    
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
    
    public String getFinancialType() {
        return financialType;
    }
    
    public void setFinancialType(String financialType) {
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
    
    public BigDecimal getCreditBalance() {
        return creditBalance;
    }
    
    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance ;
    }
    
    public BigDecimal getHistoryPayment() {
        return historyPayment;
    }
    
    public void setHistoryPayment(BigDecimal historyPayment) {
        this.historyPayment = historyPayment ;
    }
    
    public BigDecimal getCurrentCredit() {
        return currentCredit;
    }
    
    public void setCurrentCredit(BigDecimal currentCredit) {
        this.currentCredit = currentCredit ;
    }
    
    public BigDecimal getNoBackPayment() {
        return noBackPayment;
    }
    
    public void setNoBackPayment(BigDecimal noBackPayment) {
        this.noBackPayment = noBackPayment ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId ;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
    public String getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId ;
    }
    
}