package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 代理商申请表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsAgentApply {
    
    // 主键,自增
    private Integer id;
    // 公司名称
    private String company;
    // 联系地址/公司地址
    private String address;
    // 真实姓名
    private String realname;
    // 邮件
    private String email;
    // 手机
    private String mobile;
    // 所在城市
    private String city;
    // 归属销售，关联t_sms_user表中id字段
    private Long belongSale;
    // 备注
    private String remark;
    // 申请状态,0:待受理,1:受理不通过,2:已受理
    private Integer status;
    // 原因
    private String reason;
    // 提交时间
    private Date createTime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company ;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address ;
    }
    
    public String getRealname() {
        return realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname ;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email ;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile ;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city ;
    }
    
    public Long getBelongSale() {
        return belongSale;
    }
    
    public void setBelongSale(Long belongSale) {
        this.belongSale = belongSale ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
}