package com.jsmsframework.finance.entity;

import java.math.BigDecimal;

/**
 * @description OEM代理商帐户统计表
 * @author huangwenjie
 * @date 2017-08-20
 */
public class JsmsOemAgentAccountStatistics {
    
    // 代理商id
    private Integer agentId;
    // 行业短信累计购买条数
    private Integer hyTotalPurchaseNumber;
    // 行业短信累计卖出条数
    private Integer hyRemainRebateNumber;
    // 营销短信累计购买条数
    private Integer yxTotalPurchaseNumber;
    // 营销短信累计卖出条数
    private Integer yxRemainRebateNumber;
    // 国际短信累计购买金额单位：元
    private BigDecimal gjTotalPurchaseAmount;
    // 国际短信累计卖出金额 单位：元
    private BigDecimal gjRemainRebateAmount;
    // 验证码短信累计购买条数
    private Integer yzmTotalPurchaseNumber;
    // 验证码短信累计卖出条数
    private Integer yzmRemainRebateNumber;
    // 通知短信累计购买条数
    private Integer tzTotalPurchaseNumber;
    // 通知短信累计卖出条数
    private Integer tzRemainRebateNumber;
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public Integer getHyTotalPurchaseNumber() {
        return hyTotalPurchaseNumber;
    }
    
    public void setHyTotalPurchaseNumber(Integer hyTotalPurchaseNumber) {
        this.hyTotalPurchaseNumber = hyTotalPurchaseNumber ;
    }
    
    public Integer getHyRemainRebateNumber() {
        return hyRemainRebateNumber;
    }
    
    public void setHyRemainRebateNumber(Integer hyRemainRebateNumber) {
        this.hyRemainRebateNumber = hyRemainRebateNumber ;
    }
    
    public Integer getYxTotalPurchaseNumber() {
        return yxTotalPurchaseNumber;
    }
    
    public void setYxTotalPurchaseNumber(Integer yxTotalPurchaseNumber) {
        this.yxTotalPurchaseNumber = yxTotalPurchaseNumber ;
    }
    
    public Integer getYxRemainRebateNumber() {
        return yxRemainRebateNumber;
    }
    
    public void setYxRemainRebateNumber(Integer yxRemainRebateNumber) {
        this.yxRemainRebateNumber = yxRemainRebateNumber ;
    }
    
    public BigDecimal getGjTotalPurchaseAmount() {
        return gjTotalPurchaseAmount;
    }
    
    public void setGjTotalPurchaseAmount(BigDecimal gjTotalPurchaseAmount) {
        this.gjTotalPurchaseAmount = gjTotalPurchaseAmount ;
    }
    
    public BigDecimal getGjRemainRebateAmount() {
        return gjRemainRebateAmount;
    }
    
    public void setGjRemainRebateAmount(BigDecimal gjRemainRebateAmount) {
        this.gjRemainRebateAmount = gjRemainRebateAmount ;
    }
    
    public Integer getYzmTotalPurchaseNumber() {
        return yzmTotalPurchaseNumber;
    }
    
    public void setYzmTotalPurchaseNumber(Integer yzmTotalPurchaseNumber) {
        this.yzmTotalPurchaseNumber = yzmTotalPurchaseNumber ;
    }
    
    public Integer getYzmRemainRebateNumber() {
        return yzmRemainRebateNumber;
    }
    
    public void setYzmRemainRebateNumber(Integer yzmRemainRebateNumber) {
        this.yzmRemainRebateNumber = yzmRemainRebateNumber ;
    }
    
    public Integer getTzTotalPurchaseNumber() {
        return tzTotalPurchaseNumber;
    }
    
    public void setTzTotalPurchaseNumber(Integer tzTotalPurchaseNumber) {
        this.tzTotalPurchaseNumber = tzTotalPurchaseNumber ;
    }
    
    public Integer getTzRemainRebateNumber() {
        return tzRemainRebateNumber;
    }
    
    public void setTzRemainRebateNumber(Integer tzRemainRebateNumber) {
        this.tzRemainRebateNumber = tzRemainRebateNumber ;
    }
    
}