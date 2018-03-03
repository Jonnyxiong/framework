package com.jsmsframework.sysKeyword.entity;

import java.util.Date;

/**
 * @description 系统关键字组内类别表
 * @author huangwenjie
 * @date 2018-01-15
 */
public class JsmsSysKgroupRefCategory {
    
    // 自增长id
    private Integer id;
    // 组别ID，对应t_sms_sys_keyword_group中kgroup_id字段
    private Integer kgroupId;
    // 类别ID，对应t_sms_sys_keyword_category表中category_id字段
    private Integer categoryId;
    // 排序，0－9，0最高，9最低
    private Integer sort;
    // 更新时间
    private Date updateDate;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getKgroupId() {
        return kgroupId;
    }
    
    public void setKgroupId(Integer kgroupId) {
        this.kgroupId = kgroupId ;
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
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
}