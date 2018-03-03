package com.jsmsframework.middleware.entity;

import java.util.Date;

/**
 * @description 监控明细关联表
 * @author liangchengan
 * @date 2017-12-29
 */
public class JsmsMonitorDetailOption {
    
    // 唯一id，主键,自增
    private Integer id;
    // 列名，如果列名为*并且状态为关闭时整表关闭，关联当前表的组件都不会发送数据
    private String fieldName;
    // 类型，0：int,1：double,2:string
    private Integer fieldType;
    // 所属表名，0：receive_mt_sms,1：access_sms_report,2：accee_mo_sms,3：access_audit_sms,4：access_intercept_sms,5：access_sms_submit,6：reback_sms_submit,7：channel_sms_submit,8：channel_sms_subret,9：channel_sms_report,10：middleware_alarm
    private Integer measurement;
    // 状态,0:关闭,1:开启
    private Integer state;
    // 描述
    private String remark;
    // 更新时间
    private Date updateDate;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName ;
    }
    
    public Integer getFieldType() {
        return fieldType;
    }
    
    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType ;
    }
    
    public Integer getMeasurement() {
        return measurement;
    }
    
    public void setMeasurement(Integer measurement) {
        this.measurement = measurement ;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
}