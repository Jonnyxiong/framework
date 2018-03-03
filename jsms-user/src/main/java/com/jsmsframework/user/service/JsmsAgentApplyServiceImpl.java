package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsAgentApplyMapper;
import com.jsmsframework.user.entity.JsmsAgentApply;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商申请表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsAgentApplyServiceImpl implements JsmsAgentApplyService{

    @Autowired
    private JsmsAgentApplyMapper agentApplyMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentApply model) {
        return this.agentApplyMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentApply> modelList) {
        return this.agentApplyMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentApply model) {
		JsmsAgentApply old = this.agentApplyMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.agentApplyMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentApply model) {
		JsmsAgentApply old = this.agentApplyMapper.getById(model.getId());
		if(old != null)
        	return this.agentApplyMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentApply getById(Integer id) {
        JsmsAgentApply model = this.agentApplyMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentApply> list = this.agentApplyMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentApplyMapper.count(params);
    }

    @Override
    public JsmsAgentApply checkEmailAndMobileInApply(String email, String mobile) {
        return agentApplyMapper.checkEmailAndMobileInApply(email,mobile);
    }

    @Override
    public Integer getIdByEmailAndMobile(String email, String mobile) {
        return agentApplyMapper.getIdByEmailAndMobile( email,  mobile);
    }

}
