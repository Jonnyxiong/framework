package com.jsmsframework.channel.entity;

import java.util.Date;

/**
 * @description 白关键字强制路由通道表
 * @author huangwenjie
 * @date 2017-09-20
 */
public class JsmsWhiteKeywordChannel {
    
    // 序号ID,自增长
    private Integer id;
    // 签名
    private String sign;
    // 白关键字
    private String whiteKeyword;
    // 通道组对应的运营商类型0：全网1：移动2：联通3：电信4：国际
    private Integer operatorstype;
    // 发送类型，0：行业，1：营销
    private Integer sendType;
    // 通道组ID，此时通道组里的通道最好是自签平台用户端口类型，如果是其它类型通道，需要给用户分配相应的扩展端口
    private Integer channelId;
    // 客户代码，关联t_sms_segment_client表中client_code字段，为*时表示此路由规则适用于所有用户
    private String clientCode;
    // 状态，0：关闭，1：开启
    private Integer status;
    // 备注
    private String remarks;
    // 更新时间
    private Date updateTime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign ;
    }
    
    public String getWhiteKeyword() {
        return whiteKeyword;
    }
    
    public void setWhiteKeyword(String whiteKeyword) {
        this.whiteKeyword = whiteKeyword ;
    }
    
    public Integer getOperatorstype() {
        return operatorstype;
    }
    
    public void setOperatorstype(Integer operatorstype) {
        this.operatorstype = operatorstype ;
    }
    
    public Integer getSendType() {
        return sendType;
    }
    
    public void setSendType(Integer sendType) {
        this.sendType = sendType ;
    }
    
    public Integer getChannelId() {
        return channelId;
    }
    
    public void setChannelId(Integer channelId) {
        this.channelId = channelId ;
    }
    
    public String getClientCode() {
        return clientCode;
    }
    
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks ;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }
    
}