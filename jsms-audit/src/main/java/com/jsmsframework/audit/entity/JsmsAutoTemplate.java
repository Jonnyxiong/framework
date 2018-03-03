package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 智能模板表
 * @author huangwenjie
 * @date 2017-08-28
 */
public class JsmsAutoTemplate {
    
    // 模板id，主键,自增
    private Integer templateId;
    // 归属客户id，关联t_sms_account表中clientid字段
    private String clientId;
    // 归属代理商id
    private Integer agentId;
    // 模板类型，0：固定模板，1：变量模板
    private Integer templateType;
    // 模板属性 10:行业，11：营销
    private Integer smsType;
    // 签名
    private String sign;
    // 模板内容
    private String content;
    // 状态，0：待审核，1：审核通过，3：审核不通过，4：删除
    private Integer state;
    // 审核人（管理员id），关联t_sms_user表中id字段
    private Long adminId;
    // 创建时间
    private Date createTime;
    // 提交类型，0：客户提交，1：代理商提交，2：平台提交,3：系统自动提交
    private Integer submitType;
    // 备注
    private String remark;
    // 更新时间
    private Date updateTime;
    // web应用ID，1：短信调度系统，2：代理商平台，3：运营平台，4：OEM代理商平台，5：云运营平台，6：云运维平台
    private Integer webId;
    //原短信内容 submit_type=3时有值
    private String smsContent;
    //匹配发送量  submit_type=3时有值
    private Integer matchAmount;
    //创建者（管理员id）关联t_sms_user表中id字段，当submit_type=2时使用
    private  String userId;
    // 模板级别，0：全局级别，1
    private Integer templateLevel;
    // 最近匹配数
    private Integer latelyMatchAmount;
    // 最近匹配日期
    private Date latelyMatchDate;
    
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
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
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
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state ;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public Integer getSubmitType() {
        return submitType;
    }
    
    public void setSubmitType(Integer submitType) {
        this.submitType = submitType ;
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
    
    public Integer getWebId() {
        return webId;
    }
    
    public void setWebId(Integer webId) {
        this.webId = webId ;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Integer getMatchAmount() {
        return matchAmount;
    }

    public void setMatchAmount(Integer matchAmount) {
        this.matchAmount = matchAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTemplateLevel() {
        return templateLevel;
    }

    public void setTemplateLevel(Integer templateLevel) {
        this.templateLevel = templateLevel ;
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

}