package com.jsmsframework.monitor.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.monitor.entity.JsmsTask;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 定时任务表
 * @author huangwenjie
 * @date 2018-01-02
 */
@Repository
public interface JsmsTaskMapper{

	int insert(JsmsTask model);
	
	int insertBatch(List<JsmsTask> modelList);

	
	int update(JsmsTask model);
	
	int updateSelective(JsmsTask model);

    JsmsTask getByTaskId(Integer taskId);

	List<JsmsTask> queryList(JsmsPage<JsmsTask> page);

	List<JsmsTask> findList(Map params);

	int count(Map<String,Object> params);

}