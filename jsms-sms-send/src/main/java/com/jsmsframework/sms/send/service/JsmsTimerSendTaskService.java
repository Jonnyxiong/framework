package com.jsmsframework.sms.send.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.sms.send.entity.JsmsTimerSendTask;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;



/**
 * @description 定时短信发送任务信息表
 * @author Don
 * @date 2018-01-04
 */
public interface JsmsTimerSendTaskService {

    int insert(JsmsTimerSendTask model);
    
    int insertBatch(List<JsmsTimerSendTask> modelList);

    int update(JsmsTimerSendTask model);
    
    int updateSelective(JsmsTimerSendTask model);

    JsmsTimerSendTask getByTaskId(String taskId);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsTimerSendTask> findList(Map params);

    int count(Map<String, Object> params);


    /**
     * 校验是否可以修改定时任务
     * @param taskId
     * @return
     */
    R checkCanChange(String taskId);

    /**
     * 用户中心查询定时短信
     * @param page
     * @return
     */
    JsmsPage queryPageList(JsmsPage page,WebId webId,String id);

    /**
     * 定时短信取消发送
     * @param taskId 定时短信任务id
     * @param dateTime 当前时间, 取应用服务器时间
     * @param creatorId 创建者id, submittype = 0 传 client_id ; submittype = 1 传 agent_id
     * @return
     */
    ResultVO cancelSendTask(String taskId, DateTime dateTime,String creatorId);


    /**
     * 修改组件id（1、状态校验；2、发送前5分钟校验）
     * @param taskId
     * @param newComponentId
     * @return
     */
    R updateComponentId(String taskId,String newComponentId);
}
