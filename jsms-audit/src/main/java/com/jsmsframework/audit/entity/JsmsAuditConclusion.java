package com.jsmsframework.audit.entity;

import java.util.Date;

/**
 * @description 审核原因表
 * @author huangwenjie
 * @date 2017-09-12
 */
public class JsmsAuditConclusion {
    
    // 序号，自增长
    private Integer id;
    // 审核不通过原因
    private String conclusionDesc;
    // 状态，0：待审核，1：审核通过，3：审核不通过，4：删除
    private Integer state;
    // 操作者（管理员id），关联t_sms_user表中id字段
    private Long userId;
    // 审核人（管理员id），关联t_sms_user表中id字段
    private Long adminId;
    // 创建时间
    private Date createtime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getConclusionDesc() {
        return conclusionDesc;
    }
    
    public void setConclusionDesc(String conclusionDesc) {
        this.conclusionDesc = conclusionDesc ;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state ;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId ;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }
    
    public Date getCreatetime() {
        return createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime ;
    }
    
}