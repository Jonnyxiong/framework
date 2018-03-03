package com.jsmsframework.channel.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 通道池策略表
 * @author huangwenjie
 * @date 2017-09-28
 */
public class JsmsChannelPoolPolicy {
    
    // 策略ID，自增长，唯一
    private Long policyId;
    // 策略名称
    private String policyName;
    // 通道成功率权重， 小数点后2位，取值范围：[0，100]，如果不包含此因子，值=0
    private BigDecimal successWeight;
    // 通道价格权重， 小数点后2位，取值范围：[0，100]，如果不包含此因子，值=0
    private BigDecimal priceWeight;
    // 抗投诉率权重， 小数点后2位，取值范围：[0，100]，如果不包含此因子，值=0
    private BigDecimal antiComplaintWeight;
    // 低销权重， 小数点后2位，取值范围：[0，100]，如果不包含此因子，值=0
    private BigDecimal lowConsumeWeight;
    // 客情权重， 小数点后2位，取值范围：[0，100]，如果不包含此因子，值=0
    private BigDecimal customerRelationWeight;
    // 是否为默认策略，0：否，1：是，有且仅有一条记录为默认策略
    private Integer isDefault;
    // 备注
    private String remark;
    // 更新者，对应t_sms_user表中id字段
    private Long updator;
    // 更新时间
    private Date updateDate;
    
    public Long getPolicyId() {
        return policyId;
    }
    
    public void setPolicyId(Long policyId) {
        this.policyId = policyId ;
    }
    
    public String getPolicyName() {
        return policyName;
    }
    
    public void setPolicyName(String policyName) {
        this.policyName = policyName ;
    }
    
    public BigDecimal getSuccessWeight() {
        return successWeight;
    }
    
    public void setSuccessWeight(BigDecimal successWeight) {
        this.successWeight = successWeight ;
    }
    
    public BigDecimal getPriceWeight() {
        return priceWeight;
    }
    
    public void setPriceWeight(BigDecimal priceWeight) {
        this.priceWeight = priceWeight ;
    }
    
    public BigDecimal getAntiComplaintWeight() {
        return antiComplaintWeight;
    }
    
    public void setAntiComplaintWeight(BigDecimal antiComplaintWeight) {
        this.antiComplaintWeight = antiComplaintWeight ;
    }
    
    public BigDecimal getLowConsumeWeight() {
        return lowConsumeWeight;
    }
    
    public void setLowConsumeWeight(BigDecimal lowConsumeWeight) {
        this.lowConsumeWeight = lowConsumeWeight ;
    }
    
    public BigDecimal getCustomerRelationWeight() {
        return customerRelationWeight;
    }
    
    public void setCustomerRelationWeight(BigDecimal customerRelationWeight) {
        this.customerRelationWeight = customerRelationWeight ;
    }
    
    public Integer getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
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
    
}