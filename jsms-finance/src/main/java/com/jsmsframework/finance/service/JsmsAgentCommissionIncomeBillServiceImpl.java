package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentCommissionIncomeBill;
import com.jsmsframework.finance.mapper.JsmsAgentCommissionIncomeBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商佣金帐户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsAgentCommissionIncomeBillServiceImpl implements JsmsAgentCommissionIncomeBillService {

    @Autowired
    private JsmsAgentCommissionIncomeBillMapper agentCommissionIncomeBillMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentCommissionIncomeBill model) {
        return this.agentCommissionIncomeBillMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentCommissionIncomeBill> modelList) {
        return this.agentCommissionIncomeBillMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentCommissionIncomeBill model) {
		JsmsAgentCommissionIncomeBill old = this.agentCommissionIncomeBillMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.agentCommissionIncomeBillMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentCommissionIncomeBill model) {
		JsmsAgentCommissionIncomeBill old = this.agentCommissionIncomeBillMapper.getById(model.getId());
		if(old != null)
        	return this.agentCommissionIncomeBillMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentCommissionIncomeBill getById(Integer id) {
        JsmsAgentCommissionIncomeBill model = this.agentCommissionIncomeBillMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentCommissionIncomeBill> list = this.agentCommissionIncomeBillMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentCommissionIncomeBillMapper.count(params);
    }
    
}
