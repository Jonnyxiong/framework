package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 用户登陆状态表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsAccountLoginStatus {
    
    // 
    private Integer id;
    // 用户帐号
    private String clientid;
    // 锁定代码
    private Integer code;
    // 锁定原因
    private String remarks;
    // 锁定时间
    private Date locktime;
    // 解锁时间
    private Date unlocktime;
    // 解锁人
    private String unlockby;
    // 锁定状态，0：锁定，1：解锁
    private Integer status;
    // 更新时间
    private Date updatetime;
    
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
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code ;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks ;
    }
    
    public Date getLocktime() {
        return locktime;
    }
    
    public void setLocktime(Date locktime) {
        this.locktime = locktime ;
    }
    
    public Date getUnlocktime() {
        return unlocktime;
    }
    
    public void setUnlocktime(Date unlocktime) {
        this.unlocktime = unlocktime ;
    }
    
    public String getUnlockby() {
        return unlockby;
    }
    
    public void setUnlockby(String unlockby) {
        this.unlockby = unlockby ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Date getUpdatetime() {
        return updatetime;
    }
    
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime ;
    }
    
}