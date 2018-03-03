package com.jsmsframework.finance.dto;

/**
 * 在线支付信息
 * @author yeshiyuan
 * @create 2018/1/3
 */
public class JsmsOnlinePaymentInfoDto {
    //支付订单id
    private String paymentId;
    //支付方式
    private String paymentMode;
    //支付方式名称
    private String paymentModeName;
    //支付状态
    private String paymentState;
    //支付状态名称
    private String paymentStateName;
    //支付金额
    private String paymentAmount;
    //订单支付剩余时间
    private String countDownStr;

    /*--------------支付信息--------------*/
    //商户id
    private String merId;
    //epay回调地址
    private String notifyUrl;

    private String returnUrl;
    //epay地址
    private String payUrl;

    //流水的创建时间
    private String payTime;

    //流水的支付类型
    private String payType;

    private String userId;
    //签名
    private String sign;
    //支付金额
    private String payAmount;


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentModeName() {
        return paymentModeName;
    }

    public void setPaymentModeName(String paymentModeName) {
        this.paymentModeName = paymentModeName;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getPaymentStateName() {
        return paymentStateName;
    }

    public void setPaymentStateName(String paymentStateName) {
        this.paymentStateName = paymentStateName;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getCountDownStr() {
        return countDownStr;
    }

    public void setCountDownStr(String countDownStr) {
        this.countDownStr = countDownStr;
    }
}
