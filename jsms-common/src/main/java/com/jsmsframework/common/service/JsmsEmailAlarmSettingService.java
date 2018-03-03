package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsEmailAlarmSetting;

import java.util.List;
import java.util.Map;

/**
 * @description 邮件提醒设置表
 * @author tanjiangqiang
 * @date 2017-11-30
 */
public interface JsmsEmailAlarmSettingService {

    int insert(JsmsEmailAlarmSetting model);
    
    int insertBatch(List<JsmsEmailAlarmSetting> modelList);

    int update(JsmsEmailAlarmSetting model);
    
    int updateSelective(JsmsEmailAlarmSetting model);

    JsmsEmailAlarmSetting getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);

    int countForEdit(Map<String, Object> params);
}
