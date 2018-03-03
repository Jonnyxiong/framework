package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.PaymentState;
import com.jsmsframework.finance.dto.JsmsOnlinePaymentDTO;
import com.jsmsframework.finance.entity.JsmsOnlinePayment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description 在线支付流水表
 * @author huangwenjie
 * @date 2017-12-29
 */
public interface JsmsOnlinePaymentService {

    int insert(JsmsOnlinePayment model);
    
    int insertBatch(List<JsmsOnlinePayment> modelList);

    int update(JsmsOnlinePayment model);
    
    int updateSelective(JsmsOnlinePayment model);

    JsmsOnlinePayment getByPaymentId(String paymentId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);

    /**
     * 创建支付订单（1、生成支付订单；2、生成流水记录；3、返回结果）
     * @param paymentAmount 支付金额
     * @param paymentMode 支付方式
     * @param paymentId 订单id
     * @param agentId
     * @param adminId
     * @return
     */
    public R createOrder(BigDecimal paymentAmount, Integer paymentMode, String paymentId, String agentId, Long adminId, Date createTime);


    /**
     * 获取支付有效时长
     * @Param getOrderFlag(true:获取订单的有效时长，false:获取支付的有效时长)
     * @return
     */
    public String getEffMinute(boolean getOrderFlag);



    /**
     * 支付订单列表数据查询
     * @param jsmsPage
     * @return
     */
    JsmsPage queryPayOrder(JsmsPage<JsmsOnlinePaymentDTO> jsmsPage);


    /**
     * 根据订单id的前缀获取订单id的后面四位数随机数
     * @param paymentIdPre
     * @return
     */
    String getPaymentIdMostNum(String paymentIdPre);


    /**
     * 获取支付订单信息（订单的基本信息、对应epay账号、url等）
     * @param paymentId 订单id
     * @param merId epay账号
     * @param notifyUrl epay回调地址
     * @param returnUrl 页面重定向url
     * @param payUrl 调用epay接口
     * @param environmentFlag 系统运行环境
     * @param epayKey epay的密钥
     * @return
     */
    R getPaymentInfoAndEpayInfo(String paymentId,String merId,String notifyUrl,String returnUrl,String payUrl,
                                                       String environmentFlag,String epayKey);


    /**
     * 修改订单状态为支付已提交
     * @param paymentId
     * @param oldPaymentState
     * @param newPaymentState
     * @param submitTime
     * @param effMinute
     * @param adminId
     * @return
     */
    R updatePaymentStateToSubmit(String paymentId, Integer oldPaymentState, Integer newPaymentState, Date submitTime, String effMinute,Long adminId);

    /**
     * 修改订单状态
     * @param paymentId
     * @param oldPaymentState
     * @param newPaymentState
     * @param adminId
     * @return
     */
    R updatePaymentState(String paymentId, Integer oldPaymentState, Integer newPaymentState,Long adminId);

    /**
     * 获取支付状态
     * @param paymentId
     * @return
     */
    R getOnlinePaymentState(String paymentId);

    /**
     * 获取微信支付地址
     * @param paymentId 订单id
     * @param merId epay账号
     * @param notifyUrl epay回调地址
     * @param returnUrl 页面重定向url
     * @param payUrl 调用epay接口
     * @param environmentFlag 系统运行环境
     * @param epayKey epay的密钥
     * @param now
     * @return
     */
    R getPaymentAddrForWeChat(String paymentId,String merId,String notifyUrl,String returnUrl,String payUrl,
                              String environmentFlag,String epayKey,Date now,Long adminId);


    /**
     * 立即支付前的校验（1、订单是否存在；2、订单是否已过期）
     * @param paymentId
     * @param now
     * @return
     */
    R checkBeforePaySubmit(String paymentId,Date now);


    /**
     * * 支付订单列表数据查询
     * 1:查询状态为待支付的订单时,条件为:状态=待支付 且 支付提交截止时间大于当前时间
     * 2:查询状态为取消状态的订单时,条件为:状态=取消 或者 状态=未支付且  支付提交截止时间小于当前时间
     * @param jsmsPage
     * @param paymentState
     * @return
     */
    JsmsPage queryPayOrder(JsmsPage<JsmsOnlinePaymentDTO> jsmsPage,PaymentState paymentState);
}
