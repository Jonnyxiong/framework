package com.jsmsframework.monitor.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.monitor.entity.JsmsTask;
import com.jsmsframework.monitor.mapper.JsmsTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description 定时任务表
 * @author Dylan
 * @date 2018-01-02
 */
@Service
public class JsmsTaskServiceImpl implements JsmsTaskService{
    private static final Logger logger = LoggerFactory.getLogger(JsmsTaskServiceImpl.class);

    @Autowired
    private JsmsTaskMapper taskMapper;
    
    @Override
    public int insert(JsmsTask model) {
        return this.taskMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsTask> modelList) {
        return this.taskMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsTask model) {
		JsmsTask old = this.taskMapper.getByTaskId(model.getTaskId());
		if(old == null){
			return 0;
		}
		return this.taskMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsTask model) {
		JsmsTask old = this.taskMapper.getByTaskId(model.getTaskId());
		if(old != null)
        	return this.taskMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsTask getByTaskId(Integer taskId) {
        JsmsTask model = this.taskMapper.getByTaskId(taskId);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsTask> list = this.taskMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsTask> findList(Map params) {
        List<JsmsTask> list = this.taskMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.taskMapper.count(params);
    }
    
}
