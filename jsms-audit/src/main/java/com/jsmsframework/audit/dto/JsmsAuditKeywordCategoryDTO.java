package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsAuditKeywordCategory;
import com.jsmsframework.common.enums.audit.AuditClientGroupIsDefault;

public class JsmsAuditKeywordCategoryDTO extends JsmsAuditKeywordCategory {
    // 排序，0－9，0最高，9最低
    private Integer sort;

    /**
     * 是否是默认用户组的分配
     */
    AuditClientGroupIsDefault auditClientGroupIsDefault;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public AuditClientGroupIsDefault getAuditClientGroupIsDefault() {
        return auditClientGroupIsDefault;
    }

    public void setAuditClientGroupIsDefault(AuditClientGroupIsDefault auditClientGroupIsDefault) {
        this.auditClientGroupIsDefault = auditClientGroupIsDefault;
    }
}
