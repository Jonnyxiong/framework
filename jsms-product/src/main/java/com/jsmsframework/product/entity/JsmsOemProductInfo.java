package com.jsmsframework.product.entity;

import java.util.Date;
import java.math.BigDecimal;

/**
 * @description OEM产品信息表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsOemProductInfo {
    
    // 产品id,唯一标识	规则:0-999’999’999
    private Integer productId;
    // 产品代码，唯一
    private String productCode;
    // 产品名称
    private String productName;
    // 产品描述
    private String productDesc;
    // 对应运营商,0:全网,1:移动,2:联通,3:电信,4:国际
    private Integer operatorCode;
    // 产品类型,0:行业,1:营销,2:国际	0和1位普通短信,2为国际短信
    private Integer productType;
    // 适用地区,0:全国,1:国际,2:省网
    private Integer areaCode;
    // 到期时间
    private Date dueTime;
    // 短信单价,单位:元	国际短信单价为空
    private BigDecimal unitPrice;
    // 状态,0:待上架,1:已上架,2:已下架	设置下架则该产品无法购买
    private Integer status;
    // 产品是否对所有代理商可见,0:否,1:是
    private Integer isShow;
    // 创建者
    private String creator;
    // 创建时间
    private Date createTime;
    // 更新者
    private String updator;
    // 更新时间
    private Date updateTime;
    // 备注
    private String remark;
    
    public Integer getProductId() {
        return productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId ;
    }
    
    public String getProductCode() {
        return productCode;
    }
    
    public void setProductCode(String productCode) {
        this.productCode = productCode ;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName ;
    }
    
    public String getProductDesc() {
        return productDesc;
    }
    
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc ;
    }
    
    public Integer getOperatorCode() {
        return operatorCode;
    }
    
    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode ;
    }
    
    public Integer getProductType() {
        return productType;
    }
    
    public void setProductType(Integer productType) {
        this.productType = productType ;
    }
    
    public Integer getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode ;
    }
    
    public Date getDueTime() {
        return dueTime;
    }
    
    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime ;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public Integer getIsShow() {
        return isShow;
    }
    
    public void setIsShow(Integer isShow) {
        this.isShow = isShow ;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }
    
    public String getUpdator() {
        return updator;
    }
    
    public void setUpdator(String updator) {
        this.updator = updator ;
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