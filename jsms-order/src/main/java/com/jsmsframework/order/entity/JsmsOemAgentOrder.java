package com.jsmsframework.order.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description OEM代理商订单
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsOemAgentOrder {
    
    // 订单id号，15位规则：YYMMDDHHMM（年月日时分，10位）+ 类型（1位）+ 序号（4位，0000－9999），其中：客户下订单时，类型为0，品牌代理下订单时，类型为1，OEM代理下订单时，类型为2
    private Long orderId;
    // 订单编号，15位，当多个order_id同属一个订单编号时，订单编号值为最小的order_id，如：170519162330001、170519162330002同属一个订单编号，则order_no=170519162330001
    private Long orderNo;
    // 订单类型，0：OEM代理商购买，1：OEM代理商分发，2：OEM代理商回退
    private Integer orderType;
    // 产品id号订单类型为0时有值
    private Integer productId;
    // 产品代码
    private String productCode;
    // 产品类型，0：行业，1：营销，2：国际订单类型为0时有值
    private Integer productType;
    // 对应运营商，0：全网，1：移动，2：联通，3：电信，4：国际 所有订单类型时都有值
    private Integer operatorCode;
    // 适用区域，0：全国，1：国际 所有订单类型时都有值
    private Integer areaCode;
    // 产品名称订单类型为0时有值
    private String productName;
    // 短信单价，单位：元订单类型为0且为普通短信时有值
    private BigDecimal unitPrice;
    // 订单短信条数，单位：条国际短信时为空
    private Integer orderNumber;
    // 订单金额，单位：元订单类型为0或为国际短信时有值
    private BigDecimal orderAmount;
    // 国际产品包售价OEM代理商国际产品包优惠前的价格,以此价格送入代理商短信池
    private BigDecimal productPrice;
    // 代理商id
    private Integer agentId;
    // 客户ID订单类型为1、2时填用户帐号，订单类型为0时填'00000'
    private String clientId;
    // 客户户名称订单类型为1、2时填用户名称，订单类型为0时填'云之讯'
    private String name;
    // 代理商短信池id
    private Long agentPoolId;
    // 到期时间
    private Date dueTime;
    // 创建时间
    private Date createTime;
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
    
    public Integer getOrderType() {
        return orderType;
    }
    
    public void setOrderType(Integer orderType) {
        this.orderType = orderType ;
    }
    
    public Integer getProductId() {
        return productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId ;
    }
    
    public String getProductCode() {
        return productCode;
    }
    
    public void setProductCode(String productCode) {
        this.productCode = productCode ;
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
        this.operatorCode = operatorCode;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName ;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice ;
    }
    
    public Integer getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber ;
    }
    
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }
    
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount ;
    }
    
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice ;
    }
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId ;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name ;
    }
    
    public Long getAgentPoolId() {
        return agentPoolId;
    }
    
    public void setAgentPoolId(Long agentPoolId) {
        this.agentPoolId = agentPoolId ;
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
    
}