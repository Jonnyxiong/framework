package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentBalanceAlarm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商余额预警信息表
 * @author huangwenjie
 * @date 2017-08-08
 */
@Repository
public interface JsmsAgentBalanceAlarmMapper {

	int insert(JsmsAgentBalanceAlarm model);
	
	int insertBatch(List<JsmsAgentBalanceAlarm> modelList);

	int update(JsmsAgentBalanceAlarm model);
	
	int updateSelective(JsmsAgentBalanceAlarm model);

	int updateByAgentId(JsmsAgentBalanceAlarm model);

	JsmsAgentBalanceAlarm getById(Integer id);

	JsmsAgentBalanceAlarm getByAgentId(Integer agentId);

	List<JsmsAgentBalanceAlarm> queryList(JsmsPage<JsmsAgentBalanceAlarm> page);

	/**
	 * @description 查询需要告警的记录 (reminder_number > 0 && alarm_amount > 0)
	 * @param params
	 * @return
	 */
	List<JsmsAgentBalanceAlarm> queryNeedAlarmList(Map params);

	List<JsmsAgentBalanceAlarm> findList(@Param("model") JsmsAgentBalanceAlarm model, @Param("agentIds") Set agentIds);

	int count(Map<String, Object> params);

}