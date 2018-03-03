package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 认证审核记录表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsOauthAuditLog {
    
    // 唯一id，自增长
    private Integer id;
    // 代理商id
    private Integer agentId;
    // 用户帐号
    private String clientId;
    // 审核人（管理员id）
    private Long adminId;
    // 审核类型，1：代理商认证，2：客户认证
    private Integer auditType;
    // 状态，0：审核不通过，1：审核通过
    private Integer status;
    // 创建时间
    private Date createDate;
    // 备注
    private String remark;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId ;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }
    
    public Integer getAuditType() {
        return auditType;
    }
    
    public void setAuditType(Integer auditType) {
        this.auditType = auditType ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}