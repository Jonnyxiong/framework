package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.entity.JsmsParam;
import com.jsmsframework.common.enums.Code;
import com.jsmsframework.common.enums.PaymentMode;
import com.jsmsframework.common.enums.PaymentState;
import com.jsmsframework.common.mapper.JsmsParamMapper;
import com.jsmsframework.common.util.HttpUtil;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.finance.dto.JsmsOnlinePaymentDTO;
import com.jsmsframework.finance.dto.JsmsOnlinePaymentInfoDto;
import com.jsmsframework.finance.entity.JsmsOnlinePayment;
import com.jsmsframework.finance.entity.JsmsOnlinePaymentLog;
import com.jsmsframework.finance.exception.JsmsOnlinePaymentException;
import com.jsmsframework.finance.mapper.JsmsOnlinePaymentLogMapper;
import com.jsmsframework.finance.mapper.JsmsOnlinePaymentMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 在线支付流水表
 * @author huangwenjie
 * @date 2017-12-29
 */
@Service
public class JsmsOnlinePaymentServiceImpl implements JsmsOnlinePaymentService{

    private final static Logger logger = LoggerFactory.getLogger(JsmsOnlinePaymentServiceImpl.class);

    @Autowired
    private JsmsOnlinePaymentMapper onlinePaymentMapper;

    @Autowired
    private JsmsOnlinePaymentLogMapper onlinePaymentLogMapper;

    @Autowired
    private JsmsParamMapper paramMapper;


