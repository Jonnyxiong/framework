package com.jsmsframework.record.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.record.record.entity.JsmsChannelOperationStatistics;
import com.jsmsframework.record.record.mapper.JsmsChannelOperationStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description 通道运营统计表
 * @author huangwenjie
 * @date 2018-01-09
 */
@Service
public class JsmsChannelOperationStatisticsServiceImpl implements JsmsChannelOperationStatisticsService{

    @Autowired
    private JsmsChannelOperationStatisticsMapper channelOperationStatisticsMapper;
    
    @Override
    public int insert(JsmsChannelOperationStatistics model) {
        return this.channelOperationStatisticsMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsChannelOperationStatistics> modelList) {
        return this.channelOperationStatisticsMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsChannelOperationStatistics model) {
		JsmsChannelOperationStatistics old = this.channelOperationStatisticsMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.channelOperationStatisticsMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsChannelOperationStatistics model) {
		JsmsChannelOperationStatistics old = this.channelOperationStatisticsMapper.getById(model.getId());
		if(old != null)
        	return this.channelOperationStatisticsMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsChannelOperationStatistics getById(Integer id) {
        JsmsChannelOperationStatistics model = this.channelOperationStatisticsMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsChannelOperationStatistics> list = this.channelOperationStatisticsMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsChannelOperationStatistics> findList(Map params) {
        List<JsmsChannelOperationStatistics> list = this.channelOperationStatisticsMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.channelOperationStatisticsMapper.count(params);
    }

}
