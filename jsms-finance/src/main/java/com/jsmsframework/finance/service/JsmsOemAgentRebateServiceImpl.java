package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsOemAgentRebate;
import com.jsmsframework.finance.mapper.JsmsOemAgentRebateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理返点比例配置
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsOemAgentRebateServiceImpl implements JsmsOemAgentRebateService {

    @Autowired
    private JsmsOemAgentRebateMapper oemAgentRebateMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOemAgentRebate model) {
        return this.oemAgentRebateMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOemAgentRebate> modelList) {
        return this.oemAgentRebateMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOemAgentRebate model) {
		JsmsOemAgentRebate old = this.oemAgentRebateMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.oemAgentRebateMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOemAgentRebate model) {
		JsmsOemAgentRebate old = this.oemAgentRebateMapper.getById(model.getId());
		if(old != null)
        	return this.oemAgentRebateMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOemAgentRebate getById(Integer id) {
        JsmsOemAgentRebate model = this.oemAgentRebateMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOemAgentRebate> list = this.oemAgentRebateMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oemAgentRebateMapper.count(params);
    }
    
}
