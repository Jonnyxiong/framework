package com.jsmsframework.audit.dto;

import java.util.Date;

public class JsmsAuditidAndCreatetime {
    // 审核ID，由smsp_audit组件统一生成，递增
    private Long auditid;
    // 创建时间
    private Date createtime;

    public Long getAuditid() {
        return auditid;
    }

    public void setAuditid(Long auditid) {
        this.auditid = auditid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
