package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsClientBalanceAlarm;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 预付费客户余额预警信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsClientBalanceAlarmService {

    int insert(JsmsClientBalanceAlarm model);
    
    int insertBatch(List<JsmsClientBalanceAlarm> modelList);

    int update(JsmsClientBalanceAlarm model);
    
    int updateSelective(JsmsClientBalanceAlarm model);

	JsmsClientBalanceAlarm getById(Integer id);

	JsmsPage queryList(JsmsPage page);

	List<JsmsClientBalanceAlarm> findList(JsmsClientBalanceAlarm model);

	int count(Map<String, Object> params);

    int isAlarm(Map<String, Object> params);

	List<JsmsClientBalanceAlarm> findListAlarm(JsmsClientBalanceAlarm params, Set clientIds);
}
