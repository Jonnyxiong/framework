package com.jsmsframework.sms.send.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.sms.send.entity.JsmsTimerSendTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;



/**
 * @description 定时短信发送任务信息表
 * @author Don
 * @date 2018-01-04
 */
@Repository
public interface JsmsTimerSendTaskMapper{

	int insert(JsmsTimerSendTask model);
	
	int insertBatch(List<JsmsTimerSendTask> modelList);

	
	int update(JsmsTimerSendTask model);
	
	int updateSelective(JsmsTimerSendTask model);

	int updateIdempotent(@Param("oldModel") JsmsTimerSendTask oldModel, @Param("newModel") JsmsTimerSendTask newModel);

    JsmsTimerSendTask getByTaskId(String taskId);

	@SimpleCountSQL
	List<JsmsTimerSendTask> queryList(JsmsPage<JsmsTimerSendTask> page);

	List<JsmsTimerSendTask> findList(Map params);

	int count(Map<String, Object> params);

	@SimpleCountSQL
	List<JsmsTimerSendTask> queryPageList(JsmsPage<JsmsTimerSendTask> page);

	/**
	 * 修改组件id
	 * @param jsmsTimerSendTask
	 * @return
	 */
	int updateComponentId(JsmsTimerSendTask jsmsTimerSendTask);
}