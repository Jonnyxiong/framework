package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentRebateBill;
import com.jsmsframework.finance.mapper.JsmsAgentRebateBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商返点账户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsAgentRebateBillServiceImpl implements JsmsAgentRebateBillService {

    @Autowired
    private JsmsAgentRebateBillMapper agentRebateBillMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentRebateBill model) {
        return this.agentRebateBillMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentRebateBill> modelList) {
        return this.agentRebateBillMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentRebateBill model) {
		JsmsAgentRebateBill old = this.agentRebateBillMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.agentRebateBillMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentRebateBill model) {
		JsmsAgentRebateBill old = this.agentRebateBillMapper.getById(model.getId());
		if(old != null)
        	return this.agentRebateBillMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentRebateBill getById(Long id) {
        JsmsAgentRebateBill model = this.agentRebateBillMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentRebateBill> list = this.agentRebateBillMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentRebateBillMapper.count(params);
    }
    
}
