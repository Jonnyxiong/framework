package com.jsmsframework.record.record.entity;

import java.lang.reflect.Field;
import java.util.Date;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 短信记录流水表
 * @author huangwenjie
 * @date 2018-01-22
 */
public class JsmsRecord {
    
    // 短信uuid
    private String smsuuid;
    // 通道号
    private Integer channelid;
    // 通道运营商类型，0：全网，1：移动，2：联通，3：电信
    private String channeloperatorstype;
    // 通道备注
    private String channelremark;
    // 发送号码所属运营商类型
    private Integer operatorstype;
    // 地区标识，短信发往地区
    private Integer area;
    // 客户发送短信进入短信系统的标识，一条（长或短）短信的对应一个ID（由ACCESS或REST传过来的ID）
    private String smsid;
    // 平台帐号
    private String sid;
    // 用户名称
    private String username;
    // 短信内容
    private String content;
    // 通道计费条数，针对单条短信记录
    private Integer smscnt;
    // 状态，0：未发送，1：提交成功，2：发送成功，3：明确成功，4：提交失败，5：发送失败，6：明确失败
    private Integer state;
    // 手机号码
    private String phone;
    // 发送时长，单位秒（状态报告时间－提交时间）
    private Integer duration;
    // 备注
    private String remarks;
    // 源IP
    private String ip;
    // 模板类型
    private Integer templatetype;
    // 客户接入使用协议类型，2为SMPP协议，3为CMPP协议，4为SGIP协议，5为SMGP协议，6为HTTPS协议
    private Integer smsfrom;
    // 长短信拆分序号
    private Integer smsindex;
    // 成本(单位：厘)
    private BigDecimal costFee;
    // 卖出价格(单位：厘)
    private BigDecimal saleFee;
    // 通道回复状态报告的msgid标识
    private String channelsmsid;
    // 付费类型，0：预付费，1：后付费
    private Integer paytype;
    // 子账号
    private String clientid;
    // 处理描述
    private String errorcode;
    // smsp_c2s组件接收时间，对应access流水表中的date时间
    private Date date;
    // 提交状态描述
    private String submit;
    // 提交时间(提交运营商时间)
    private Date submitdate;
    // 应答状态描述
    private String subret;
    // 应答时间（运营商应答时间）
    private Date subretdate;
    // 状态报告描述
    private String report;
    // 状态报告时间（运营商状态报告时间）
    private Date reportdate;
    // send接收状态报告时间
    private Date recvreportdate;
    // 0:sid在调度系统中配置过选通道，1：没配置过
    private Integer sidinpaas;
    // 显示号码（通道代码+用户端口+签名端口+自扩展端口）
    private String showphone;
    // 模板id（关联t_sms_template表中template_id字段）
    private Integer templateId;
    // 通道模板编号（关联t_sms_template_audit表中channel_tempid字段）
    private String channelTempid;
    // 
    private String tempParams;
    // smsp_send组件id，记录短信出口的组件
    private Integer sendId;
    // 是否长短信，0：否，1：是
    private Integer longsms;
    // 单个smsid的客户计费总条数，记录客户长短信计费总条数，同一smsid，多条记录值相同（与access流水表对帐用）
    private Integer clientcnt;
    // 单个smsid的通道计费总条数，记录通道长短信计费总条数，同一smsid，多条记录值相同，（与access流水表对帐用）
    private Integer channelcnt;
    // smsp_send组件接收时间
    private Date senddate;
    // 所属销售，关联t_sms_access_0表中belong_sale字段
    private Long belongSale;
    // 归属商务，关联t_sms_user表中id字段
    private Long belongBusiness;
    // 签名（短信内容中的自签名）
    private String sign;
    // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Integer smstype;
    // 失败重发标识0：未进入失败重发流程1：进入失败重发流程
    private Integer failedResendFlag;
    // 失败重发次数
    private Integer failedResendTimes;
    
    public String getSmsuuid() {
        return smsuuid;
    }
    
    public void setSmsuuid(String smsuuid) {
        this.smsuuid = smsuuid ;
    }
    
    public Integer getChannelid() {
        return channelid;
    }
    
    public void setChannelid(Integer channelid) {
        this.channelid = channelid ;
    }
    
    public String getChanneloperatorstype() {
        return channeloperatorstype;
    }
    
    public void setChanneloperatorstype(String channeloperatorstype) {
        this.channeloperatorstype = channeloperatorstype ;
    }
    
    public String getChannelremark() {
        return channelremark;
    }
    
    public void setChannelremark(String channelremark) {
        this.channelremark = channelremark ;
    }
    
    public Integer getOperatorstype() {
        return operatorstype;
    }
    
    public void setOperatorstype(Integer operatorstype) {
        this.operatorstype = operatorstype ;
    }
    
    public Integer getArea() {
        return area;
    }
    
    public void setArea(Integer area) {
        this.area = area ;
    }
    
    public String getSmsid() {
        return smsid;
    }
    
    public void setSmsid(String smsid) {
        this.smsid = smsid ;
    }
    
    public String getSid() {
        return sid;
    }
    
    public void setSid(String sid) {
        this.sid = sid ;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username ;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content ;
    }
    