    @Override
    public int insert(JsmsOnlinePayment model) {
        return this.onlinePaymentMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsOnlinePayment> modelList) {
        return this.onlinePaymentMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsOnlinePayment model) {
		JsmsOnlinePayment old = this.onlinePaymentMapper.getByPaymentId(model.getPaymentId());
		if(old == null){
			return 0;
		}
		return this.onlinePaymentMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsOnlinePayment model) {
		JsmsOnlinePayment old = this.onlinePaymentMapper.getByPaymentId(model.getPaymentId());
		if(old != null)
        	return this.onlinePaymentMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsOnlinePayment getByPaymentId(String paymentId) {
        JsmsOnlinePayment model = this.onlinePaymentMapper.getByPaymentId(paymentId);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOnlinePayment> list = this.onlinePaymentMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.onlinePaymentMapper.count(params);
    }

    @Override
    public R createOrder(BigDecimal paymentAmount, Integer paymentMode, String paymentId, String agentId, Long adminId,Date createTime) {
        int paymentState = PaymentState.WEI_ZHI_FU.getValue();//未支付
        //生成支付订单
        JsmsOnlinePayment jsmsOnlinePayment = new JsmsOnlinePayment();
        jsmsOnlinePayment.setPaymentId(paymentId);
        jsmsOnlinePayment.setAgentId(Integer.valueOf(agentId));
        jsmsOnlinePayment.setPaymentAmount(paymentAmount);
        jsmsOnlinePayment.setPaymentMode(paymentMode);
        jsmsOnlinePayment.setCreateTime(createTime);
        jsmsOnlinePayment.setPaymentState(paymentState);
        jsmsOnlinePayment.setAdminId(adminId);
        jsmsOnlinePayment.setUpdateTime(createTime);
        //设置过期时间，具体过期时间以参数表的ONLINE_PAYMENT_OVERTIME参数为准
        int submitDeadline = Integer.parseInt(getEffMinute(true));
        Date date = new Date();
        date.setTime(date.getTime()+submitDeadline*60*1000);
        jsmsOnlinePayment.setSubmitDeadline(date);

        logger.info("充值订单信息：{}", JsonUtil.toJson(jsmsOnlinePayment));
        int i = onlinePaymentMapper.insert(jsmsOnlinePayment);
        if (i<1){
            throw new JsmsOnlinePaymentException("创建在线支付订单失败");
        }
        //支付订单流水记录
        insertPaymentLog(paymentId,createTime,paymentState,adminId);
        //返回支付信息
        Map<String,Object> map = new HashMap<>();
        map.put("paymentId",paymentId);
        map.put("paymentAmount",paymentAmount.setScale(2));
        map.put("paymentMode", PaymentMode.getDesc(paymentMode));
        //查询系统设置的支付倒计时有效时间
        Integer effMinute = Integer.valueOf(getEffMinute(true));
        map.put("effMinute", effMinute*60);
        return R.ok("创建在线支付订单成功",map);
    }

    /**
     * 获取支付有效时长
     * @Param getOrderFlag(true:获取订单的有效时长，false:获取支付的有效时长)
     * @return
     */
    public String getEffMinute(boolean getOrderFlag){
        String effMinute = null;
        List<JsmsParam> list = paramMapper.getByParamKey("ONLINE_PAYMENT_OVERTIME");
        if (list!=null && list.size()>0){
            JsmsParam jsmsParam = list.get(0);
            String value = jsmsParam.getParamValue();
            if (getOrderFlag){
                effMinute = value.substring(0,value.indexOf("|")); //订单有效时间
            }else{
                effMinute = value.substring(value.indexOf("|")+1); //支付提交有效时间
            }
        }
        return effMinute;
    }

    @Override
    public JsmsPage queryPayOrder(JsmsPage<JsmsOnlinePaymentDTO> jsmsPage) {
        List<JsmsOnlinePaymentDTO> list = this.onlinePaymentMapper.queryPayOrder(jsmsPage);
        //计算未支付的倒计时
        if (list!=null && list.size()>0){
            String effMinute = getEffMinute(true);
            Long effMinuteLong = Long.valueOf(effMinute)*60 * 1000; //转化为毫秒
            Date now = new Date();
            int i =jsmsPage.getPage()==1 ? 1:(jsmsPage.getPage()-1)*jsmsPage.getRows()+1;
            for (JsmsOnlinePaymentDTO dto: list) {
                Integer paymentState = dto.getPaymentState();
                dto.setRowNum(i++);
                if(paymentState==0){
                    Date createTime = dto.getCreateTime();
                    Long diff  = now.getTime() - createTime.getTime(); //计算当前时间与创建时间差是否大于倒计时长
                    if (diff < effMinuteLong){
                        Long countDown = (effMinuteLong - diff) / 1000; //减去已过的时间
                        dto.setCountDownStr(countDown.toString());
                    }
                }
            }
        }
        jsmsPage.setData(list);
        return jsmsPage;
    }

    @Override
    public String getPaymentIdMostNum(String paymentIdPre) {
        String num = onlinePaymentMapper.getPaymentIdMostNum(paymentIdPre);
        return num;
    }

    @Override
    public R getPaymentInfoAndEpayInfo(String paymentId,String merId,String notifyUrl,String returnUrl,String payUrl,
                                                              String environmentFlag,String epayKey) {
        JsmsOnlinePayment jsmsOnlinePayment = onlinePaymentMapper.getByPaymentId(paymentId);
        if (jsmsOnlinePayment==null){
            throw new JsmsOnlinePaymentException("订单不存在");
        }
        JsmsOnlinePaymentInfoDto paymentInfoDto = new JsmsOnlinePaymentInfoDto();
        //生成显示信息
        Integer paymentMode =jsmsOnlinePayment.getPaymentMode();
        String paymentModeName = PaymentMode.getDesc(paymentMode);
        Integer paymentState = jsmsOnlinePayment.getPaymentState();
        String paymentStateName = PaymentState.getName(paymentState);
        paymentInfoDto.setPaymentId(paymentId);
        paymentInfoDto.setPaymentMode(paymentMode.toString());
        paymentInfoDto.setPaymentModeName(paymentModeName);
        paymentInfoDto.setPaymentAmount(jsmsOnlinePayment.getPaymentAmount().setScale(2).toString());
        paymentInfoDto.setPaymentState(paymentState.toString());
        paymentInfoDto.setPaymentStateName(paymentStateName);
        //生成epay支付信息
        String payTime = new DateTime(jsmsOnlinePayment.getCreateTime()).toString("yyyyMMddHHmmss");
        String payType = null;
        if (paymentMode == PaymentMode.ZHI_FU_BAO.getKey()) {
            payType = "alipay";
        }else if (paymentMode == PaymentMode.WEI_XIN.getKey()) {
            payType = "wechart";
        }
        String userId = "1";
        BigDecimal bgPayAmount = jsmsOnlinePayment.getPaymentAmount();
        bgPayAmount = bgPayAmount.multiply(new BigDecimal("100"));
        String payAmount = bgPayAmount.toString();
        if(environmentFlag != null && !environmentFlag.contains("prod")){
            //测试使用(使用1分钱进行测试)
            payAmount = "1";
        }
        payAmount = StringUtils.subZeroAndDot(payAmount);
        String bankId = "";
        String merData = "";
        String cardId = "";
        String cardPassword = "";
        String cardAmount = "";
        String productId = "";
        String productDesc = "";
        String signDecode = merId + paymentId + payType + bankId + payAmount + payTime + notifyUrl + merData + cardId
                + cardPassword + cardAmount + userId + productId + productDesc + epayKey;

        logger.debug("请求支付信息merId={}", merId);
        logger.debug("请求支付信息paymentId={}", paymentId);
        logger.debug("请求支付信息payType={}", payType);
        logger.debug("请求支付信息bankId={}", bankId);
        logger.debug("请求支付信息payAmount={}", payAmount);

        logger.debug("请求支付信息payTime={}", payTime);
        logger.debug("请求支付信息notifyUrl={}", notifyUrl);
        logger.debug("请求支付信息merData={}", merData);
        logger.debug("请求支付信息cardId={}", cardId);
        logger.debug("请求支付信息cardPassword={}", cardPassword);

        logger.debug("请求支付信息cardAmount={}", cardAmount);
        logger.debug("请求支付信息userId={}", userId);
        logger.debug("请求支付信息productId={}", productId);
        logger.debug("请求支付信息productDesc={}", productDesc);
        logger.debug("请求支付信息epay_key={}", epayKey);

        logger.debug("请求支付信息signDecode={}", signDecode);

        String sign = null;
        try {
            sign = StringUtils.getMD5(signDecode);
            logger.debug("加密支付信息sign={}", sign);
        } catch (NoSuchAlgorithmException e) {
            logger.error("加密支付信息sign报错:",e);
            throw new JsmsOnlinePaymentException("加密支付信息sign出错");
        }
        paymentInfoDto.setMerId(merId);
        paymentInfoDto.setPaymentId(paymentId);
        paymentInfoDto.setNotifyUrl(notifyUrl);
        paymentInfoDto.setReturnUrl(returnUrl);
        paymentInfoDto.setPayUrl(payUrl);
        paymentInfoDto.setPayTime(payTime);
        paymentInfoDto.setPayType(payType);
        paymentInfoDto.setUserId(userId);
        paymentInfoDto.setSign(sign);
        paymentInfoDto.setPayAmount(payAmount);
        //返回订单支付剩余时间
        String effMinute = getEffMinute(true);
        Long effMinuteL = Long.valueOf(effMinute)*60 * 1000; //转化为毫秒
        Date now = new Date();
        Date createTime = jsmsOnlinePayment.getCreateTime();
        Long diff = now.getTime()-createTime.getTime();
        if(diff<effMinuteL){
            Long countDown = (effMinuteL-diff)/1000;
            paymentInfoDto.setCountDownStr(countDown.toString());
        }
        return R.ok("获取订单信息和epay信息成功",paymentInfoDto);
    }

    @Override
    public R updatePaymentStateToSubmit(String paymentId, Integer oldPaymentState, Integer newPaymentState, Date submitTime, String effMinute,Long adminId) {
        R r = R.error(Code.OPT_ERR,"更新订单状态（支付已提交）失败");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("paymentId",paymentId);
        params.put("oldPaymentState",oldPaymentState);
        params.put("newPaymentState",newPaymentState);
        params.put("submitTime",submitTime);
        params.put("effMinute",effMinute);
        int i = onlinePaymentMapper.updatePaymentToSubmit(params);
        if (i>0){
            //支付订单流水记录
            insertPaymentLog(paymentId,submitTime,newPaymentState,adminId);
            r = R.ok("更新订单状态（支付已提交）成功");
        }
        return r;
    }


    @Override
    public R updatePaymentState(String paymentId, Integer oldPaymentState, Integer newPaymentState, Long adminId) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("paymentId",paymentId);
        params.put("oldPaymentState",oldPaymentState);
        params.put("newPaymentState",newPaymentState);
        Date now = new Date();
        params.put("updateTime",now);
        int i = onlinePaymentMapper.updatePaymentState(params);
        R r = R.error(Code.OPT_ERR,"修改支付订单状态失败");
        if (i>0){
            r = R.ok("修改支付订单状态成功");
            //生成支付流水记录
            insertPaymentLog(paymentId,now,newPaymentState,adminId);
        }
        return r;
    }



