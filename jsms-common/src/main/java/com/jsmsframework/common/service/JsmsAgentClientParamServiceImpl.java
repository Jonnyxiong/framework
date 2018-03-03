package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsAgentClientParam;
import com.jsmsframework.common.mapper.JsmsAgentClientParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商和客户属性配置表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsAgentClientParamServiceImpl implements JsmsAgentClientParamService{

    @Autowired
    private JsmsAgentClientParamMapper agentClientParamMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentClientParam model) {
        return this.agentClientParamMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentClientParam> modelList) {
        return this.agentClientParamMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentClientParam model) {
		JsmsAgentClientParam old = this.agentClientParamMapper.getByParamId(model.getParamId());
		if(old == null){
			return 0;
		}
		return this.agentClientParamMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentClientParam model) {
		JsmsAgentClientParam old = this.agentClientParamMapper.getByParamId(model.getParamId());
		if(old != null)
        	return this.agentClientParamMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentClientParam getByParamId(Long paramId) {
        JsmsAgentClientParam model = this.agentClientParamMapper.getByParamId(paramId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentClientParam> list = this.agentClientParamMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentClientParamMapper.count(params);
    }

    @Override
    public JsmsAgentClientParam getByParamKey(String paramKey) {
        return this.agentClientParamMapper.getByParamKey(paramKey);
    }
}
