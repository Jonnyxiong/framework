package com.jsmsframework.finance.entity;

import java.util.Date;

/**
 * @description 在线支付流水日志表
 * @author huangwenjie
 * @date 2017-12-29
 */
public class JsmsOnlinePaymentLog {
    
    // 主键自增
    private Integer id;
    // 支付id，关联t_sms_online_payment表中payment_id字段
    private String paymentId;
    // 支付状态，0：未支付，1：支付已提交，2：支付成功，3：支付失败（超时未支付），4：支付取消，5：超时支付，6：支付异常
    private Integer paymentState;
    // 创建时间
    private Date createTime;
    // 备注，系统操作时备注
    private String remark;
    // 操作者，关联t_sms_user表中id，操作者为系统组件(smsa-pay或smsp-task)时，填0
    private Long adminId;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId ;
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
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }
    
}