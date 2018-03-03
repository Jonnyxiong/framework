package com.jsmsframework.finance.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsOnlinePaymentLog;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 在线支付流水日志表
 * @author huangwenjie
 * @date 2017-12-29
 */
@Repository
public interface JsmsOnlinePaymentLogMapper{

	int insert(JsmsOnlinePaymentLog model);
	
	int insertBatch(List<JsmsOnlinePaymentLog> modelList);

	
	int update(JsmsOnlinePaymentLog model);
	
	int updateSelective(JsmsOnlinePaymentLog model);

    JsmsOnlinePaymentLog getById(Integer id);

	List<JsmsOnlinePaymentLog> queryList(JsmsPage<JsmsOnlinePaymentLog> page);

	int count(Map<String, Object> params);

}