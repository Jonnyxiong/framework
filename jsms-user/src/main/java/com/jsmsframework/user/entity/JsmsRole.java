package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 角色表
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsRole {
    
    // 角色ID
    private Integer id;
    // 角色名
    private String roleName;
    // 用户首页
    private String welcomePage;
    // 创建时间
    private Date createDate;
    // 更新时间
    private Date updateDate;
    // 角色状态：1、正常 0、禁用
    private String status;
    // web应用ID，1：短信调度系统 ，2：代理商平台，3：运营平台，4：OEM代理商平台，5：云运营平台
    private Integer webId;
    // 角色编码，通过t_sms_dict表进行角色编码配置
    private String roleCode;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName ;
    }
    
    public String getWelcomePage() {
        return welcomePage;
    }
    
    public void setWelcomePage(String welcomePage) {
        this.welcomePage = welcomePage ;
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status ;
    }
    
    public Integer getWebId() {
        return webId;
    }
    
    public void setWebId(Integer webId) {
        this.webId = webId ;
    }
    
    public String getRoleCode() {
        return roleCode;
    }
    
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode ;
    }
    
}