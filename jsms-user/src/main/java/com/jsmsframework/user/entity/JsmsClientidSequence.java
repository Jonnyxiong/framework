package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 客户账号clientid序列表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsClientidSequence {
    
    // 主键(自增)
    private Long id;
    // 客户账号(6位,首位a-z,末位0-9,其余为0-z)
    private String clientid;
    // 客户账号使用状态,0:未使用,1:已使用
    private Boolean status;
    // 是否被锁定，0：否 1：是
    private Boolean lock;
    // 锁定开始时间
    private Date lockStartTime;
    // clientid类型：0 归属于代理商的客户的账号，1 直客的账号
    private Integer clientidType;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public Boolean getStatus() {
        return status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status ;
    }
    
    public Boolean getLock() {
        return lock;
    }
    
    public void setLock(Boolean lock) {
        this.lock = lock ;
    }
    
    public Date getLockStartTime() {
        return lockStartTime;
    }
    
    public void setLockStartTime(Date lockStartTime) {
        this.lockStartTime = lockStartTime ;
    }
    
    public Integer getClientidType() {
        return clientidType;
    }
    
    public void setClientidType(Integer clientidType) {
        this.clientidType = clientidType ;
    }
    
}