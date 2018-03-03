package com.jsmsframework.finance.dto;

import com.jsmsframework.common.enums.invoice.InvoiceBodyEnum;
import com.jsmsframework.common.enums.invoice.InvoiceStatusEnum;
import com.jsmsframework.common.enums.invoice.InvoiceTypeEnum;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceList;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Created by xiongfenglin on 2018/1/24.
 *
 * @author: xiongfenglin
 */
public class JsmsAgentInvoiceListDTO extends JsmsAgentInvoiceList{
    //序号
    private Integer rowNum;
    //客户名称
    private String name;
    //归属销售
    private  String belongSaleStr;
    //申请人
    private String applicantStr;
    //审核人
    private String auditorStr;
    //创建时间
    private String createTimeStr;
    //更新时间
    private String updateTimeStr;
    //状态
    private String statusStr;
    //开票主体
    private String invoiceBodyStr;
    //发票类型
    private String invoiceTypeStr;
    //开票项目   固定值  信息服务费
    private String invoiceProject;
    //取消的操作权限标准
    private Integer flag;
    //发票金额的string类型
    private String invoiceAmountStr;
    //快递公司名称String格式
    private String expressCompanyStr;

    public String getExpressCompanyStr() {
        return expressCompanyStr;
    }

    public void setExpressCompanyStr(String expressCompanyStr) {
        this.expressCompanyStr = expressCompanyStr;
    }
    //操作人
    private String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getInvoiceAmountStr() {
        return invoiceAmountStr;
    }

    public void setInvoiceAmountStr(String invoiceAmountStr) {
        this.invoiceAmountStr = invoiceAmountStr;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelongSaleStr() {
        return belongSaleStr;
    }

    public void setBelongSaleStr(String belongSaleStr) {
        this.belongSaleStr = belongSaleStr;
    }

    public String getApplicantStr() {
        return applicantStr;
    }

    public void setApplicantStr(String applicantStr) {
        this.applicantStr = applicantStr;
    }

    public String getAuditorStr() {
        return auditorStr;
    }

    public void setAuditorStr(String auditorStr) {
        this.auditorStr = auditorStr;
    }

    public String getCreateTimeStr() {
        if(getCreateTime()!=null){
            createTimeStr= DateFormatUtils.format(getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        }else {
            createTimeStr="-";
        }
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        if(getUpdateTime()!=null){
            updateTimeStr= DateFormatUtils.format(getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
        }else {
            updateTimeStr="-";
        }
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getStatusStr() {
        return statusStr = InvoiceStatusEnum.getDescByValue(getStatus());
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getInvoiceBodyStr() {
        return invoiceBodyStr = InvoiceBodyEnum.getDescByValue(getInvoiceBody());
    }

    public void setInvoiceBodyStr(String invoiceBodyStr) {
        this.invoiceBodyStr = invoiceBodyStr;
    }

    public String getInvoiceTypeStr() {
        return invoiceTypeStr = InvoiceTypeEnum.getDescByValue(getInvoiceType());
    }

    public void setInvoiceTypeStr(String invoiceTypeStr) {
        this.invoiceTypeStr = invoiceTypeStr;
    }

    public String getInvoiceProject() {
        return invoiceProject;
    }

    public void setInvoiceProject(String invoiceProject) {
        this.invoiceProject = invoiceProject;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
