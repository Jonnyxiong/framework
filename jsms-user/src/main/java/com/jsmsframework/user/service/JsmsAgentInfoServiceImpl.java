package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import com.jsmsframework.user.mapper.JsmsAgentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsAgentInfoServiceImpl implements JsmsAgentInfoService{

    @Autowired
    private JsmsAgentInfoMapper agentInfoMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentInfo model) {
        return this.agentInfoMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentInfo> modelList) {
        return this.agentInfoMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentInfo model) {
		JsmsAgentInfo old = this.agentInfoMapper.getByAgentId(model.getAgentId());
		if(old == null){
			return 0;
		}
		return this.agentInfoMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentInfo model) {
		JsmsAgentInfo old = this.agentInfoMapper.getByAgentId(model.getAgentId());
		if(old != null)
        	return this.agentInfoMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentInfo getByAgentId(Integer agentId) {
        JsmsAgentInfo model = this.agentInfoMapper.getByAgentId(agentId);
		return model;
    }

    @Override
	@Transactional
    public JsmsAgentInfo getByAdminId(Long adminId) {
        JsmsAgentInfo model = this.agentInfoMapper.getByAdminId(adminId);
		return model;
    }

    @Override
	@Transactional
    public List<JsmsAgentInfo> getByAgentIds(Set agentIds) {
        List<JsmsAgentInfo> models = this.agentInfoMapper.getByAgentIds(agentIds);
		return models;
    }

    @Override
    public List<JsmsAgentInfo> getByAgentIdsAndType(Integer agentType, Set agentId) {
        return this.agentInfoMapper.getByAgentIdsAndType(agentType, agentId);
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentInfo> list = this.agentInfoMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentInfoMapper.count(params);
    }

    @Override
    @Transactional
    public List<JsmsAgentInfo> getByAgentName(String agentName) {
        List<JsmsAgentInfo> model = this.agentInfoMapper.getByAgentName(agentName);
        return model;
    }

    @Override
    public List<JsmsAgentInfo> findList(JsmsAgentInfo model) {
        return this.agentInfoMapper.findList(model);
    }

    @Override
    public JsmsPage queryListForSale(JsmsPage page) {
        List<JsmsAgentInfo> list = this.agentInfoMapper.queryListForSale(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<Integer> queryAgentIdsByParams(Map<String, Object> params) {
        return this.agentInfoMapper.queryAgentIdsByParams(params);
    }

    @Override
    public List<JsmsAgentInfo> queryAll(Map<String, Object> params) {
        return this.agentInfoMapper.queryAll(params);
    }
    @Override
    public List<JsmsAgentInfo> getAgentInfoNotInInvoiceAuth(JsmsPage<JsmsAgentInfo> page){
        return this.agentInfoMapper.getAgentInfoNotInInvoiceAuth( page);
    }

    @Override
    public List<JsmsAgentInfo> getByBelongSale(Long belongSale) {
        return agentInfoMapper.getByBelongSale( belongSale);
    }

    @Override
    public List<JsmsAgentInfo> findListByRight(Map<String, Object> params) {
        return this.agentInfoMapper.findListByRight(params);
    }
    @Override
    public List<JsmsAgentInfo> loadAllForSelect(String webId) {
        return this.agentInfoMapper.loadAllForSelect(webId);
    }
}
