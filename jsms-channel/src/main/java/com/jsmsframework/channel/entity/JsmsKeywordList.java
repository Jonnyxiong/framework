package com.jsmsframework.channel.entity;

import java.util.Date;

/**
 * @description 关键字表
 * @author huangwenjie
 * @date 2017-12-06
 */
public class JsmsKeywordList {
    
    // 
    private Long id;
    // 
    private String keyword;
    // 
    private String remarks;
    // 
    private Date createtime;
    
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
    
}