package com.jsmsframework.sms.send.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.sms.send.entity.JsmsTimerSendPhones;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @description 定时短信发送任务关联号码表-原表
 * @author Don
 * @date 2018-01-04
 */
@Repository
public interface JsmsTimerSendPhonesMapper {

	int insert(JsmsTimerSendPhones model);
	
	int insertBatch(List<JsmsTimerSendPhones> modelList);

	
	int update(JsmsTimerSendPhones model);
	
	int updateSelective(JsmsTimerSendPhones model);

    JsmsTimerSendPhones getById(Integer id);

	@SimpleCountSQL
	List<JsmsTimerSendPhones> queryList(JsmsPage<JsmsTimerSendPhones> page);

	List<JsmsTimerSendPhones> findList(Map params);

	int count(Map<String, Object> params);

	String getAllPhone(@Param("tableDate") Integer tableDate,@Param("taskId") String taskId);

	List<String> getAllPhoneOfList(@Param("tableDate") Integer tableDate,@Param("taskId") String taskId);

}