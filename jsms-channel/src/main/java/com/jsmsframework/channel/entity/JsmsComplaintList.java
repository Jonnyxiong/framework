package com.jsmsframework.channel.entity;

import java.util.Date;

/**
 * @description 投诉明细记录表
 * @author huangwenjie
 * @date 2018-01-09
 */
public class JsmsComplaintList {
    
    // 序号ID，自增长，主键
    private Integer id;
    // 通道号，关联t_sms_channel表中cid字段
    private Integer channelId;
    // 客户ID，关联t_sms_account表中clientid字段,投诉记录类型type为通道投诉的时候可为空
    private String clientId;
    // 通道运营商类型0：全网1：移动2：联通3：电信4：国际,投诉记录类型type为通道投诉的时候可为空
    private Integer operatorstype;
    // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信,投诉记录类型type为通道投诉的时候可为空
    private Integer smstype;
    // 发送日期，投诉记录类型type为通道投诉的时候可为空
    private Date sendTime;
    // 手机号码
    private String phone;
    // 签名（短信内容中的自签名）
    private String sign;
    // 短信内容
    private String content;
    // 归属商务，关联t_sms_user表中id字段
    private Long belongBusiness;
    // 归属销售，关联t_sms_user表中id字段
    private Long belongSale;
    // 操作者, 对应t_sms_user表中id字段
    private Long operator;
    // 状态，0：正常，1：删除
    private Integer status;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    // 备注
    private String remark;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getChannelId() {
        return channelId;
    }
    
    public void setChannelId(Integer channelId) {
        this.channelId = channelId ;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId ;
    }
    
    public Integer getOperatorstype() {
        return operatorstype;
    }
    
    public void setOperatorstype(Integer operatorstype) {
        this.operatorstype = operatorstype ;
    }
    
    public Integer getSmstype() {
        return smstype;
    }
    
    public void setSmstype(Integer smstype) {
        this.smstype = smstype ;
    }
    
    public Date getSendTime() {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime ;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone ;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign ;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content ;
    }
    
    public Long getBelongBusiness() {
        return belongBusiness;
    }
    
    public void setBelongBusiness(Long belongBusiness) {
        this.belongBusiness = belongBusiness ;
    }
    
    public Long getBelongSale() {
        return belongSale;
    }
    
    public void setBelongSale(Long belongSale) {
        this.belongSale = belongSale ;
    }
    
    public Long getOperator() {
        return operator;
    }
    
    public void setOperator(Long operator) {
        this.operator = operator ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}