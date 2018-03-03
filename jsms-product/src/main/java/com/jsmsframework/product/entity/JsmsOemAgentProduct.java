package com.jsmsframework.product.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description OEM代理商产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsOemAgentProduct {
    
    // 自增序列
    private Integer id;
    // 代理商id，关联t_sms_agent_info表中agent_id字段
    private Integer agentId;
    // 产品id号，关联t_sms_oem_product_info表中product_id字段
    private Integer productId;
    // 折后价，单位：元，适用于普通短信
    private BigDecimal discountPrice;
    // 国际短信折扣率，适用于国际短信
    private BigDecimal gjSmsDiscount;
    // 创建时间
    private Date createTime;
    // 操作管理员id
    private Long adminId;
    // 修改时间
    private Date updateTime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
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
    
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }
    
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice ;
    }
    
    public BigDecimal getGjSmsDiscount() {
        return gjSmsDiscount;
    }
    
    public void setGjSmsDiscount(BigDecimal gjSmsDiscount) {
        this.gjSmsDiscount = gjSmsDiscount ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
    
}