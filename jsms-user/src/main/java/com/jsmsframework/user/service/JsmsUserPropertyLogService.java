package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsUserPropertyLog;

import java.util.List;
import java.util.Map;

/**
 * @description 用户属性变更记录
 * @author lpjLiu
 * @date 2017-10-11
 */
public interface JsmsUserPropertyLogService {

    int insert(JsmsUserPropertyLog model);
    
    int insertBatch(List<JsmsUserPropertyLog> modelList);

    int update(JsmsUserPropertyLog model);
    
    int updateSelective(JsmsUserPropertyLog model);

    JsmsUserPropertyLog getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    Integer getChargeRuleByClientIdAndDate(String clientId, String date);

    JsmsUserPropertyLog getCanUpdateChargeRuleByClientIdAndEffectDate(String clientId, String date);

    List<JsmsUserPropertyLog> findLastEffectDateList(String property, String value);
}
