package com.jsmsframework.finance.entity;

import java.math.BigDecimal;

/**
 * @description 代理商帐户表
 * @author huangwenjie
 * @date 2017-08-08
 */
public class JsmsAgentAccount {

    // 代理商id
    private Integer agentId;
    // 账户余额（元）
    private BigDecimal balance;
    // 历史授信额度，单位：元
    private BigDecimal creditBalance;
    //历史授信回款额度，单位：元
    private BigDecimal historyPayment;
    //授信余额，单位：元
    private BigDecimal currentCredit;
    //未回款授信额度，单位：元
    private BigDecimal noBackPayment;
    // 佣金收入（元）
    private BigDecimal commissionIncome;
    // 返点收入（元）
    private BigDecimal rebateIncome;
    // 押金（元）
    private BigDecimal deposit;
    // 历史充值总额（元）
    private BigDecimal accumulatedRecharge;
    // 累计消费总额（元）
    private BigDecimal accumulatedConsume;
    // 历史累计佣金收入（元）
    private BigDecimal accumulatedIncome;
    // 累计返点收入（元）
    private BigDecimal accumulatedRebateIncome;
    // 累计返点支出（元）
    private BigDecimal accumulatedRebatePay;
    // 累计退款金额(元)
    private BigDecimal accumulatedRefund;
    // 已开票金额(元)
    private BigDecimal hasTakeInvoice;
    // 已开票初始化金额(元)
    private BigDecimal hasTakeInvoiceInit;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
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
        this.creditBalance = creditBalance;
    }

    public BigDecimal getHistoryPayment() {
        return historyPayment;
    }

    public void setHistoryPayment(BigDecimal historyPayment) {
        this.historyPayment = historyPayment;
    }

    public BigDecimal getCurrentCredit() {
        return currentCredit;
    }

    public void setCurrentCredit(BigDecimal currentCredit) {
        this.currentCredit = currentCredit;
    }

    public BigDecimal getNoBackPayment() {
        return noBackPayment;
    }

    public void setNoBackPayment(BigDecimal noBackPayment) {
        this.noBackPayment = noBackPayment;
    }

    public BigDecimal getCommissionIncome() {
        return commissionIncome;
    }

    public void setCommissionIncome(BigDecimal commissionIncome) {
        this.commissionIncome = commissionIncome;
    }

    public BigDecimal getRebateIncome() {
        return rebateIncome;
    }

    public void setRebateIncome(BigDecimal rebateIncome) {
        this.rebateIncome = rebateIncome;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getAccumulatedRecharge() {
        return accumulatedRecharge;
    }

    public void setAccumulatedRecharge(BigDecimal accumulatedRecharge) {
        this.accumulatedRecharge = accumulatedRecharge;
    }

    public BigDecimal getAccumulatedConsume() {
        return accumulatedConsume;
    }

    public void setAccumulatedConsume(BigDecimal accumulatedConsume) {
        this.accumulatedConsume = accumulatedConsume;
    }

    public BigDecimal getAccumulatedIncome() {
        return accumulatedIncome;
    }

    public void setAccumulatedIncome(BigDecimal accumulatedIncome) {
        this.accumulatedIncome = accumulatedIncome;
    }

    public BigDecimal getAccumulatedRebateIncome() {
        return accumulatedRebateIncome;
    }

    public void setAccumulatedRebateIncome(BigDecimal accumulatedRebateIncome) {
        this.accumulatedRebateIncome = accumulatedRebateIncome;
    }

    public BigDecimal getAccumulatedRebatePay() {
        return accumulatedRebatePay;
    }

    public void setAccumulatedRebatePay(BigDecimal accumulatedRebatePay) {
        this.accumulatedRebatePay = accumulatedRebatePay;
    }

    public BigDecimal getAccumulatedRefund() {
        return accumulatedRefund;
    }

    public void setAccumulatedRefund(BigDecimal accumulatedRefund) {
        this.accumulatedRefund = accumulatedRefund;
    }

    public BigDecimal getHasTakeInvoice() {
        return hasTakeInvoice;
    }

    public void setHasTakeInvoice(BigDecimal hasTakeInvoice) {
        this.hasTakeInvoice = hasTakeInvoice;
    }

    public BigDecimal getHasTakeInvoiceInit() {
        return hasTakeInvoiceInit;
    }

    public void setHasTakeInvoiceInit(BigDecimal hasTakeInvoiceInit) {
        this.hasTakeInvoiceInit = hasTakeInvoiceInit;
    }

}