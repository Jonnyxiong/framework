package com.jsmsframework.finance.dto;

import com.jsmsframework.finance.entity.JsmsAgentInvoiceConfig;

import java.util.Date;

/**
 * @author huangwenjie
 * @since 2018-01-23
 */
public class JsmsAgentInvoiceConfigDto extends JsmsAgentInvoiceConfig {

    //序号
    private Integer rowNum;
    //客户名称
    private String name;
    //归属销售
    private  String belongSaleStr;

    //操作人
    private String operatorName;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelongSaleStr() {
        return belongSaleStr;
    }

    public void setBelongSaleStr(String belongSaleStr) {
        this.belongSaleStr = belongSaleStr;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}