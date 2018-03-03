package com.jsmsframework.audit.dto;

import java.util.Date;

public class JsmsIdAndCreatetime {

    // 审核明细ID，由审核模块统一生成，递增
    private Long id;
    // 创建时间
    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
