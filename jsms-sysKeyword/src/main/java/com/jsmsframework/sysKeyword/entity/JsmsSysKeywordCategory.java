package com.jsmsframework.sysKeyword.entity;

import java.util.Date;

/**
 * @description 系统关键字分类表
 * @author huangwenjie
 * @date 2018-01-15
 */
public class JsmsSysKeywordCategory {
    
    // 类别ID，自增长
    private Integer categoryId;
    // 类别名称
    private String categoryName;
    // 类别描述
    private String categoryDesc;
    // 显示颜色
    private String categoryColor;
    // 操作者，对应t_sms_user表中id字段
    private Long operator;
    // 更新时间
    private Date updateDate;
    // 备注
    private String remark;
    
    public Integer getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId ;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName ;
    }
    
    public String getCategoryDesc() {
        return categoryDesc;
    }
    
    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc ;
    }
    
    public String getCategoryColor() {
        return categoryColor;
    }
    
    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor ;
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