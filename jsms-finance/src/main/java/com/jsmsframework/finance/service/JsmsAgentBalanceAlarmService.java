package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentBalanceAlarm;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商余额预警信息表
 * @author huangwenjie
 * @date 2017-08-08
 */
public interface JsmsAgentBalanceAlarmService {

    public int insert(JsmsAgentBalanceAlarm model);
    
    public int insertBatch(List<JsmsAgentBalanceAlarm> modelList);

    public int update(JsmsAgentBalanceAlarm model);
    
    public int updateSelective(JsmsAgentBalanceAlarm model);

    public int updateByAgentId(JsmsAgentBalanceAlarm model);

    public JsmsAgentBalanceAlarm getById(Integer id);

    public JsmsPage queryList(JsmsPage page);
    /**
     * @description 查询需要告警的记录 (reminder_number > 0 && alarm_amount > 0)
     * @param params
     * @return
     */
    public List queryNeedAlarmList(Map params);

    List<JsmsAgentBalanceAlarm> findList(JsmsAgentBalanceAlarm model, Set agentIds);

    public int count(Map<String, Object> params);

    int insertOrUpdate(JsmsAgentBalanceAlarm agentBalanceAlarm);

    public JsmsAgentBalanceAlarm getByAgentId(Integer agentId);


}
