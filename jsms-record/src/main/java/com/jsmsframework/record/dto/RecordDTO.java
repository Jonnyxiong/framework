package com.jsmsframework.record.dto;

import com.jsmsframework.common.enums.OperatorType;
import com.jsmsframework.common.enums.SmsTypeEnum;
import com.jsmsframework.common.enums.schedule.SmsStateEnum;
import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.record.record.entity.JsmsRecord;

/**
 * Created by Don on 2018/1/11.
 */
public class RecordDTO extends JsmsRecord {
    //行号
    private String rownum;
    //客户名称
    private String clientName;
    //短信类型
    private String smsTypeStr;
    //代理商id
    private String agentId;
    //渠道名称
    private String channelName;
    //状态
    private String stateStr;
    //运营商名称
    private String operatorsTypeStr;
    //状态报告时间
    private String reportDateStr;
    //通道运营商名称
    private String channeloperatorTypeStr;
    //提交时间
    private String dealDateStr;

    private Integer identify;

    private String tableName;

    /**
     * 操作人
     */
    private Long operator;


    public Integer getIdentify() {
        return identify;
    }

    public void setIdentify(Integer identify) {
        this.identify = identify;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSmsTypeStr() {
        if (getSmstype()!=null){
            smsTypeStr = SmsTypeEnum.getDescByValue(getSmstype());
        }
        return smsTypeStr;
    }

    public void setSmsTypeStr(String smsTypeStr) {
        this.smsTypeStr = smsTypeStr;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStateStr() {
        if (getState()!=null){
            stateStr = SmsStateEnum.getDescByValue(getState());
        }
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getOperatorsTypeStr() {
        if (getOperatorstype()!=null){
            operatorsTypeStr = OperatorType.getDescByValue(getOperatorstype());
        }
        return operatorsTypeStr;
    }

    public void setOperatorsTypeStr(String operatorsTypeStr) {
        this.operatorsTypeStr = operatorsTypeStr;
    }

    public String getChanneloperatorTypeStr() {
        if (getChanneloperatorstype()!=null){
            channeloperatorTypeStr = OperatorType.getDescByValue(Integer.valueOf(getChanneloperatorstype()));
        }
        return channeloperatorTypeStr;
    }

    public void setChanneloperatorTypeStr(String channeloperatorTypeStr) {
        this.channeloperatorTypeStr = channeloperatorTypeStr;
    }
    public String getReportDateStr() {
        if (getReportdate()!=null){
            reportDateStr = DateUtil.dateToStr(getReportdate(),"yyyy-MM-dd HH:mm:ss");
        }
        return reportDateStr;
    }

    public void setReportDateStr(String reportDateStr) {
        this.reportDateStr = reportDateStr;
    }

    public String getDealDateStr() {
        if (getDate()!=null){
            dealDateStr = DateUtil.dateToStr(getDate(),"yyyy-MM-dd HH:mm:ss");
        }
        return dealDateStr;
    }

    public void setDealDateStr(String dealDateStr) {
        this.dealDateStr = dealDateStr;
    }
}
