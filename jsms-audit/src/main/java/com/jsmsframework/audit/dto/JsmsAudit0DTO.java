package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsAudit0;

import java.text.SimpleDateFormat;

/**短信审核-历史审核0查询
 * Created by Don on 2017-11-28.
 */
public class JsmsAudit0DTO extends JsmsAudit0 {
    Integer rownum;
    String smsTypeName;
    String statusName;
    String transferpersonName;
    String auditpersonName;
    String audittimeStr;
    String createtimeStr;

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public String getSmsTypeName() {
        return smsTypeName;
    }

    public void setSmsTypeName(String smsTypeName) {
        this.smsTypeName = smsTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTransferpersonName() {
        return transferpersonName;
    }

    public void setTransferpersonName(String transferpersonName) {
        this.transferpersonName = transferpersonName;
    }

    public String getAuditpersonName() {
        return auditpersonName;
    }

    public void setAuditpersonName(String auditpersonName) {
        this.auditpersonName = auditpersonName;
    }

    public String getAudittimeStr() {
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getAudittime()).toString();
    }

    public void setAudittimeStr(String audittimeStr) {
        this.audittimeStr = audittimeStr;
    }

    public String getCreatetimeStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCreatetime()).toString();
    }

    public void setCreatetimeStr(String createtimeStr) {
        this.createtimeStr = createtimeStr;
    }
}
