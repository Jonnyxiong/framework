package com.jsmsframework.user.entity;

import java.math.BigDecimal;

/**
 * Created by xiongfenglin on 2017/11/23.
 *
 * @author: xiongfenglin
 */
public class JsmsUserFinance {
    // 公司/个人名称
    private String company;
    // 登入密码
    private String password;
    // 确认密码
    private String confirmPassword;
    // 邀请码
    private String inviteCode;
    // 登入邮件
    private String email;
    // 手机号码
    private String mobile;
    // 联系人
    private String linkman;
    //省
    private String province;
    //区/县
    private String area;
    // 市
    private String city;
    //详细地址
    private String address;
    // 归属销售，关联t_sms_user表中id字段
    private Long belongSale;
    // 备注
    private String remark;
    // 申请状态,0:待受理,1:受理不通过,2:已受理
    private Integer status;
    //默认OEM域名
    private String domainName;
    //
    private String copyright;
    //导航栏背景色
    private String navBackcolor;
    //导航栏文字颜色
    private String navTextColor;
    //oem代理商站点地址
    private String oemAgentDomainName;
    //用户id
    private Long userId;
    // 行业短信折扣率
    private BigDecimal hySmsDiscount;
    // 测试短信产品id号，关联t_sms_oem_product_info表中product_id字段
    private Integer testProductId;
    // 赠送的测试短信条数
    private Integer testSmsNumber;

    public BigDecimal getHySmsDiscount() {
        return hySmsDiscount;
    }
    public void setHySmsDiscount(BigDecimal hySmsDiscount) {
        this.hySmsDiscount = hySmsDiscount;
    }

    public Integer getTestProductId() {
        return testProductId;
    }

    public void setTestProductId(Integer testProductId) {
        this.testProductId = testProductId;
    }

    public Integer getTestSmsNumber() {
        return testSmsNumber;
    }

    public void setTestSmsNumber(Integer testSmsNumber) {
        this.testSmsNumber = testSmsNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOemAgentDomainName() {
        return oemAgentDomainName;
    }

    public void setOemAgentDomainName(String oemAgentDomainName) {
        this.oemAgentDomainName = oemAgentDomainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getNavBackcolor() {
        return navBackcolor;
    }

    public void setNavBackcolor(String navBackcolor) {
        this.navBackcolor = navBackcolor;
    }

    public String getNavTextColor() {
        return navTextColor;
    }

    public void setNavTextColor(String navTextColor) {
        this.navTextColor = navTextColor;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBelongSale() {
        return belongSale;
    }

    public void setBelongSale(Long belongSale) {
        this.belongSale = belongSale;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
