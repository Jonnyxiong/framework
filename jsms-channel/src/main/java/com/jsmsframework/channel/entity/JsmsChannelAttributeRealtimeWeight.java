package com.jsmsframework.channel.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 通道属性实时权重表
 * @author huangwenjie
 * @date 2017-09-28
 */
public class JsmsChannelAttributeRealtimeWeight {
    
    // 唯一ID
    private Long id;
    // 通道id，关联t_sms_channel表中cid字段
    private Integer channelid;
    // 状态报告成功率，小数点后2位，取值范围：[0，100]
    private BigDecimal successRate;
    // 单价，单位：元 取值范围：[0，1]，小数点后4位
    private BigDecimal unitPrice;
    // 抗投诉率，小数点后2位，取值范围：[0，100]
    private BigDecimal antiComplaint;
    // 扩展标志，1：客情，2：低销
    private Integer exFlag;
    // 低销额度
    private BigDecimal lowConsumeLimit;
    // 验证码成功率权重， 小数点后2位，取值范围：[0，100]
    private BigDecimal yzSuccessWeight;
    // 通知成功率权重， 小数点后2位，取值范围：[0，100]
    private BigDecimal tzSuccessWeight;
    // 营销成功率权重， 小数点后2位，取值范围：[0，100]
    private BigDecimal yxSuccessWeight;
    // 告警成功率权重， 小数点后2位，取值范围：[0，100]
    private BigDecimal gjSuccessWeight;
    // 移动号码对应通道价格权重， 小数点后2位，取值范围：[0，100]
    private BigDecimal ydPriceWeight;
    // 联通号码对应通道价格权重， 小数点后2位，取值范围：[0，100]
    private BigDecimal ltPriceWeight;
    // 电信号码对应通道价格权重， 小数点后2位，取值范围：[0，100]
    private BigDecimal dxPriceWeight;
    // 更新者，对应t_sms_user表中id字段，系统自动更新，值为0，手动更新，值为用户id
    private Long updator;
    // 更新时间
    private Date updateDate;
    // 备注
    private String remark;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id ;
    }
    
    public Integer getChannelid() {
        return channelid;
    }
    
    public void setChannelid(Integer channelid) {
        this.channelid = channelid ;
    }
    
    public BigDecimal getSuccessRate() {
        return successRate;
    }
    
    public void setSuccessRate(BigDecimal successRate) {
        this.successRate = successRate ;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice ;
    }
    
    public BigDecimal getAntiComplaint() {
        return antiComplaint;
    }
    
    public void setAntiComplaint(BigDecimal antiComplaint) {
        this.antiComplaint = antiComplaint ;
    }
    
    public Integer getExFlag() {
        return exFlag;
    }
    
    public void setExFlag(Integer exFlag) {
        this.exFlag = exFlag ;
    }
    
    public BigDecimal getLowConsumeLimit() {
        return lowConsumeLimit;
    }
    
    public void setLowConsumeLimit(BigDecimal lowConsumeLimit) {
        this.lowConsumeLimit = lowConsumeLimit ;
    }
    
    public BigDecimal getYzSuccessWeight() {
        return yzSuccessWeight;
    }
    
    public void setYzSuccessWeight(BigDecimal yzSuccessWeight) {
        this.yzSuccessWeight = yzSuccessWeight ;
    }
    
    public BigDecimal getTzSuccessWeight() {
        return tzSuccessWeight;
    }
    
    public void setTzSuccessWeight(BigDecimal tzSuccessWeight) {
        this.tzSuccessWeight = tzSuccessWeight ;
    }
    
    public BigDecimal getYxSuccessWeight() {
        return yxSuccessWeight;
    }
    
    public void setYxSuccessWeight(BigDecimal yxSuccessWeight) {
        this.yxSuccessWeight = yxSuccessWeight ;
    }
    
    public BigDecimal getGjSuccessWeight() {
        return gjSuccessWeight;
    }
    
    public void setGjSuccessWeight(BigDecimal gjSuccessWeight) {
        this.gjSuccessWeight = gjSuccessWeight ;
    }
    
    public BigDecimal getYdPriceWeight() {
        return ydPriceWeight;
    }
    
    public void setYdPriceWeight(BigDecimal ydPriceWeight) {
        this.ydPriceWeight = ydPriceWeight ;
    }
    
    public BigDecimal getLtPriceWeight() {
        return ltPriceWeight;
    }
    
    public void setLtPriceWeight(BigDecimal ltPriceWeight) {
        this.ltPriceWeight = ltPriceWeight ;
    }
    
    public BigDecimal getDxPriceWeight() {
        return dxPriceWeight;
    }
    
    public void setDxPriceWeight(BigDecimal dxPriceWeight) {
        this.dxPriceWeight = dxPriceWeight ;
    }
    
    public Long getUpdator() {
        return updator;
    }
    
    public void setUpdator(Long updator) {
        this.updator = updator ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}