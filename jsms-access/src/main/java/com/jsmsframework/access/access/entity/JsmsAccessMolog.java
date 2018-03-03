package com.jsmsframework.access.access.entity;

import java.util.Date;

/**
 * @description 客户上行明细表
 * @author huangwenjie
 * @date 2017-11-28
 */
public class JsmsAccessMolog {
    
    // 上行ID
    private String moid;
    // 通道ID
    private Integer channelid;
    // 上行接收时间
    private Date receivedate;
    // 手机号码
    private String phone;
    // 目的号码（通道代码+扩展端口）
    private String tophone;
    // 短信内容
    private String content;
    // smsp send上行短信ID
    private String sendmoid;
    // 对应到的客户ID
    private String clientid;
    // 已返回给客户端的客户ID（以此字段来确认是否已上行给客户）
    private String userid;
    
    public String getMoid() {
        return moid;
    }
    
    public void setMoid(String moid) {
        this.moid = moid ;
    }
    
    public Integer getChannelid() {
        return channelid;
    }
    
    public void setChannelid(Integer channelid) {
        this.channelid = channelid ;
    }
    
    public Date getReceivedate() {
        return receivedate;
    }
    
    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate ;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone ;
    }
    
    public String getTophone() {
        return tophone;
    }
    
    public void setTophone(String tophone) {
        this.tophone = tophone ;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content ;
    }
    
    public String getSendmoid() {
        return sendmoid;
    }
    
    public void setSendmoid(String sendmoid) {
        this.sendmoid = sendmoid ;
    }
    
    public String getClientid() {
        return clientid;
    }
    
    public void setClientid(String clientid) {
        this.clientid = clientid ;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid ;
    }
    
}