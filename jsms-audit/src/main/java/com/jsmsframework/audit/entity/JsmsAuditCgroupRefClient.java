package com.jsmsframework.audit.entity;


/**
 * @description 审核用户组内用户表
 * @author huangwenjie
 * @date 2017-10-31
 */
public class JsmsAuditCgroupRefClient {
    
    // 自增长id
    private Integer id;
    // 审核用户组ID，对应t_sms_audit_client_group中cgroup_id字段
    private Integer cgroupId;
    // 用户帐号，关联t_sms_accoun表中clientid字段
    private String clientid;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getCgroupId() {
        return cgroupId;
    }
    
    public void setCgroupId(Integer cgroupId) {
        this.cgroupId = cgroupId ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
}