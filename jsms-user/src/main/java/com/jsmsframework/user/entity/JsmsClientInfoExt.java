package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 用户信息扩展表
 * @author huangwenjie
 * @date 2017-11-07
 */
public class JsmsClientInfoExt {
    
    // 序号，自增长
    private Integer id;
    // 用户帐号,关联t_sms_accoun表中clientid字段
    private String clientid;
    // 
    private String webPassword;
    // 扩展属性,1：支持子账号,2：自动返还
    private Integer extValue;
    // 父级用户账号,关联t_sms_accoun表中clientid字段
    private String parentId;
    // 客户星级
    private Integer starLevel;
    // 账号标签
    private String clientLabel;
    // 更新者,对应t_sms_user表中id字段
    private Long updator;
    // 更新时间
    private Date updateDate;
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
    
    public String getWebPassword() {
        return webPassword;
    }
    
    public void setWebPassword(String webPassword) {
        this.webPassword = webPassword ;
    }
    
    public Integer getExtValue() {
        return extValue;
    }
    
    public void setExtValue(Integer extValue) {
        this.extValue = extValue ;
    }
    
    public String getParentId() {
        return parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId ;
    }
    
    public Integer getStarLevel() {
        return starLevel;
    }
    
    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel ;
    }

    public String getClientLabel() {
        return clientLabel;
    }

    public void setClientLabel(String clientLabel) {
        this.clientLabel = clientLabel;
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