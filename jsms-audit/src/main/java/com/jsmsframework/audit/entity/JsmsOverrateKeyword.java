package com.jsmsframework.audit.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description 超频关键字表
 * @author huangwenjie
 * @date 2017-10-31
 */
public class JsmsOverrateKeyword {
    
    // 自增长id
    private Integer id;
    // 关键字
    private String keyword;
    // 用户帐号，'*'表示适用于所有用户
    private String clientid;
    // 备注
    private String remarks;
    // 更新时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
}