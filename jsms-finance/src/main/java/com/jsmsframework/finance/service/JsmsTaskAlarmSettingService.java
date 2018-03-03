package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsTaskAlarmSetting;
import com.jsmsframework.finance.enums.TaskAlarmType;

import java.util.List;
import java.util.Map;

/**
 * @description 提醒设置表
 * @author huangwenjie
 * @date 2017-08-08
 */
public interface JsmsTaskAlarmSettingService {

    public int insert(JsmsTaskAlarmSetting model);
    
    public int insertBatch(List<JsmsTaskAlarmSetting> modelList);

    public int update(JsmsTaskAlarmSetting model);
    
    public int updateSelective(JsmsTaskAlarmSetting model);
    
    public JsmsTaskAlarmSetting getById(Integer id);
    
    public JsmsPage queryList(JsmsPage page);
    
    public int count(Map<String, Object> params);

    public List<JsmsTaskAlarmSetting> getByTaskAlarmType(TaskAlarmType taskAlarmType);
    
}
