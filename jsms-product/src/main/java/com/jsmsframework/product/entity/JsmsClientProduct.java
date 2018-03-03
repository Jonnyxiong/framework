package com.jsmsframework.product.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 客户产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsClientProduct {
    
    // 自增序列
    private Integer id;
    // 用户帐号
    private String clientId;
    // 代理商id
    private Integer agentId;
    // 产品id号
    private Integer productId;
    // 客户销售折扣率,针对产品定价
    private BigDecimal priceDiscount;
    // 折后价，单位：元，适用于普通短信 针对产品定价
    private BigDecimal gnDiscountPrice;
    // 创建时间
    private Date createTime;
    // 修改者
    private String updator;
    // 修改时间
    private Date updateTime;
    // 备注
    private String remark;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
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
    
    public Integer getProductId() {
        return productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId ;
    }
    
    public BigDecimal getPriceDiscount() {
        return priceDiscount;
    }
    
    public void setPriceDiscount(BigDecimal priceDiscount) {
        this.priceDiscount = priceDiscount ;
    }
    
    public BigDecimal getGnDiscountPrice() {
        return gnDiscountPrice;
    }
    
    public void setGnDiscountPrice(BigDecimal gnDiscountPrice) {
        this.gnDiscountPrice = gnDiscountPrice ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public String getUpdator() {
        return updator;
    }
    
    public void setUpdator(String updator) {
        this.updator = updator ;
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