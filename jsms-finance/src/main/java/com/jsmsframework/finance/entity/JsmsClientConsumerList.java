package com.jsmsframework.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 品牌客户消耗记录
 * @author lpjLiu
 * @date 2017-10-11
 */
public class JsmsClientConsumerList {
    
    // 序号ID，自增长, 主键
    private Integer id;
    // 用户账号,关联t_sms_account表中clientid字段
    private String clientid;
    // 订单子id, 关联t_sms_client_order表中sub_id字段
    private Long subId;
    // 订单id, 关联t_sms_client_order表中order_id字段
    private Long orderId;
    // 操作烈性, 0:短信失败返还
    private Integer operateType;
    // 产品类型, 0:行业, 1:营销, 2:国际, 3:验证码, 4:通知, 7:USSD, 8:闪信, 9:挂机短信 ;其中0、1、3、4为普通短信, 2为国际短信
    private Integer productType;
    // 对应运营商, 0:全网, 1:移动, 2:联通, 3:电信, 4:国际
    private Integer operatorCode;
    // 使用区域, 0:全国, 1:国际
    private Integer areaCode;
    // 短信单价, 单位:元
    private BigDecimal unitPrice;
    // 到期时间
    private Date dueTime;
    // 短信条数, 单价:条
    private Integer smsNumber;
    // 消费日期
    private Integer consumerDate;
    // 操作者, 对应t_sms_user表中id字段
    private Long operator;
    // 操作时间
    private Date operateTime;
    // 备注
    private String remark;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public Long getSubId() {
        return subId;
    }
    
    public void setSubId(Long subId) {
        this.subId = subId ;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId ;
    }
    
    public Integer getOperateType() {
        return operateType;
    }
    
    public void setOperateType(Integer operateType) {
        this.operateType = operateType ;
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
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice ;
    }
    
    public Date getDueTime() {
        return dueTime;
    }
    
    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime ;
    }
    
    public Integer getSmsNumber() {
        return smsNumber;
    }
    
    public void setSmsNumber(Integer smsNumber) {
        this.smsNumber = smsNumber ;
    }
    
    public Integer getConsumerDate() {
        return consumerDate;
    }
    
    public void setConsumerDate(Integer consumerDate) {
        this.consumerDate = consumerDate ;
    }
    
    public Long getOperator() {
        return operator;
    }
    
    public void setOperator(Long operator) {
        this.operator = operator ;
    }
    
    public Date getOperateTime() {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}