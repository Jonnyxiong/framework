package com.jsmsframework.channel.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.channel.mapper.JsmsChannelgroupMapper;
import com.jsmsframework.channel.entity.JsmsChannelgroup;
import com.jsmsframework.channel.service.JsmsChannelgroupService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道组明细表
 * @author huangwenjie
 * @date 2017-09-20
 */
@Service
public class JsmsChannelgroupServiceImpl implements JsmsChannelgroupService{

    @Autowired
    private JsmsChannelgroupMapper channelgroupMapper;
    
    @Override
	@Transactional
    public int insert(JsmsChannelgroup model) {
        return this.channelgroupMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsChannelgroup> modelList) {
        return this.channelgroupMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsChannelgroup model) {
		JsmsChannelgroup old = this.channelgroupMapper.getByChannelgroupid(model.getChannelgroupid());
		if(old == null){
			return 0;
		}
		return this.channelgroupMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsChannelgroup model) {
		JsmsChannelgroup old = this.channelgroupMapper.getByChannelgroupid(model.getChannelgroupid());
		if(old != null)
        	return this.channelgroupMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsChannelgroup getByChannelgroupid(Integer channelgroupid) {
        JsmsChannelgroup model = this.channelgroupMapper.getByChannelgroupid(channelgroupid);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsChannelgroup> list = this.channelgroupMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.channelgroupMapper.count(params);
    }

    @Override
    public Map<String, Object> queryOperatorstypeByChannelId(Integer channelgroupid) {
        return this.channelgroupMapper.queryOperatorstypeByChannelId(channelgroupid);
    }

}
