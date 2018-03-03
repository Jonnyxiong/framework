package com.jsmsframework.channel.entity;

import java.util.Date;

/**
 * @description 通道属性变更记录表
 * @author Don
 * @date 2018-01-13
 */
public class JsmsChannelPropertyLog {
    
    // 
    private Integer id;
    // 
    private Integer channelId;
    // 
    private String property;
    // 
    private String value;
    // 
    private Date effectDate;
    // 
    private Long operator;
    // 
    private Date createTime;
    // 
    private Date updateTime;
    // 
    private String remark;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getChannelId() {
        return channelId;
    }
    
    public void setChannelId(Integer channelId) {
        this.channelId = channelId ;
    }
    
    public String getProperty() {
        return property;
    }
    
    public void setProperty(String property) {
        this.property = property ;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value ;
    }
    
    public Date getEffectDate() {
        return effectDate;
    }
    
    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate ;
    }
    
    public Long getOperator() {
        return operator;
    }
    
    public void setOperator(Long operator) {
        this.operator = operator ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
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