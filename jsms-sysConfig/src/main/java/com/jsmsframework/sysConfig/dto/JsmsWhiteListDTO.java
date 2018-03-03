package com.jsmsframework.sysConfig.dto;

import com.jsmsframework.sysConfig.entity.JsmsWhiteList;

import java.text.SimpleDateFormat;

/**
 * Created by Don on 2018/1/19.
 */
public class JsmsWhiteListDTO extends JsmsWhiteList{

    private String smsTpyesDesc;

    private String operatorName;

    private String createTimestr;


    public String getSmsTpyesDesc() {
        return smsTpyesDesc;
    }

    public void setSmsTpyesDesc(String smsTpyesDesc) {
        this.smsTpyesDesc = smsTpyesDesc;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getCreateTimestr() {
        return   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCreatetime()).toString();
    }

    public void setCreateTimestr(String createTimestr) {
        this.createTimestr = createTimestr;
    }
}
