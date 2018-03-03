package com.jsmsframework.finance.dto;

import com.jsmsframework.common.enums.PaymentMode;
import com.jsmsframework.common.enums.PaymentState;
import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.finance.entity.JsmsOnlinePayment;

/**
 * @author yeshiyuan
 * @create 2018/1/3
 */
public class JsmsOnlinePaymentDTO extends JsmsOnlinePayment{
    //序号
    private Integer rowNum;
    // 支付金额，单位：元
    private String paymentAmountStr;
    // 支付方式，0：支付宝，1：微信支付
    private String paymentModeStr;
    // 支付状态，0：未支付，1：支付已提交，2：支付成功，3：支付失败（超时未支付），4：支付取消，5：超时支付，6：支付异常
    private String paymentStateStr;
    // 创建时间
    private String createTimeStr;
    // 支付提交时间
    private String submitTimeStr;
    //倒计时
    private String countDownStr;
    // 操作者，关联t_sms_user表中id，操作者为系统组件(smsa-pay或smsp-task)时，填0
    private String adminIdStr;
    // 更新时间
    private String updateTimeStr;
    //支付时间
    private String payTimeStr;


    public String getPaymentAmountStr() {
        if (getPaymentAmount()!=null){
            paymentAmountStr = getPaymentAmount().setScale(4).toString();
        }
        return paymentAmountStr;
    }

    public void setPaymentAmountStr(String paymentAmountStr) {
        this.paymentAmountStr = paymentAmountStr;
    }

    public String getPaymentModeStr() {
        if(getPaymentMode() != null){
            paymentModeStr = PaymentMode.getDesc(getPaymentMode());
        }
        return paymentModeStr;
    }

    public void setPaymentModeStr(String paymentModeStr) {
        this.paymentModeStr = paymentModeStr;
    }

    public String getPaymentStateStr() {
        if(getPaymentState() != null){
            paymentStateStr = PaymentState.getName(getPaymentState());
        }
        return paymentStateStr;
    }

    public void setPaymentStateStr(String paymentStateStr) {
        this.paymentStateStr = paymentStateStr;
    }

    public String getCreateTimeStr() {
        if (getCreateTime()!=null){
            createTimeStr = DateUtil.dateToStr(getCreateTime(),"yyyy-MM-dd HH:mm:ss");
        }
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getSubmitTimeStr() {
        if (getSubmitTime()!=null){
            submitTimeStr = DateUtil.dateToStr(getSubmitTime(),"yyyy-MM-dd HH:mm:ss");
        }
        return submitTimeStr;
    }

    public void setSubmitTimeStr(String submitTimeStr) {
        this.submitTimeStr = submitTimeStr;
    }

    public String getCountDownStr() {
        return countDownStr;
    }

    public void setCountDownStr(String countDownStr) {
        this.countDownStr = countDownStr;
    }

    public String getAdminIdStr() {
        return adminIdStr;
    }

    public void setAdminIdStr(String adminIdStr) {
        this.adminIdStr = adminIdStr;
    }

    public String getUpdateTimeStr() {
        if (getUpdateTime()!=null){
            updateTimeStr = DateUtil.dateToStr(getUpdateTime(),"yyyy-MM-dd HH:mm:ss");
        }
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getPayTimeStr() {
        if (getPayTime()!=null){
            payTimeStr = DateUtil.dateToStr(getPayTime(),"yyyy-MM-dd HH:mm:ss");
        }
        return payTimeStr;
    }

    public void setPayTimeStr(String payTimeStr) {
        this.payTimeStr = payTimeStr;
    }
}
