package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 审核用户组
 * @author huangwenjie
 * @date 2017-10-31
 */
public class JsmsAuditClientGroup {
    
    // 审核用户组ID，自增长
    private Integer cgroupId;
    // 审核用户组名称
    private String cgroupName;
    // 关联的审核关键字组别ID，对应t_sms_audit_keyword_group中kgroup_id字段
    private Integer kgroupId;
    // 是否为默认组，0：否，1：是，有且仅有一条记录为默认组
    private Integer isDefault;
    // 操作者，对应t_sms_user表中id字段
    private Long operator;
    // 更新时间
    private Date updateDate;
    // 备注
    private String remark;
    
    public Integer getCgroupId() {
        return cgroupId;
    }
    
    public void setCgroupId(Integer cgroupId) {
        this.cgroupId = cgroupId ;
    }
    
    public String getCgroupName() {
        return cgroupName;
    }
    
    public void setCgroupName(String cgroupName) {
        this.cgroupName = cgroupName ;
    }
    
    public Integer getKgroupId() {
        return kgroupId;
    }
    
    public void setKgroupId(Integer kgroupId) {
        this.kgroupId = kgroupId ;
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