package com.jsmsframework.order.dto;


import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OEM代理商客户充值和回退页面数据实体
 */
public class OemClientRechargeRollBackDTO {

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("agent_id")
    private Integer agentId;

    @SerializedName("product_type")
    private Integer productType;

    @SerializedName(value = "updateNum", alternate = {"update_num"})
    private BigDecimal updateNum;

    @SerializedName("due_time")
    private Date dueTime;

    @SerializedName("unit_price")
    private BigDecimal unitPrice;

    @SerializedName("operator_code")
    private Integer operatorCode;

    @SerializedName("area_code")
    private Integer areaCode;

    private Date updateTime;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public BigDecimal getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(BigDecimal updateNum) {
        this.updateNum = updateNum;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
