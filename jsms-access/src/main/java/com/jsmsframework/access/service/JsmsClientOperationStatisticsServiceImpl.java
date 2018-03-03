package com.jsmsframework.access.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.access.access.entity.JsmsClientOperationStatistics;
import com.jsmsframework.access.access.mapper.JsmsClientOperationStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 客户运营统计表
 * @author huangwenjie
 * @date 2018-01-09
 */
@Service
public class JsmsClientOperationStatisticsServiceImpl implements JsmsClientOperationStatisticsService{

    @Autowired
    private JsmsClientOperationStatisticsMapper clientOperationStatisticsMapper;
    
    @Override
    public int insert(JsmsClientOperationStatistics model) {
        return this.clientOperationStatisticsMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsClientOperationStatistics> modelList) {
        return this.clientOperationStatisticsMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsClientOperationStatistics model) {
		JsmsClientOperationStatistics old = this.clientOperationStatisticsMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.clientOperationStatisticsMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsClientOperationStatistics model) {
		JsmsClientOperationStatistics old = this.clientOperationStatisticsMapper.getById(model.getId());
		if(old != null)
        	return this.clientOperationStatisticsMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsClientOperationStatistics getById(Integer id) {
        JsmsClientOperationStatistics model = this.clientOperationStatisticsMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientOperationStatistics> list = this.clientOperationStatisticsMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsClientOperationStatistics> findList(Map params) {
        List<JsmsClientOperationStatistics> list = this.clientOperationStatisticsMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.clientOperationStatisticsMapper.count(params);
    }
    
}
