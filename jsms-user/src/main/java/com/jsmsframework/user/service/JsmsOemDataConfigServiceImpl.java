package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsOemDataConfig;
import com.jsmsframework.user.mapper.JsmsOemDataConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description OEM资料配置
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsOemDataConfigServiceImpl implements JsmsOemDataConfigService{

    @Autowired
    private JsmsOemDataConfigMapper oemDataConfigMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOemDataConfig model) {
        return this.oemDataConfigMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOemDataConfig> modelList) {
        return this.oemDataConfigMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOemDataConfig model) {
		JsmsOemDataConfig old = this.oemDataConfigMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.oemDataConfigMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOemDataConfig model) {
		JsmsOemDataConfig old = this.oemDataConfigMapper.getById(model.getId());
		if(old != null)
        	return this.oemDataConfigMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOemDataConfig getById(Integer id) {
        JsmsOemDataConfig model = this.oemDataConfigMapper.getById(id);
		return model;
    }

    @Override
    public JsmsOemDataConfig getByAgentId(Integer id) {
        JsmsOemDataConfig model = this.oemDataConfigMapper.getByAgentId(id);
        return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOemDataConfig> list = this.oemDataConfigMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oemDataConfigMapper.count(params);
    }

    @Override
    public int updateSelectiveByAgentId(JsmsOemDataConfig model) {
        return this.oemDataConfigMapper.updateSelectiveByAgentId(model);
    }
}
