package com.jsmsframework.common.entity;

import java.util.Date;

/**
 * @description 邮件提醒设置表
 * @author tanjiangqiang
 * @date 2017-11-30
 */
public class JsmsEmailAlarmSetting {
    
    // 序号ID, 自增长, 主键
    private Integer id;
    // 名称
    private String alarmName;
    // 提醒邮箱，多个邮箱以英文“,”分隔
    private String alarmEmail;
    // 状态，0：禁用，1：启用
    private Integer status;
    // web应用ID，1:短信调度系统,2:代理商平台,3:运营平台,4:OEM代理商平台,5:品牌客户端,5:OEM客户端
    private Integer webId;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getAlarmName() {
        return alarmName;
    }
    
    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName ;
    }
    
    public String getAlarmEmail() {
        return alarmEmail;
    }
    
    public void setAlarmEmail(String alarmEmail) {
        this.alarmEmail = alarmEmail ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Integer getWebId() {
        return webId;
    }
    
    public void setWebId(Integer webId) {
        this.webId = webId ;
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