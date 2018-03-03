package com.jsmsframework.sysKeyword.entity;

import java.util.Date;

/**
 * @description 系统关键字列表
 * @author huangwenjie
 * @date 2018-01-15
 */
public class JsmsKeywordList {
    
    // 
    private Long id;
    // 
    private String keyword;
    // 
    private String remarks;
    // 更新时间
    private Date createtime;
    // 类别ID
    private Integer categoryId;
    // 操作者，对应t_sms_user表中id字段
    private Long operator;
    // 更新时间
    private Date updateDate;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id ;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword ;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks ;
    }
    
    public Date getCreatetime() {
        return createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime ;
    }
    
    public Integer getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId ;
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
    
}