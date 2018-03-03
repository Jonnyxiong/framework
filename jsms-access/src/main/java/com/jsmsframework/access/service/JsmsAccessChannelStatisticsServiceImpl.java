package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsAccessChannelStatistics;
import com.jsmsframework.access.access.mapper.JsmsAccessChannelStatisticsMapper;
import com.jsmsframework.common.dto.JsmsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 客户发送统计表
 * @author huangwenjie
 * @date 2017-10-16
 */
@Service
public class JsmsAccessChannelStatisticsServiceImpl implements JsmsAccessChannelStatisticsService{

    @Autowired
    private JsmsAccessChannelStatisticsMapper accessChannelStatisticsMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAccessChannelStatistics model) {
        return this.accessChannelStatisticsMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAccessChannelStatistics> modelList) {
        return this.accessChannelStatisticsMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAccessChannelStatistics model) {
		JsmsAccessChannelStatistics old = this.accessChannelStatisticsMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.accessChannelStatisticsMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAccessChannelStatistics model) {
		JsmsAccessChannelStatistics old = this.accessChannelStatisticsMapper.getById(model.getId());
		if(old != null)
        	return this.accessChannelStatisticsMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAccessChannelStatistics getById(Long id) {
        JsmsAccessChannelStatistics model = this.accessChannelStatisticsMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAccessChannelStatistics> list = this.accessChannelStatisticsMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.accessChannelStatisticsMapper.count(params);
    }
    
}
