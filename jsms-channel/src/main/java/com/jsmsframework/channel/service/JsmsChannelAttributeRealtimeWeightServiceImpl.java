package com.jsmsframework.channel.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.channel.mapper.JsmsChannelAttributeRealtimeWeightMapper;
import com.jsmsframework.channel.entity.JsmsChannelAttributeRealtimeWeight;
import com.jsmsframework.channel.service.JsmsChannelAttributeRealtimeWeightService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道属性实时权重表
 * @author huangwenjie
 * @date 2017-09-28
 */
@Service
public class JsmsChannelAttributeRealtimeWeightServiceImpl implements JsmsChannelAttributeRealtimeWeightService{

    @Autowired
    private JsmsChannelAttributeRealtimeWeightMapper channelAttributeRealtimeWeightMapper;
    
    @Override
	@Transactional
    public int insert(JsmsChannelAttributeRealtimeWeight model) {
        return this.channelAttributeRealtimeWeightMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsChannelAttributeRealtimeWeight> modelList) {
        return this.channelAttributeRealtimeWeightMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsChannelAttributeRealtimeWeight model) {
		JsmsChannelAttributeRealtimeWeight old = this.channelAttributeRealtimeWeightMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.channelAttributeRealtimeWeightMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsChannelAttributeRealtimeWeight model) {
		JsmsChannelAttributeRealtimeWeight old = this.channelAttributeRealtimeWeightMapper.getById(model.getId());
		if(old != null)
        	return this.channelAttributeRealtimeWeightMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsChannelAttributeRealtimeWeight getById(Long id) {
        JsmsChannelAttributeRealtimeWeight model = this.channelAttributeRealtimeWeightMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsChannelAttributeRealtimeWeight> list = this.channelAttributeRealtimeWeightMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.channelAttributeRealtimeWeightMapper.count(params);
    }

    @Override
    public JsmsChannelAttributeRealtimeWeight getByChannelId(Integer channelid) {
        return this.channelAttributeRealtimeWeightMapper.getByChannelId(channelid);
    }

    @Override
    public int updateWeightByChannelId(JsmsChannelAttributeRealtimeWeight model) {
        return this.channelAttributeRealtimeWeightMapper.updateWeightByChannelId(model);
    }

    @Override
    public List<JsmsChannelAttributeRealtimeWeight> queryAll() {
        return this.channelAttributeRealtimeWeightMapper.queryAll();
    }

}