    @Override
    public R getOnlinePaymentState(String paymentId) {
        R r = null;
        Map<String,Object> map = onlinePaymentMapper.queryPaymentState(paymentId);
        if (map!=null){
            r = R.ok("查询订单状态成功",map);
        }else{
            r = R.error(Code.OPT_ERR,"查询订单状态失败");
        }
        return r;
    }

    @Override
    public R getPaymentAddrForWeChat(String paymentId,String merId,String notifyUrl,String returnUrl,String payUrl,
                                     String environmentFlag,String epayKey,Date now,Long adminId) {
        R r;
        R payResult = this.getPaymentInfoAndEpayInfo(paymentId,merId,notifyUrl,returnUrl,payUrl,environmentFlag,epayKey);
        //获取epay、订单信息
        JsmsOnlinePaymentInfoDto jsmsOnlinePaymentInfoDto = (JsmsOnlinePaymentInfoDto)payResult.getData();
        //把上述数据拼接url参数
        String param = this.toWeChatParam(jsmsOnlinePaymentInfoDto);
        //请求epay，获取微信支付地址
        String answer = "";
        try {
            answer = HttpUtil.httpPost(payUrl,param,false);
        } catch (Exception e) {
            logger.error("请求epay异常,pay_url=" + payUrl, e);
            throw new JsmsOnlinePaymentException("请求epay异常");
        }
        logger.debug("请求epay返回answer={}", answer);
        if (StringUtils.isEmpty(answer)) {
            throw new JsmsOnlinePaymentException("返回的地址为空");
        }
        if(answer.contains("客户不存在")){
            throw new JsmsOnlinePaymentException("merId客户不存在，请重新发送");
        }
        if("签名不符".equals(answer)){
            throw new JsmsOnlinePaymentException("回的地址为签名不符，请重新发送");
        }
        //修改订单流水的状态
        try {
            Integer newPaymentState = PaymentState.SUBMIT.getValue();
            String effMinute = getEffMinute(false); //获取支付有效时间
            r = this.updatePaymentStateToSubmit(paymentId,PaymentState.WEI_ZHI_FU.getValue(),newPaymentState,now,effMinute,adminId);
            if (r.getCode()==0){
                r = R.ok("更新订单状态成功",answer);
            }
        } catch (Exception e) {
            throw new JsmsOnlinePaymentException("更新订单状态失败");
        }
        return r;
    }

