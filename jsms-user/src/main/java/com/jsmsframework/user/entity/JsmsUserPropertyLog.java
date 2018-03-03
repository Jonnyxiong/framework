package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 用户属性变更记录
 * @author lpjLiu
 * @date 2017-10-11
 */
public class JsmsUserPropertyLog {
    
    // 序号ID，自增长，主键
    private Integer id;
    // 用户账号,关联t_sms_account表中clientid字段
    private String clientid;
    // 属性
    private String property;
    // 值
    private String value;
    // 生效日期
    private Date effectDate;
    // 操作者, 对应t_sms_user表中id字段
    private Long operator;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
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