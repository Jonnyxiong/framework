package com.jsmsframework.order.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description OEM代理商客户订单
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsOemClientOrder {
    
    // 订单id号，15位规则：YYMMDDHHMM（年月日时分，10位）+ 类型（1位）+ 序号（4位，0000－9999），其中：客户下订单时，类型为0，品牌代理下订单时，类型为1，OEM代理下订单时，类型为2
    private Long orderId;
    // 订单编号，15位，当多个order_id同属一个订单编号时，订单编号值为最小的order_id，如：170519162330001、170519162330002同属一个订单编号，则order_no=170519162330001
    private Long orderNo;
    // 产品类型，0：行业，1：营销，2：国际
    private Integer productType;
    //对应运营商，0：全网，1：移动，2：联通，3：电信，4：国际 所有订单类型时都有值
    private Integer operatorCode;
    //适用区域，0：全国，1：国际 所有订单类型时都有值
    private Integer areaCode;
    // 订单类型，1：OEM代理商分发，2：OEM代理商回退，3：短信失败返还
    private Integer orderType;
    // 普通订单短信条数，单位：条
    private Integer orderNumber;
    // 普通订单短信单价，单位：元，普通短信时有值
    private BigDecimal unitPrice;
    // 国际订单买价，单位：元
    private BigDecimal orderPrice;
    // 用户帐号
    private String clientId;
    // 所属代理商id
    private Integer agentId;
    // 客户短信池id
    private Long clientPoolId;
    // 到期时间
    private Date dueTime;
    // 创建时间
    private Date createTime;
    // 消费日期
    private Integer consumerDate;
    // 操作者, 对应t_sms_user表中id字段
    private Long operator;
    // 备注
    private String remark;
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId ;
    }
    
    public Long getOrderNo() {
        return orderNo;
    }
    
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo ;
    }
    
    public Integer getProductType() {
        return productType;
    }
    
    public void setProductType(Integer productType) {
        this.productType = productType ;
    }
    
    public Integer getOrderType() {
        return orderType;
    }
    
    public void setOrderType(Integer orderType) {
        this.orderType = orderType ;
    }
    
    public Integer getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber ;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice ;
    }
    
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }
    
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice ;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId ;
    }
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public Long getClientPoolId() {
        return clientPoolId;
    }
    
    public void setClientPoolId(Long clientPoolId) {
        this.clientPoolId = clientPoolId ;
    }
    
    public Date getDueTime() {
        return dueTime;
    }
    
    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime ;
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

    public Integer getConsumerDate() {
        return consumerDate;
    }

    public void setConsumerDate(Integer consumerDate) {
        this.consumerDate = consumerDate;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }
}