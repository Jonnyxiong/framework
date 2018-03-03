package com.jsmsframework.finance.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 提醒设置表
 * @author huangwenjie
 * @date 2017-08-08
 */
public class JsmsTaskAlarmSetting {
    
    // 序号ID,自增长,主键
    private Integer id;
    // 提醒类型：1短信余额提醒 2可用额度提醒
    @NotNull(message = "提醒类型不能为空")
    private Integer taskAlarmType;
    // 提醒频次
    @NotNull(message = "提醒频次不能为空")
    private Integer taskAlarmFrequecy;

    // 提醒内容--运营
    private String taskAlarmContent;
    // 提醒内容--销售
    private String saleAlarmContent;
    // 提醒内容--客户
    private String userAlarmContent;
    // 提醒内容--提醒手机号
    private String taskAlarmPhone;
    // 提醒内容--默认提醒金额
    private BigDecimal taskAlarmAmount;

    // 提醒时间段开始时间(含)
    private String beginTime;
    // 提醒时间段结束时间(含)
    private String endTime;
    // 检查频次(毫秒)
    @Min(value = 0,  message = "检查频率最小为0")
    @Max(value = 1440,  message = "检查频率最大为1440")
    private Integer scanFrequecy;
    // 状态,0:禁用,1:启用
    @NotNull(message = "状态不能为空")
    private Integer status;
    // web应用ID：1短信调度系统 2代理商平台 3运营平台 4OEM代理商平台 5品牌客户端 6OEM客户端
    @NotNull(message = "webId不能为空")
    private Integer webId;
    // 创建时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    // 更新时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getTaskAlarmType() {
        return taskAlarmType;
    }
    
    public void setTaskAlarmType(Integer taskAlarmType) {
        this.taskAlarmType = taskAlarmType ;
    }
    
    public Integer getTaskAlarmFrequecy() {
        return taskAlarmFrequecy;
    }
    
    public void setTaskAlarmFrequecy(Integer taskAlarmFrequecy) {
        this.taskAlarmFrequecy = taskAlarmFrequecy ;
    }
    
    public String getTaskAlarmContent() {
        return taskAlarmContent;
    }
    
    public void setTaskAlarmContent(String taskAlarmContent) {
        this.taskAlarmContent = taskAlarmContent ;
    }

    public String getSaleAlarmContent() {
        return saleAlarmContent;
    }

    public void setSaleAlarmContent(String saleAlarmContent) {
        this.saleAlarmContent = saleAlarmContent;
    }

    public String getUserAlarmContent() {
        return userAlarmContent;
    }

    public void setUserAlarmContent(String userAlarmContent) {
        this.userAlarmContent = userAlarmContent;
    }

    public String getTaskAlarmPhone() {
        return taskAlarmPhone;
    }

    public void setTaskAlarmPhone(String taskAlarmPhone) {
        this.taskAlarmPhone = taskAlarmPhone;
    }

    public BigDecimal getTaskAlarmAmount() {
        return taskAlarmAmount;
    }

    public void setTaskAlarmAmount(BigDecimal taskAlarmAmount) {
        this.taskAlarmAmount = taskAlarmAmount;
    }

    public String getBeginTime() {
        return beginTime;
    }
    
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime ;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime ;
    }
    
    public Integer getScanFrequecy() {
        return scanFrequecy;
    }
    
    public void setScanFrequecy(Integer scanFrequecy) {
        this.scanFrequecy = scanFrequecy ;
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