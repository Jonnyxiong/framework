package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsUserAuditKeywordIgnore;
import com.jsmsframework.common.enums.SmsTypeEnum;
import com.jsmsframework.common.util.DateUtil;

/**
 * @author yeshiyuan
 * @create 2018/1/22
 */
public class JsmsUserAuditKeywordIgnoreDTO extends JsmsUserAuditKeywordIgnore{
    private Integer rowNum;
    private String updateDateStr;
    private String operatorName;
    private String smsTypeStr;
    private String clientName;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getUpdateDateStr() {
        if (getUpdateDate()!=null){
            updateDateStr = DateUtil.dateToStr(getUpdateDate(),"yyyy-MM-dd HH:mm:ss");
        }
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getSmsTypeStr() {
        smsTypeStr = SmsTypeEnum.getDescByValue(getSmstype());
        return smsTypeStr;
    }

    public void setSmsTypeStr(String smsTypeStr) {
        this.smsTypeStr = smsTypeStr;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
