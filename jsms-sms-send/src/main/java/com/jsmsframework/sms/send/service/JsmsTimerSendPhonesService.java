package com.jsmsframework.sms.send.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.sms.send.entity.JsmsTimerSendPhones;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description 定时短信发送任务关联号码表-原表
 * @author Don
 * @date 2018-01-04
 */
public interface JsmsTimerSendPhonesService {

    int insert(JsmsTimerSendPhones model);
    
    int insertBatch(List<JsmsTimerSendPhones> modelList);

    int update(JsmsTimerSendPhones model);
    
    int updateSelective(JsmsTimerSendPhones model);

    JsmsTimerSendPhones getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsTimerSendPhones> findList(Map params);

    int count(Map<String, Object> params);

    /**
     * 根据提交创建时间+taskID查询，该任务下所有手机号
     * 以逗号分隔字符串返回
     * @param submitTime
     * @param taskId
     * @return
     */
    @Deprecated
    String getAllPhone(Date submitTime, String taskId);
    /**
     * 根据提交创建时间+taskID查询，该任务下所有手机号
     * list返回
     * @param submitTime
     * @param taskId
     * @return
     */
    List<String> getAllPhoneOfList(Date submitTime, String taskId);

}
