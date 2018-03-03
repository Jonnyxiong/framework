package com.jsmsframework.channel.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.channel.mapper.JsmsChannelPoolPolicyMapper;
import com.jsmsframework.channel.entity.JsmsChannelPoolPolicy;
import com.jsmsframework.channel.service.JsmsChannelPoolPolicyService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道池策略表
 * @author huangwenjie
 * @date 2017-09-28
 */
@Service
public class JsmsChannelPoolPolicyServiceImpl implements JsmsChannelPoolPolicyService{

    @Autowired
    private JsmsChannelPoolPolicyMapper channelPoolPolicyMapper;
    
    @Override
	@Transactional
    public int insert(JsmsChannelPoolPolicy model) {
        return this.channelPoolPolicyMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsChannelPoolPolicy> modelList) {
        return this.channelPoolPolicyMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsChannelPoolPolicy model) {
		JsmsChannelPoolPolicy old = this.channelPoolPolicyMapper.getByPolicyId(model.getPolicyId());
		if(old == null){
			return 0;
		}
		return this.channelPoolPolicyMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsChannelPoolPolicy model) {
		JsmsChannelPoolPolicy old = this.channelPoolPolicyMapper.getByPolicyId(model.getPolicyId());
		if(old != null)
        	return this.channelPoolPolicyMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsChannelPoolPolicy getByPolicyId(Long policyId) {
        JsmsChannelPoolPolicy model = this.channelPoolPolicyMapper.getByPolicyId(policyId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsChannelPoolPolicy> list = this.channelPoolPolicyMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.channelPoolPolicyMapper.count(params);
    }

    @Override
    public int updatedefault() {
        return this.channelPoolPolicyMapper.updatedefault();
    }

}
