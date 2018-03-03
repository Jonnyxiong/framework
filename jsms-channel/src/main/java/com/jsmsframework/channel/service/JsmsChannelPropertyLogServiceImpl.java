package com.jsmsframework.channel.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.jsmsframework.common.enums.channel.ChannelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.channel.mapper.JsmsChannelPropertyLogMapper;
import com.jsmsframework.channel.entity.JsmsChannelPropertyLog;
import com.jsmsframework.channel.service.JsmsChannelPropertyLogService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道属性变更记录表
 * @author Don
 * @date 2018-01-13
 */
@Service
public class JsmsChannelPropertyLogServiceImpl implements JsmsChannelPropertyLogService{

    @Autowired
    private JsmsChannelPropertyLogMapper channelPropertyLogMapper;
    
    @Override
    public int insert(JsmsChannelPropertyLog model) {
        return this.channelPropertyLogMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsChannelPropertyLog> modelList) {
        return this.channelPropertyLogMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsChannelPropertyLog model) {
		JsmsChannelPropertyLog old = this.channelPropertyLogMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.channelPropertyLogMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsChannelPropertyLog model) {
		JsmsChannelPropertyLog old = this.channelPropertyLogMapper.getById(model.getId());
		if(old != null)
        	return this.channelPropertyLogMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsChannelPropertyLog getById(Integer id) {
        JsmsChannelPropertyLog model = this.channelPropertyLogMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsChannelPropertyLog> list = this.channelPropertyLogMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsChannelPropertyLog> findList(Map params) {
        List<JsmsChannelPropertyLog> list = this.channelPropertyLogMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.channelPropertyLogMapper.count(params);
    }

    @Override
    public JsmsChannelPropertyLog getByChannelAndProperty(Integer channelId, ChannelProperty channelProperty,boolean isEffect) {
        String isEffectStr=isEffect==true?"1":null;
        return this.channelPropertyLogMapper.getByChannelAndProperty(channelId,channelProperty.getValue(),isEffectStr);
    }

}
