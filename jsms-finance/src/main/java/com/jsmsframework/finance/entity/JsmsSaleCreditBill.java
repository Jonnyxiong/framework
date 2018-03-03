package com.jsmsframework.finance.entity;

import com.jsmsframework.common.util.DateUtil;
import com.ucpaas.sms.common.util.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 销售授信收支明细
 * @author huangwenjie
 * @date 2017-10-25
 */
public class JsmsSaleCreditBill {
    
    // 业务单号，自增序列id
    private Integer id;
    // 销售id，关联t_sms_user表中id字段
    private Long saleId;
    // 业务类型，0：财务给销售授信，1：财务降低销售授信，2：销售给客户授信，3：销售降低客户授信，4：客户回款
    private Integer businessType;
    // 财务类型，0：入账，1：出账
    private String financialType;
    // 金额，单位：元
    private BigDecimal amount;
    // 授信对象ID，object_type=0时，关联t_sms_user表中id字段，object_type=1时，关联t_sms_agent_info表中agent_id字段
    private Long objectId;
    // 授信对象类型，0：销售，1：代理商
    private Integer objectType;
    // 财务历史授信额度，单位：元，执行授信操作时间点对应值
    private BigDecimal financialHistoryCredit;
    // 授信余额，单位：元，执行授信操作时间点对应值
    private BigDecimal currentCredit;
    // 销售历史授出额度，单位：元，执行授信操作时间点对应值
    private BigDecimal saleHistoryCredit;
    // 客户历史回款，单位：元，执行授信操作时间点对应值
    private BigDecimal agentHistoryPayment;
    // 未回款额度，单位：元，执行授信操作时间点对应值
    private BigDecimal noBackPayment;
    // 操作者id，关联t_sms_user表中id字段
    private Long adminId;
    // 创建时间
    private Date createTime;
    // 备注
    private String remark;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Long getSaleId() {
        return saleId;
    }
    
    public void setSaleId(Long saleId) {
        this.saleId = saleId ;
    }
    
    public Integer getBusinessType() {
        return businessType;
    }
    
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType ;
    }
    
    public String getFinancialType() {
        return financialType;
    }
    
    public void setFinancialType(String financialType) {
        this.financialType = financialType ;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount ;
    }
    
    public Long getObjectId() {
        return objectId;
    }
    
    public void setObjectId(Long objectId) {
        this.objectId = objectId ;
    }
    
    public Integer getObjectType() {
        return objectType;
    }
    
    public void setObjectType(Integer objectType) {
        this.objectType = objectType ;
    }
    
    public BigDecimal getFinancialHistoryCredit() {
        return financialHistoryCredit;
    }
    
    public void setFinancialHistoryCredit(BigDecimal financialHistoryCredit) {
        this.financialHistoryCredit = financialHistoryCredit ;
    }
    
    public BigDecimal getCurrentCredit() {
        return currentCredit;
    }
    
    public void setCurrentCredit(BigDecimal currentCredit) {
        this.currentCredit = currentCredit ;
    }
    
    public BigDecimal getSaleHistoryCredit() {
        return saleHistoryCredit;
    }
    
    public void setSaleHistoryCredit(BigDecimal saleHistoryCredit) {
        this.saleHistoryCredit = saleHistoryCredit ;
    }
    
    public BigDecimal getAgentHistoryPayment() {
        return agentHistoryPayment;
    }
    
    public void setAgentHistoryPayment(BigDecimal agentHistoryPayment) {
        this.agentHistoryPayment = agentHistoryPayment ;
    }
    
    public BigDecimal getNoBackPayment() {
        return noBackPayment;
    }
    
    public void setNoBackPayment(BigDecimal noBackPayment) {
        this.noBackPayment = noBackPayment ;
    }
    
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId ;
    }
    
    public Date getCreateTime() {
        return createTime;
      //  "yyyy-MM-dd HH:mm:ss";
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
    
}