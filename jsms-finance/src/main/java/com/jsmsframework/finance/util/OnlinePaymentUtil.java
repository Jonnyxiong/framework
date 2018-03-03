package com.jsmsframework.finance.util;

import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.finance.exception.JsmsOnlinePaymentException;

import java.util.Date;

/**
 * 在线支付工具类
 * @author yeshiyuan
 * @create 2018/1/2
 */
public class OnlinePaymentUtil {

    public static int PAYMENTID_NUM = -1;

    private static int MAX_NUM = 9999;

    public static String PAYMENTID_PRE = "";
    /**
     * 生成在线支付订单id
     * @param now
     * @param webId
     * @return
     */
    public static String getPaymentId(Date now, String webId,String systemId) throws JsmsOnlinePaymentException{
        if (now==null){
            throw new JsmsOnlinePaymentException("生成在线支付id出错：时间参数为空");
        }
        if (StringUtils.isEmpty(webId)){
            throw new JsmsOnlinePaymentException("生成在线支付id出错：webId为空");
        }
        if (StringUtils.isEmpty(systemId)){
            throw new JsmsOnlinePaymentException("生成在线支付id出错：系统id为空");
        }
        if (PAYMENTID_NUM==-1){
            throw new JsmsOnlinePaymentException("生成在线支付id出错：支付id的后四位随机数未初始化（请在项目启动时进行初始化" +
                    "【封装id前缀，调用JsmsOnlinePaymentService.getPaymentIdMostNum方法查看当前随机数，为空则设0，不为空则+1】）");
        }
        StringBuffer paymentId = new StringBuffer();
        paymentId.append("Z")
                .append(webId)
                .append(systemId)
                .append(DateUtil.dateToStr(now,"yyMMddHHmm"));
        if(PAYMENTID_PRE.equals(paymentId.toString())){
            if (PAYMENTID_NUM==MAX_NUM){
                throw new JsmsOnlinePaymentException("一分钟内生成订单号随机数超过9999");
            }else{
                PAYMENTID_NUM++;
            }
        }else{
            PAYMENTID_NUM = 0;
            PAYMENTID_PRE = paymentId.toString();
        }
        paymentId.append(StringUtils.addZeroForNum(PAYMENTID_NUM, 4, "0"));
        return paymentId.toString();
    }



}
