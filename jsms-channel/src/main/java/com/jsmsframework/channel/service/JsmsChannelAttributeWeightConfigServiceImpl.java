package com.jsmsframework.channel.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.channel.mapper.JsmsChannelAttributeWeightConfigMapper;
import com.jsmsframework.channel.entity.JsmsChannelAttributeWeightConfig;
import com.jsmsframework.channel.service.JsmsChannelAttributeWeightConfigService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道属性区间权重配置表
 * @author huangwenjie
 * @date 2017-09-28
 */
@Service
public class JsmsChannelAttributeWeightConfigServiceImpl implements JsmsChannelAttributeWeightConfigService{

    @Autowired
    private JsmsChannelAttributeWeightConfigMapper channelAttributeWeightConfigMapper;
    
    @Override
	@Transactional
    public int insert(JsmsChannelAttributeWeightConfig model) {
        return this.channelAttributeWeightConfigMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsChannelAttributeWeightConfig> modelList) {
        return this.channelAttributeWeightConfigMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsChannelAttributeWeightConfig model) {
		JsmsChannelAttributeWeightConfig old = this.channelAttributeWeightConfigMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.channelAttributeWeightConfigMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsChannelAttributeWeightConfig model) {
		JsmsChannelAttributeWeightConfig old = this.channelAttributeWeightConfigMapper.getById(model.getId());
		if(old != null)
        	return this.channelAttributeWeightConfigMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsChannelAttributeWeightConfig getById(Integer id) {
        JsmsChannelAttributeWeightConfig model = this.channelAttributeWeightConfigMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsChannelAttributeWeightConfig> list = this.channelAttributeWeightConfigMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    @Transactional
    public JsmsPage queryList1(JsmsPage page) {
        List<JsmsChannelAttributeWeightConfig> list = this.channelAttributeWeightConfigMapper.queryList1(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.channelAttributeWeightConfigMapper.count(params);
    }

    @Override
    public int delete(Integer id) {
        return this.channelAttributeWeightConfigMapper.delete(id);
    }

    @Override
    public Map<String, Object> channelConfigCheck(Map<String, String> params) {
        Map<String,Object> result=new HashMap();
        List<Map<String,Object>> datalist=this.channelAttributeWeightConfigMapper.checkAll(params);
        result.put("datalist",datalist);
        return result;
    }

    @Override
    public List<JsmsChannelAttributeWeightConfig> queryAllBySmSType() {
        return this.channelAttributeWeightConfigMapper.queryAllBySmSType();
    }

}
