package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentRebate;
import com.jsmsframework.finance.mapper.JsmsAgentRebateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 代理返点比例配置
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsAgentRebateServiceImpl implements JsmsAgentRebateService {

    @Autowired
    private JsmsAgentRebateMapper agentRebateMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentRebate model) {
        return this.agentRebateMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentRebate> modelList) {
        return this.agentRebateMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentRebate model) {
		JsmsAgentRebate old = this.agentRebateMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.agentRebateMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentRebate model) {
		JsmsAgentRebate old = this.agentRebateMapper.getById(model.getId());
		if(old != null)
        	return this.agentRebateMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentRebate getById(Integer id) {
        JsmsAgentRebate model = this.agentRebateMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentRebate> list = this.agentRebateMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentRebateMapper.count(params);
    }
    
}