    public Integer getSmscnt() {
        return smscnt;
    }
    
    public void setSmscnt(Integer smscnt) {
        this.smscnt = smscnt ;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state ;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone ;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration ;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks ;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip ;
    }
    
    public Integer getTemplatetype() {
        return templatetype;
    }
    
    public void setTemplatetype(Integer templatetype) {
        this.templatetype = templatetype ;
    }
    
    public Integer getSmsfrom() {
        return smsfrom;
    }
    
    public void setSmsfrom(Integer smsfrom) {
        this.smsfrom = smsfrom ;
    }
    
    public Integer getSmsindex() {
        return smsindex;
    }
    
    public void setSmsindex(Integer smsindex) {
        this.smsindex = smsindex ;
    }
    
    public BigDecimal getCostFee() {
        return costFee;
    }
    
    public void setCostFee(BigDecimal costFee) {
        this.costFee = costFee ;
    }
    
    public BigDecimal getSaleFee() {
        return saleFee;
    }
    
    public void setSaleFee(BigDecimal saleFee) {
        this.saleFee = saleFee ;
    }
    
    public String getChannelsmsid() {
        return channelsmsid;
    }
    
    public void setChannelsmsid(String channelsmsid) {
        this.channelsmsid = channelsmsid ;
    }
    
    public Integer getPaytype() {
        return paytype;
    }
    
    public void setPaytype(Integer paytype) {
        this.paytype = paytype ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public String getErrorcode() {
        return errorcode;
    }
    
    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode ;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date ;
    }
    
    public String getSubmit() {
        return submit;
    }
    
    public void setSubmit(String submit) {
        this.submit = submit ;
    }
    
    public Date getSubmitdate() {
        return submitdate;
    }
    
    public void setSubmitdate(Date submitdate) {
        this.submitdate = submitdate ;
    }
    
    public String getSubret() {
        return subret;
    }
    
    public void setSubret(String subret) {
        this.subret = subret ;
    }
    
    public Date getSubretdate() {
        return subretdate;
    }
    
    public void setSubretdate(Date subretdate) {
        this.subretdate = subretdate ;
    }
    
    public String getReport() {
        return report;
    }
    
    public void setReport(String report) {
        this.report = report ;
    }
    
    public Date getReportdate() {
        return reportdate;
    }
    
    public void setReportdate(Date reportdate) {
        this.reportdate = reportdate ;
    }
    
    public Date getRecvreportdate() {
        return recvreportdate;
    }
    
    public void setRecvreportdate(Date recvreportdate) {
        this.recvreportdate = recvreportdate ;
    }
    
    public Integer getSidinpaas() {
        return sidinpaas;
    }
    
    public void setSidinpaas(Integer sidinpaas) {
        this.sidinpaas = sidinpaas ;
    }
    
    public String getShowphone() {
        return showphone;
    }
    
    public void setShowphone(String showphone) {
        this.showphone = showphone ;
    }
    
    public Integer getTemplateId() {
        return templateId;
    }
    
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId ;
    }
    
    public String getChannelTempid() {
        return channelTempid;
    }
    
    public void setChannelTempid(String channelTempid) {
        this.channelTempid = channelTempid ;
    }
    
    public String getTempParams() {
        return tempParams;
    }
    
    public void setTempParams(String tempParams) {
        this.tempParams = tempParams ;
    }
    
    public Integer getSendId() {
        return sendId;
    }
    
    public void setSendId(Integer sendId) {
        this.sendId = sendId ;
    }
    
    public Integer getLongsms() {
        return longsms;
    }
    
    public void setLongsms(Integer longsms) {
        this.longsms = longsms ;
    }
    
    public Integer getClientcnt() {
        return clientcnt;
    }
    
    public void setClientcnt(Integer clientcnt) {
        this.clientcnt = clientcnt ;
    }
    
    public Integer getChannelcnt() {
        return channelcnt;
    }
    
    public void setChannelcnt(Integer channelcnt) {
        this.channelcnt = channelcnt ;
    }
    
    public Date getSenddate() {
        return senddate;
    }
    
    public void setSenddate(Date senddate) {
        this.senddate = senddate ;
    }
    
    public Long getBelongSale() {
        return belongSale;
    }
    
    public void setBelongSale(Long belongSale) {
        this.belongSale = belongSale ;
    }
    
    public Long getBelongBusiness() {
        return belongBusiness;
    }
    
    public void setBelongBusiness(Long belongBusiness) {
        this.belongBusiness = belongBusiness ;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign ;
    }
    
    public Integer getSmstype() {
        return smstype;
    }
    
    public void setSmstype(Integer smstype) {
        this.smstype = smstype ;
    }
    
    public Integer getFailedResendFlag() {
        return failedResendFlag;
    }
    
    public void setFailedResendFlag(Integer failedResendFlag) {
        this.failedResendFlag = failedResendFlag ;
    }
    
    public Integer getFailedResendTimes() {
        return failedResendTimes;
    }
    
    public void setFailedResendTimes(Integer failedResendTimes) {
        this.failedResendTimes = failedResendTimes ;
    }


    
}