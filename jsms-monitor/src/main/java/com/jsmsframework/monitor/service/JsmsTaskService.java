package com.jsmsframework.monitor.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.monitor.entity.JsmsTask;

import java.util.List;
import java.util.Map;

/**
 * @description 定时任务表
 * @author Dylan
 * @date 2018-01-02
 */
public interface JsmsTaskService {

    int insert(JsmsTask model);
    
    int insertBatch(List<JsmsTask> modelList);

    int update(JsmsTask model);
    
    int updateSelective(JsmsTask model);

    JsmsTask getByTaskId(Integer taskId);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsTask> findList(Map params);

    int count(Map<String,Object> params);
    
}
