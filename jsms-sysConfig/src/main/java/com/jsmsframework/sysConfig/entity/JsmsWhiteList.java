package com.jsmsframework.sysConfig.entity;

import java.util.Date;

/**
 * @description 黑名单表
 * @author Don
 * @date 2018-01-10
 */
public class JsmsWhiteList {
    
    // 
    private Long id;
    // 
    private String phone;
    //短信类型组合，1：通知短信，2：验证码短信，4：营销短信，8：告警短信，16：USSD，32：闪信，值为配置的代表各短信类型的数值总和,举例：若号码对通知短信和验证码短信为系统黑名单，smstypes值为3 (1+2)
    private Long smstypes;
    // 
    private String remarks;
    // 
    private Date createtime;

    private Long operatorId;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id ;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone ;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks ;
    }
    
    public Date getCreatetime() {
        return createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime ;
    }

    public Long getSmstypes() {
        return smstypes;
    }

    public void setSmstypes(Long smstypes) {
        this.smstypes = smstypes;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
}