package com.jsmsframework.finance.entity;

import java.util.Date;
import java.math.BigDecimal;

/**
 * @description 在线支付流水表
 * @author huangwenjie
 * @date 2018-01-05
 */
public class JsmsOnlinePayment {

    // 支付id,18位，规则："Z"+ web_id + XX + YYMMDDHHMM（年月日时分，10位）+ 序号（4位，0000－9999）,XX预留系统id,暂定00
    private String paymentId;
    // 代理商id，关联t_sms_agent_info表中agent_id字段
    private Integer agentId;
    // 流水号，支付宝、微信或银联返回的流水号
    private String flowId;
    // 支付金额，单位：元
    private BigDecimal paymentAmount;
    // 支付方式，0：支付宝，1：微信支付
    private Integer paymentMode;
    // 支付状态，0：未支付，1：支付已提交，2：支付成功，3：支付失败（超时未支付），4：支付取消，5：超时支付，6：支付异常
    private Integer paymentState;
    // 创建时间
    private Date createTime;
    // 支付提交时间
    private Date submitTime;
    // 支付提交截止时间（submit_time + t_sms_param.param_key= ONLINE_PAYMENT_OVERTIME中第一个参数）
    private Date submitDeadline;
    // 备注，系统操作时备注
    private String remark;
    // desc	说明，人工操作时说明
    private String description;
    // 操作者，关联t_sms_user表中id，操作者为系统组件(smsa-pay或smsp-task)时，填0
    private Long adminId;
    // 更新时间
    private Date updateTime;
    // 支付时间
    private Date payTime;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId ;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId ;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount ;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode ;
    }

    public Integer getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState ;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime ;
    }

    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline ;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description ;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime ;
    }

}