package com.jsmsframework.finance.dto;

import com.jsmsframework.common.enums.BusinessType;
import com.jsmsframework.common.enums.FinancialType;
import com.jsmsframework.common.enums.ObjectType;
import com.jsmsframework.finance.entity.JsmsSaleCreditBill;


import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Created by Don on 2017/10/26.
 */
public class JsmsSaleCreditBillDTO extends JsmsSaleCreditBill {

    private String businessTypeName;

    private String financialTypeName;

    private  String objectName;

    private String objectTypeName;

    private String adminName;
    //页面账单金额
    private String billamount;

    private String createTimeStr;

    public String getBusinessTypeName() {
        //我的授信,流水显示
        if(Objects.equals(getBusinessType(), BusinessType.财务给销售授信.getValue())){
            businessTypeName="财务给我授信";
        }else if(Objects.equals(getBusinessType(),BusinessType.财务降低销售授信.getValue())){
            businessTypeName="财务降低我授信";
        }else if(Objects.equals(getBusinessType(),BusinessType.销售给客户授信.getValue())){
            businessTypeName="我给客户授信";
        }else  if(Objects.equals(getBusinessType(),BusinessType.销售降低客户授信.getValue())){
            businessTypeName="我降低客户授信";
      }else if(Objects.equals(getBusinessType(),BusinessType.客户回款.getValue())){
            businessTypeName=BusinessType.客户回款.getDesc();
       }else {
            return businessTypeName;
        }
    //    businessTypeName= BusinessType.getDescByValue(getBusinessType());
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getFinancialTypeName() {
        financialTypeName= FinancialType.getDescByValue(getFinancialType());
//        if(Objects.equals(getFinancialType(), FinancialType.入账.getValue())){
//            financialTypeName=FinancialType.入账.getDesc();
//        }else if(Objects.equals(getFinancialType(),FinancialType.出账.getValue())){
//            financialTypeName=FinancialType.出账.getDesc();
//        }else {
//            return financialTypeName;
//        }
        return financialTypeName;
    }

    public void setFinancialTypeName(String financialTypeName) {
        this.financialTypeName = financialTypeName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectTypeName() {

        objectTypeName= ObjectType.getDescByValue(getObjectType());
//        if(Objects.equals(getObjectType(), ObjectType.销售.getValue())) {
//            objectTypeName=ObjectType.销售.getDesc();
//        }else if(Objects.equals(getObjectType(),ObjectType.代理商.getValue())){
//            objectTypeName=ObjectType.代理商.getDesc();
//        }else {
//            return objectTypeName;
//        }

        return objectTypeName;
    }

    public void setObjectTypeName(String objectTypeName) {
        this.objectTypeName = objectTypeName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getBillamount() {
        if(Objects.equals(getFinancialType(), FinancialType.入账.getValue())){
            billamount="+"+getAmount();
        }else if(Objects.equals(getFinancialType(),FinancialType.出账.getValue())){
            billamount="-"+getAmount();
        }else {
            return billamount;
        }

        return billamount;
    }

    public void setBillamount(String billamount) {
        this.billamount = billamount;
    }

    public String getCreateTimeStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCreateTime());
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
