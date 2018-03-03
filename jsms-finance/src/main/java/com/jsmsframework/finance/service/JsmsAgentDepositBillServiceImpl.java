package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentDepositBill;
import com.jsmsframework.finance.mapper.JsmsAgentDepositBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商押金账户收支明细
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsAgentDepositBillServiceImpl implements JsmsAgentDepositBillService {

    @Autowired
    private JsmsAgentDepositBillMapper agentDepositBillMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentDepositBill model) {
        return this.agentDepositBillMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentDepositBill> modelList) {
        return this.agentDepositBillMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentDepositBill model) {
		JsmsAgentDepositBill old = this.agentDepositBillMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.agentDepositBillMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentDepositBill model) {
		JsmsAgentDepositBill old = this.agentDepositBillMapper.getById(model.getId());
		if(old != null)
        	return this.agentDepositBillMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentDepositBill getById(Long id) {
        JsmsAgentDepositBill model = this.agentDepositBillMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentDepositBill> list = this.agentDepositBillMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentDepositBillMapper.count(params);
    }
    
}
