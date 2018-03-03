package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsTaskAlarmSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 提醒设置表
 * @author huangwenjie
 * @date 2017-08-08
 */
@Repository
public interface JsmsTaskAlarmSettingMapper {

	int insert(JsmsTaskAlarmSetting model);
	
	int insertBatch(List<JsmsTaskAlarmSetting> modelList);

	int update(JsmsTaskAlarmSetting model);

	int updateSelective(JsmsTaskAlarmSetting model);
	
	JsmsTaskAlarmSetting getById(Integer id);
	
	List<JsmsTaskAlarmSetting> queryList(JsmsPage<JsmsTaskAlarmSetting> page);
	
	int count(Map<String, Object> params);

	int updateTaskExecutePeriod(@Param("executePeriod") String executePeriod, @Param("taskType") String taskType);
}