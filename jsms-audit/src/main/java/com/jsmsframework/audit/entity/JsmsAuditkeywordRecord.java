package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 短信审核与关键字记录表
 * @author huangwenjie
 * @date 2017-09-12
 */
public class JsmsAuditkeywordRecord {
    
    // 自增长主键
    private Integer id;
    // 审核ID
    private Long auditid;
    // 审核时的关键字
    private String keyword;
    // 1：审核通过，2：审核不通过
    private Integer auditStatus;
    // 审核记录表中审核记录的创建时间
    private Date auditCreateTime;
    // 创建时间
    private Date createTime;
    // keyword归属类别ID
    private Integer categoryId;
    // 排序，1－n，表示关键字的触发顺序
    private Integer sort;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Long getAuditid() {
        return auditid;
    }
    
    public void setAuditid(Long auditid) {
        this.auditid = auditid ;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword ;
    }
    
    public Integer getAuditStatus() {
        return auditStatus;
    }
    
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus ;
    }
    
    public Date getAuditCreateTime() {
        return auditCreateTime;
    }
    
    public void setAuditCreateTime(Date auditCreateTime) {
        this.auditCreateTime = auditCreateTime ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId ;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort ;
    }
}