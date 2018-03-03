package com.jsmsframework.product.dto;

import com.jsmsframework.common.enums.AreaCodeEnum;
import com.jsmsframework.common.enums.OperatorType;
import com.jsmsframework.common.enums.ProductType;
import com.jsmsframework.product.entity.JsmsProductInfo;

import java.math.BigDecimal;

public class JsmsProductInfoDTO extends JsmsProductInfo {

    String productTypeName;

    String operatorCodeName;


    String areaCodeName;

    String activePeriodName;

    String quantityStr;
    String productPriceStr;
    String totalPriceStr;

    public String getProductTypeName() {
        if(productTypeName==null){
            if(ProductType.行业.getValue().equals(getProductType())){
                productTypeName = ProductType.行业.getDesc();
            }

            if(ProductType.营销.getValue().equals(getProductType())){
                productTypeName = ProductType.营销.getDesc();
            }
            if(ProductType.国际.getValue().equals(getProductType())){
                productTypeName = ProductType.国际.getDesc();
            }
            if(ProductType.通知.getValue().equals(getProductType())){
                productTypeName = ProductType.通知.getDesc();
            }
            if(ProductType.验证码.getValue().equals(getProductType())){
                productTypeName = ProductType.验证码.getDesc();
            }
            if(ProductType.USSD.getValue().equals(getProductType())){
                productTypeName = ProductType.USSD.getDesc();
            }
            if(ProductType.闪信.getValue().equals(getProductType())){
                productTypeName = ProductType.闪信.getDesc();
            }
            if(ProductType.挂机短信.getValue().equals(getProductType())){
                productTypeName = ProductType.挂机短信.getDesc();
            }
        }

        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getOperatorCodeName() {
        if(operatorCodeName==null){
            if(OperatorType.全网.getValue().equals(getOperatorCode())){
                operatorCodeName = OperatorType.全网.getDesc();
            }
            if(OperatorType.移动.getValue().equals(getOperatorCode())){
                operatorCodeName = OperatorType.移动.getDesc();
            }
            if(OperatorType.电信.getValue().equals(getOperatorCode())){
                operatorCodeName = OperatorType.电信.getDesc();
            }
            if(OperatorType.联通.getValue().equals(getOperatorCode())){
                operatorCodeName = OperatorType.联通.getDesc();
            }
            if(OperatorType.国际.getValue().equals(getOperatorCode())){
                operatorCodeName = OperatorType.国际.getDesc();
            }
        }
        return operatorCodeName;
    }

    public void setOperatorCodeName(String operatorCodeName) {
        this.operatorCodeName = operatorCodeName;
    }

    public String getAreaCodeName() {
        if(areaCodeName==null){
            if(AreaCodeEnum.全国.getValue().equals(getAreaCode())){
                areaCodeName = AreaCodeEnum.全国.getDesc();
            }
            if(AreaCodeEnum.国际.getValue().equals(getAreaCode())){
                areaCodeName = AreaCodeEnum.国际.getDesc();
            }
        }
        return areaCodeName;
    }

    public void setAreaCodeName(String areaCodeName) {
        this.areaCodeName = areaCodeName;
    }

    public String getActivePeriodName() {
        if(activePeriodName==null){
            if(getActivePeriod().equals(0)){
                activePeriodName = "无限期";
            }else{
                activePeriodName = getActivePeriod()+"天";
            }
        }
        return activePeriodName;
    }

    public void setActivePeriodName(String activePeriodName) {
        this.activePeriodName = activePeriodName;
    }

    public String getQuantityStr() {
        if(quantityStr==null){

            if(ProductType.国际.getValue().equals(getProductType())){
                quantityStr = getQuantity().setScale(4,BigDecimal.ROUND_HALF_UP)+"元";
            }else{
                quantityStr = getQuantity().setScale(0,BigDecimal.ROUND_HALF_UP)+"条";
            }
        }
        return quantityStr;
    }

    public void setQuantityStr(String quantityStr) {
        this.quantityStr = quantityStr;
    }

    public String getProductPriceStr() {
        if(productPriceStr==null){

            if(ProductType.国际.getValue().equals(getProductType())){
                productPriceStr = getProductPrice().setScale(4,BigDecimal.ROUND_HALF_UP)+"元";
            }else{
                productPriceStr = getProductPrice().setScale(4,BigDecimal.ROUND_HALF_UP)+"元";
            }
        }
        return productPriceStr;
    }

    public void setProductPriceStr(String productPriceStr) {
        this.productPriceStr = productPriceStr;
    }


    public String getTotalPriceStr() {
        if(totalPriceStr==null){
            BigDecimal totalPrice = null;
            totalPrice = getProductPrice();
//            if(ProductType.国际.getValue().equals(getProductType())){
//                totalPrice = getQuantity();
//            }else{
//                totalPrice = getProductPrice();
//            }
            totalPriceStr = totalPrice.setScale(4,BigDecimal.ROUND_HALF_UP).toString();
        }

        return totalPriceStr;
    }

    public void setTotalPriceStr(String totalPriceStr) {
        this.totalPriceStr = totalPriceStr;
    }
}
