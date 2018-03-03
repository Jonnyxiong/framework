package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsClientBalanceAlarm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 预付费客户余额预警信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsClientBalanceAlarmMapper{

	int insert(JsmsClientBalanceAlarm model);
	
	int insertBatch(List<JsmsClientBalanceAlarm> modelList);

	
	int update(JsmsClientBalanceAlarm model);
	
	int updateSelective(JsmsClientBalanceAlarm model);

    JsmsClientBalanceAlarm getById(Integer id);

	List<JsmsClientBalanceAlarm> queryList(JsmsPage<JsmsClientBalanceAlarm> page);

	List<JsmsClientBalanceAlarm> findList(JsmsClientBalanceAlarm model);

	int count(Map<String, Object> params);
	

	int isAlarm(Map<String, Object> params);

	List<JsmsClientBalanceAlarm> findListAlarm(@Param("params") JsmsClientBalanceAlarm params, @Param("clientIds") Set clientIds);
}