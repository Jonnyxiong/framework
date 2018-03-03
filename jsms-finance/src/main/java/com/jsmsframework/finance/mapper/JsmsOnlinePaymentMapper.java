package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.finance.dto.JsmsOnlinePaymentDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsOnlinePayment;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 在线支付流水表
 * @author huangwenjie
 * @date 2017-12-29
 */
@Repository
public interface JsmsOnlinePaymentMapper{

	int insert(JsmsOnlinePayment model);
	
	int insertBatch(List<JsmsOnlinePayment> modelList);

	
	int update(JsmsOnlinePayment model);
	
	int updateSelective(JsmsOnlinePayment model);

	@SimpleCountSQL
    JsmsOnlinePayment getByPaymentId(String paymentId);

	List<JsmsOnlinePayment> queryList(JsmsPage<JsmsOnlinePayment> page);

	int count(Map<String, Object> params);

	/**
	 * 查询订单数据
	 * @param page
	 * @return
	 */
	@SimpleCountSQL
	List<JsmsOnlinePaymentDTO> queryPayOrder(JsmsPage<JsmsOnlinePaymentDTO> page);

	/**
	 * 根据订单id的前缀获取订单id的后面四位数随机数
	 * @param paymentIdPre
	 * @return
	 */
	String getPaymentIdMostNum(@Param("paymentIdPre") String paymentIdPre);


	/**
	 * 修改订单状态为支付已提交
	 * @param params
	 * @return
	 */
	int updatePaymentToSubmit(Map<String,Object> params);

	/**
	 * 修改订单状态
	 * @param params
	 * @return
	 */
	int updatePaymentState(Map<String,Object> params);


	/**
	 * 查询订单状态
	 * @param paymentId
	 * @return
	 */
	Map<String,Object> queryPaymentState(String paymentId);
}