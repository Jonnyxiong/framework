package com.jsmsframework.order.entity;

import java.util.Date;
import java.math.BigDecimal;

/**
 * @description OEM代理商短信池
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsOemAgentPool {
    
    // 代理商短信池id,自增长
    private Long agentPoolId;
    // 代理商id
    private Integer agentId;
    // 产品类型，0：行业，1：营销，2：国际
    private Integer productType;
    // 对应运营商，0：全网，1：移动，2：联通，3：电信，4：国际
    private Integer operatorCode;
    // 适用区域，0：全国，1：国际
    private Integer areaCode;
    // 到期时间
    private Date dueTime;
    // 状态，0：正常，1：停用
    private Integer status;
    // 普通短信剩余条数，单位：条
    private Integer remainNumber;
    // 普通短信单价，单位：元
    private BigDecimal unitPrice;
    // 国际短信剩余金额，单位：元
    private BigDecimal remainAmount;
    // 更新时间
    private Date updateTime;
    // 备注
    private String remark;
    
    public Long getAgentPoolId() {
        return agentPoolId;
    }
    
    public void setAgentPoolId(Long agentPoolId) {
        this.agentPoolId = agentPoolId ;
    }
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public Integer getProductType() {
        return productType;
    }
    
    public void setProductType(Integer productType) {
        this.productType = productType ;
    }
    
    public Integer getOperatorCode() {
        return operatorCode;
    }
    
    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode ;
    }
    
    public Integer getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode ;
    }
    
    public Date getDueTime() {
        return dueTime;
    }
    
    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Integer getRemainNumber() {
        return remainNumber;
    }
    
    public void setRemainNumber(Integer remainNumber) {
        this.remainNumber = remainNumber ;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice ;
    }
    
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }
    
    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount ;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}