package com.jsmsframework.order.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiongfenglin on 2017/10/18.
 */
public class JsmsClientConsumer {
    private int id;
    private String clientId;
    // 订单子id，唯一id,递增
    private Long subId;
    // 订单id号，15位规则：YYMMDDHHMM（年月日时分，10位）+ 类型（1位）+ 序号（4位，0000－9999），其中：客户下订单时，类型为0，品牌代理下订单时，类型为1，OEM代理下订单时，类型为2
    private Long orderId;
    //操作类型 0：短信失败返还
    private Integer operateType;
    //产品类型.0：行业，1：营销， 2：国际， 3：验证码，4：通知， 7：USSD，8：闪信，9：挂机短信其中0、1、3、4为普通短信，2为国际短信
    private Integer productType;
    //对应运营商，0：全网， 1：移动， 2：联通， 3：电信， 4：国际
    private Integer operatorCode;
    //适用区域，0：全国，1：国际
    private int areaCode;
    //短信单价，单位：元
    private BigDecimal unitPrice;
    //到期时间
    private Date dueTime;
    //短信条数，单位：条
    private int smsNumber;
    //消费日期
    private int consumerDate;
    //操作者，对应t_sms_user表中id字段
    private Long operator;
    //操作时间
    private Date operateTime;
    //备注
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public int getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(int smsNumber) {
        this.smsNumber = smsNumber;
    }

    public int getConsumerDate() {
        return consumerDate;
    }

    public void setConsumerDate(int consumerDate) {
        this.consumerDate = consumerDate;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
