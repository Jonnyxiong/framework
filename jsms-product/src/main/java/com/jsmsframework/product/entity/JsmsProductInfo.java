package com.jsmsframework.product.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 产品信息表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsProductInfo {
    
    // 产品id，唯一标识,规则：0－9999
    private Integer productId;
    // 产品代码，唯一
    private String productCode;
    // 产品名称
    private String productName;
    // 产品描述
    private String productDesc;
    // 对应运营商，0：全网，1：移动，2：联通，3：电信，4：国际
    private Integer operatorCode;
    // 产品类型，0：行业，1：营销，2：国际，7：USSD，8：闪信，9：挂机短信，其中0和1为普通短信，2为国际短信
    private Integer productType;
    // 适用区域，0：全国 1：国际
    private Integer areaCode;
    // 有效期，单位为天，0为无限期
    private Integer activePeriod;
    // 数量，普通短信：条，国际短信：元
    private BigDecimal quantity;
    // 产品定价，针对客户,普通短信：元，国际短信：1
    private BigDecimal productPrice;
    // 产品成本，针对代理商,普通短信：元，国际短信：国际费率折扣率
    private BigDecimal productCost;
    // 状态，0：待上架，1：已上架，2：已下架,设置下架则该产品无法购买
    private Integer status;
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
    
    public Integer getActivePeriod() {
        return activePeriod;
    }
    
    public void setActivePeriod(Integer activePeriod) {
        this.activePeriod = activePeriod ;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity ;
    }
    
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice ;
    }
    
    public BigDecimal getProductCost() {
        return productCost;
    }
    
    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
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