    @Override
    public R checkBeforePaySubmit(String paymentId, Date now) {
        R r = null;
        if (StringUtils.isEmpty(paymentId)){
            throw new JsmsOnlinePaymentException("支付订单id为空");
        }else{
            JsmsOnlinePayment jsmsOnlinePayment = onlinePaymentMapper.getByPaymentId(paymentId);
            if (jsmsOnlinePayment==null){
                throw new JsmsOnlinePaymentException("订单不存在");
            }else{
                //校验订单是否已过期
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(jsmsOnlinePayment.getCreateTime());
//                String effMinute = this.getEffMinute(true);
//                calendar.add(Calendar.MINUTE,Integer.valueOf(effMinute));
                Date submitDeadline = jsmsOnlinePayment.getSubmitDeadline();
                if (now.getTime()>submitDeadline.getTime()){
                    throw new JsmsOnlinePaymentException("此订单已过期");
                }else{
                    r = R.ok("校验通过");
                }
            }
        }
        return r;
    }

    /**
     * 生成支付订单操作记录
     * @param paymentId
     * @param createTime
     * @param paymentState
     * @param adminId
     */
    public void insertPaymentLog(String paymentId,Date createTime,Integer paymentState,Long adminId){
        JsmsOnlinePaymentLog jsmsOnlinePaymentLog = new JsmsOnlinePaymentLog();
        jsmsOnlinePaymentLog.setCreateTime(createTime);
        jsmsOnlinePaymentLog.setAdminId(adminId);
        jsmsOnlinePaymentLog.setPaymentState(paymentState);
        jsmsOnlinePaymentLog.setPaymentId(paymentId);
        onlinePaymentLogMapper.insert(jsmsOnlinePaymentLog);
    }


    /**
     * 封装微信支付所需的参数
     * @param  jsmsOnlinePaymentInfoDto
     * @return
     */
    public String toWeChatParam(JsmsOnlinePaymentInfoDto jsmsOnlinePaymentInfoDto){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("merId", jsmsOnlinePaymentInfoDto.getMerId());
        resultMap.put("orderId", jsmsOnlinePaymentInfoDto.getPaymentId());
        resultMap.put("notifyUrl",jsmsOnlinePaymentInfoDto.getNotifyUrl());
        resultMap.put("payTime", jsmsOnlinePaymentInfoDto.getPayTime());
        resultMap.put("payType", jsmsOnlinePaymentInfoDto.getPayType());
        resultMap.put("userId", jsmsOnlinePaymentInfoDto.getUserId());
        resultMap.put("sign", jsmsOnlinePaymentInfoDto.getSign());
        resultMap.put("payAmount", jsmsOnlinePaymentInfoDto.getPayAmount());
        String param = "";
        int i = 0;
        for (Map.Entry<String, Object> map : resultMap.entrySet()) {
            if(i == 0){
                param = map.getKey()+"="+map.getValue();
            }else{
                param = param + "&" + map.getKey()+"="+map.getValue();
            }
            i++;
        }
        logger.debug("微信支付请求参数：{}",param);
        return param;
    }

    @Override
    public JsmsPage queryPayOrder(JsmsPage<JsmsOnlinePaymentDTO> jsmsPage,PaymentState paymentState) {
        if (PaymentState.WEI_ZHI_FU.equals(paymentState)) {//待支付
            jsmsPage.getParams().put("paymentState", 99);
        } else if (PaymentState.CANCEL.equals(paymentState)) {//取消支付
            jsmsPage.getParams().put("paymentState", 98);
        }
        return this.queryPayOrder(jsmsPage);
    }

}
