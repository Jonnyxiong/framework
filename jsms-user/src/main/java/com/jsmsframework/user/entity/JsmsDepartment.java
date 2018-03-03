package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 部门表
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsDepartment {
    
    // 部门id，自增长
    private Integer departmentId;
    // 部门名称
    private String departmentName;
    // 部门描述
    private String departmentDesc;
    // 父级部门，基础部门的父级部门填空
    private Integer parentId;
    // 部门级别，0：基础部门，1：一级部门，2：二级部门，依此类推
    private Integer level;
    // 状态，0：关闭，1：正常
    private Integer state;
    // 创建者ID，关联t_sms_user表中id字段
    private Long createId;
    // 创建时间
    private Date createTime;
    // 修改时间
    private Date updateTime;
    
    public Integer getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId ;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName ;
    }
    
    public String getDepartmentDesc() {
        return departmentDesc;
    }
    
    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc ;
    }
    
    public Integer getParentId() {
        return parentId;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId ;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level ;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state ;
    }
    
    public Long getCreateId() {
        return createId;
    }
    
    public void setCreateId(Long createId) {
        this.createId = createId ;
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
    
}