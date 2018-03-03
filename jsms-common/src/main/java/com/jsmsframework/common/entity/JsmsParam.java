package com.jsmsframework.common.entity;

import java.util.Date;

/**
 * @description 参数表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsParam {
    
    // 参数id
    private Long paramId;
    // 参数键值
    private String paramKey;
    // 参数值
    private String paramValue;
    // 描述
    private String description;
    // 创建时间
    private Date createDate;
    // 更新时间
    private Date updateDate;
    // 是否有效1有效,2无效
    private Byte paramStatus;
    // 参数类型，0：系统参数 1：SMSP参数
    private Integer paramType;
    
    public Long getParamId() {
        return paramId;
    }
    
    public void setParamId(Long paramId) {
        this.paramId = paramId ;
    }
    
    public String getParamKey() {
        return paramKey;
    }
    
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey ;
    }
    
    public String getParamValue() {
        return paramValue;
    }
    
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue ;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description ;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
    public Byte getParamStatus() {
        return paramStatus;
    }
    
    public void setParamStatus(Byte paramStatus) {
        this.paramStatus = paramStatus ;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType ;
    }
    
}