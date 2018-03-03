package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 审核关键字分组表
 * @author huangwenjie
 * @date 2017-10-31
 */
public class JsmsAuditKeywordGroup {
    
    // 
    private Integer kgroupId;
    // 组别名称
    private String kgroupName;
    // 是否为默认组，0：否，1：是，有且仅有一条记录为默认组
    private Integer isDefault;
    // 操作者，对应t_sms_user表中id字段
    private Long operator;
    // 更新时间
    private Date updateDate;
    // 备注
    private String remark;
    
    public Integer getKgroupId() {
        return kgroupId;
    }
    
    public void setKgroupId(Integer kgroupId) {
        this.kgroupId = kgroupId ;
    }
    
    public String getKgroupName() {
        return kgroupName;
    }
    
    public void setKgroupName(String kgroupName) {
        this.kgroupName = kgroupName ;
    }
    
    public Integer getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault ;
    }
    
    public Long getOperator() {
        return operator;
    }
    
    public void setOperator(Long operator) {
        this.operator = operator ;
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