package com.jsmsframework.product.entity;

import java.util.Date;

/**
 * @description 代理商产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsAgentProduct {
    
    // 自增序列
    private Integer id;
    // 代理商id
    private Integer agentId;
    // 产品id号
    private Integer productId;
    // 备注
    private String remark;
    // 创建者
    private String creator;
    // 创建时间
    private Date createTime;
    
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
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
}