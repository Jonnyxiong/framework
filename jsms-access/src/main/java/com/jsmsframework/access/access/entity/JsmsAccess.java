package com.jsmsframework.access.access.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description Access流水表
 * @author huangwenjie
 * @date 2017-10-20
 */
public class JsmsAccess {
    
    // 主键ID（uuid）
    private String id;
    // 短信内容
    private String content;
    // 来源号码（扩展端口）
    private String srcphone;
    // 目标手机号码
    private String phone;
    // 单个smsid的客户计费总条数;记录客户长短信计费总条数，同一smsid，多条记录值相同（与record流水表对帐用）
    private Integer smscnt;
    // 客户短信拆分序号
    private Integer smsindex;
    // 签名（短信内容中的自签名）
    private String sign;
    // 显示自签名的方式，1：前置，2：后置
    private Integer showsigntype;
    // 短信状态报告回调给客户的标识
    private String submitid;
    // 客户发送短信进入短信系统的标识，一条（长或短）短信的对应一个ID
    private String smsid;
    // 用户帐号（子帐号）
    private String clientid;
    // 用户名称
    private String username;
    // 平台帐号
    private String sid;
    // 运营商类型
    private Integer operatorstype;
    // 客户接入使用协议类型: 2为SMPP协议，3为CMPP协议，4为SGIP协议，5为SMGP协议，6为HTTPS协议
    private Integer smsfrom;
    // 通道号
    private Integer channelid;
    // 状态，0：未发送，1：提交成功，3：明确成功，4：提交失败，5：提交审核模块失败，6：明确失败，7：审核不通过，8：网关接收拦截，9：网关发送拦截，10：超频拦截
    private Integer state;
    // 处理描述
    private String errorcode;
    // 处理时间（客户提交时间或入库时间）
    private Date date;
    // 提交状态描述
    private String submit;
    // 提交时间（提交send时间）
    private Date submitdate;
    // 应答状态描述
    private String subret;
    // 应答时间（send应答时间）
    private Date subretdate;
    // 状态报告描述
    private String report;
    // 状态报告时间（运营商状态报告时间）
    private Date reportdate;
    // 用户透传ID，随状态报告返回给客户
    private String uid;
    // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Integer smstype;
    // 通道成本单价(单位：厘),普通短信：值 = 发送通道成本价,国际短信：值 = 前缀对应国际通道费率
    private BigDecimal costfee;
    // 产品销售单价(单位：厘)针对客户1.	代理商平台普通短信：值 = sub_id对应产品总销售价/总数量国际短信：值 = 前缀对应客户国际费率*sub_id对应产品总销售价2.	云平台普通短信：值 = sub_id对应云通道单价国际短信：值 = 前缀对应客户国际费率
    private BigDecimal salefee;
    // 产品成本单价(单位：厘)针对代理商1.	代理商平台普通短信：值 = sub_id对应产品总成本价/总数量国际短信：值 = 前缀对应客户费率*sub_id对应产品总成本价2.	云平台填空值
    private BigDecimal productfee;
    // 唯一id，订单子id 1.	代理商平台品牌代理和销售代理对应t_sms_client_order表中sub_id字段OEM代理对应t_sms_oem_client_pool表中client_pool_id字段2.	云平台对应t_sms_cloud_user_order表中的order_id字段
    private String subId;
    // 产品类型，0：行业，1：营销，2：国际，7：USSD，8：闪信，9：挂机短信
    private Integer productType;
    // 计费条数，针对单条短信记录
    private Integer chargeNum;
    // 付费类型，0：预付费，1：后付费
    private Integer paytype;
    // 代理商id
    private Integer agentId;
    // 用户是否超频计费，0：不需要，1：需要
    private Integer isoverratecharge;
    // 通道运营商类型，0：全网，1：移动，2：联通，3：电信
    private Integer channeloperatorstype;
    // 通道备注
    private String channelremark;
    // 是否进行过异常处理，0：否，1：是
    private Integer isReback;
    // 模板id（关联t_sms_template表中template_id字段）
    private Integer templateId;
    // 通道模板编号（关联t_sms_template_audit表中channel_tempid字段）
    private String channelTempid;
    // 模板中对应的参数值，多个参数值以英文分号间隔，参数值顺序与模板中变量顺序对应，参数值个数必须与模板中变量个数一致，格式为“参数值;参数值;参数值”的方式，例如  888888;123456
    private String tempParams;
    // smsp_c2s组件id，记录短信入口组件
    private Integer c2sId;
    // 短信处理次数，默认为1，如果是异常处理，根据处理次数进行累加
    private Integer processTimes;
    // 是否长短信，0：否，1：是
    private Integer longsms;
    // 单个smsid的通道计费总条数;记录通道长短信计费总条数，同一smsid，多条记录值相同,更新状态报告时同时更新（与record流水表对帐用）
    private Integer channelcnt;
    // 所属销售，关联t_sms_account表中belong_sale字段
    private Long belongSale;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id ;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content ;
    }
    
    public String getSrcphone() {
        return srcphone;
    }
    
    public void setSrcphone(String srcphone) {
        this.srcphone = srcphone ;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone ;
    }
    
    public Integer getSmscnt() {
        return smscnt;
    }
    
    public void setSmscnt(Integer smscnt) {
        this.smscnt = smscnt ;
    }
    
    public Integer getSmsindex() {
        return smsindex;
    }
    
    public void setSmsindex(Integer smsindex) {
        this.smsindex = smsindex ;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign ;
    }
    
    public Integer getShowsigntype() {
        return showsigntype;
    }
    
    public void setShowsigntype(Integer showsigntype) {
        this.showsigntype = showsigntype ;
    }
    
    public String getSubmitid() {
        return submitid;
    }
    
    public void setSubmitid(String submitid) {
        this.submitid = submitid ;
    }
    
    public String getSmsid() {
        return smsid;
    }
    
    public void setSmsid(String smsid) {
        this.smsid = smsid ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username ;
    }
    
    public String getSid() {
        return sid;
    }
    
    public void setSid(String sid) {
        this.sid = sid ;
    }
    
    public Integer getOperatorstype() {
        return operatorstype;
    }
    
    public void setOperatorstype(Integer operatorstype) {
        this.operatorstype = operatorstype ;
    }
    
    public Integer getSmsfrom() {
        return smsfrom;
    }
    
    public void setSmsfrom(Integer smsfrom) {
        this.smsfrom = smsfrom ;
    }
    
    public Integer getChannelid() {
        return channelid;
    }
    
    public void setChannelid(Integer channelid) {
        this.channelid = channelid ;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state ;
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
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid ;
    }
    
    public Integer getSmstype() {
        return smstype;
    }
    
    public void setSmstype(Integer smstype) {
        this.smstype = smstype ;
    }
    
    public BigDecimal getCostfee() {
        return costfee;
    }
    
    public void setCostfee(BigDecimal costfee) {
        this.costfee = costfee ;
    }
    
    public BigDecimal getSalefee() {
        return salefee;
    }
    
    public void setSalefee(BigDecimal salefee) {
        this.salefee = salefee ;
    }
    
    public BigDecimal getProductfee() {
        return productfee;
    }
    
    public void setProductfee(BigDecimal productfee) {
        this.productfee = productfee ;
    }
    
    public String getSubId() {
        return subId;
    }
    
    public void setSubId(String subId) {
        this.subId = subId ;
    }
    
    public Integer getProductType() {
        return productType;
    }
    
    public void setProductType(Integer productType) {
        this.productType = productType ;
    }
    
    public Integer getChargeNum() {
        return chargeNum;
    }
    
    public void setChargeNum(Integer chargeNum) {
        this.chargeNum = chargeNum ;
    }
    
    public Integer getPaytype() {
        return paytype;
    }
    
    public void setPaytype(Integer paytype) {
        this.paytype = paytype ;
    }
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public Integer getIsoverratecharge() {
        return isoverratecharge;
    }
    
    public void setIsoverratecharge(Integer isoverratecharge) {
        this.isoverratecharge = isoverratecharge ;
    }
    
    public Integer getChanneloperatorstype() {
        return channeloperatorstype;
    }
    
    public void setChanneloperatorstype(Integer channeloperatorstype) {
        this.channeloperatorstype = channeloperatorstype ;
    }
    
    public String getChannelremark() {
        return channelremark;
    }
    
    public void setChannelremark(String channelremark) {
        this.channelremark = channelremark ;
    }
    
    public Integer getIsReback() {
        return isReback;
    }
    
    public void setIsReback(Integer isReback) {
        this.isReback = isReback ;
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
    
    public Integer getC2sId() {
        return c2sId;
    }
    
    public void setC2sId(Integer c2sId) {
        this.c2sId = c2sId ;
    }
    
    public Integer getProcessTimes() {
        return processTimes;
    }
    
    public void setProcessTimes(Integer processTimes) {
        this.processTimes = processTimes ;
    }
    
    public Integer getLongsms() {
        return longsms;
    }
    
    public void setLongsms(Integer longsms) {
        this.longsms = longsms ;
    }
    
    public Integer getChannelcnt() {
        return channelcnt;
    }
    
    public void setChannelcnt(Integer channelcnt) {
        this.channelcnt = channelcnt ;
    }
    
    public Long getBelongSale() {
        return belongSale;
    }
    
    public void setBelongSale(Long belongSale) {
        this.belongSale = belongSale ;
    }
    
}