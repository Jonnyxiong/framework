package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 智能黑模板表
 * @author huangwenjie
 * @date 2017-11-30
 */
public class JsmsAutoBlackTemplate {
    
    // 模板id，主键,自增
    private Integer templateId;
    // 归属客户id，关联t_sms_account表中clientid字段,  全局级别时，填*
    private String clientId;
    // 模板级别，0：全局级别，1：用户级别
    private Integer templateLevel;
    // 模板类型，0：固定模板，1：变量模板
    private Integer templateType;
    // 模板属性，10：行业，11：营销
    private Integer smsType;
    // 签名，用户级别时必填，全局级别时选填
    private String sign;
    // 模板内容
    private String content;
    // 匹配发送量，submit_type=3时有值
    private Integer matchAmount;
    // 最近匹配数
    private Integer latelyMatchAmount;
    // 最近匹配日期 
    private Date latelyMatchDate;
    // 状态，0：待审核，1：审核通过，3：审核不通过，4：删除
    private Integer state;
    // 创建者（管理员id），关联t_sms_user表中id字段
    private String userId;
    // 创建时间
    private Date createTime;
    // 备注
    private String remark;
    // 更新时间
    private Date updateTime;
    
    public Integer getTemplateId() {
        return templateId;
    }
    
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId ;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId ;
    }
    
    public Integer getTemplateLevel() {
        return templateLevel;
    }
    
    public void setTemplateLevel(Integer templateLevel) {
        this.templateLevel = templateLevel ;
    }
    
    public Integer getTemplateType() {
        return templateType;
    }
    
    public void setTemplateType(Integer templateType) {
        this.templateType = templateType ;
    }
    
    public Integer getSmsType() {
        return smsType;
    }
    
    public void setSmsType(Integer smsType) {
        this.smsType = smsType ;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign ;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content ;
    }
    
    public Integer getMatchAmount() {
        return matchAmount;
    }
    
    public void setMatchAmount(Integer matchAmount) {
        this.matchAmount = matchAmount ;
    }
    
    public Integer getLatelyMatchAmount() {
        return latelyMatchAmount;
    }
    
    public void setLatelyMatchAmount(Integer latelyMatchAmount) {
        this.latelyMatchAmount = latelyMatchAmount ;
    }
    
    public Date getLatelyMatchDate() {
        return latelyMatchDate;
    }
    
    public void setLatelyMatchDate(Date latelyMatchDate) {
        this.latelyMatchDate = latelyMatchDate ;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state ;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
    
}