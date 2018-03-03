package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsOemAgentAccountStatistics;
import com.jsmsframework.finance.mapper.JsmsOemAgentAccountStatisticsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商帐户统计表
 * @author huangwenjie
 * @date 2017-08-20
 */
@Service
public class JsmsOemAgentAccountStatisticsServiceImpl implements JsmsOemAgentAccountStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsOemAgentAccountStatisticsServiceImpl.class);

    @Autowired
    private JsmsOemAgentAccountStatisticsMapper oemAgentAccountStatisticsMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOemAgentAccountStatistics model) {
        return this.oemAgentAccountStatisticsMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOemAgentAccountStatistics> modelList) {
        return this.oemAgentAccountStatisticsMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOemAgentAccountStatistics model) {
		JsmsOemAgentAccountStatistics old = this.oemAgentAccountStatisticsMapper.getByAgentId(model.getAgentId());
		if(old == null){
			return 0;
		}
		return this.oemAgentAccountStatisticsMapper.update(model); 
    }

	@Override
	@Transactional
    public int updateForAddPurchaseNumber(JsmsOemAgentAccountStatistics model) {
		JsmsOemAgentAccountStatistics old = this.oemAgentAccountStatisticsMapper.getByAgentId(model.getAgentId());
		if(old == null){
			return 0;
		}
		return this.oemAgentAccountStatisticsMapper.updateForAddPurchaseNumber(model);
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOemAgentAccountStatistics model) {
		JsmsOemAgentAccountStatistics old = this.oemAgentAccountStatisticsMapper.getByAgentId(model.getAgentId());
		if(old != null)
        	return this.oemAgentAccountStatisticsMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOemAgentAccountStatistics getByAgentId(Integer agentId) {
        JsmsOemAgentAccountStatistics model = this.oemAgentAccountStatisticsMapper.getByAgentId(agentId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage jsmsPage) {
        List<JsmsOemAgentAccountStatistics> list = this.oemAgentAccountStatisticsMapper.queryList(jsmsPage);
        jsmsPage.setData(list);
        return jsmsPage;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oemAgentAccountStatisticsMapper.count(params);
    }


    
}
