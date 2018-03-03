package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsClientTariff;
import com.jsmsframework.product.mapper.JsmsClientTariffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description 国际短信费率表
 * @author huangwenjie
 * @date 2017-11-27
 */
@Service
public class JsmsClientTariffServiceImpl implements JsmsClientTariffService{

    @Autowired
    private JsmsClientTariffMapper clientTariffMapper;
    
    @Override
    public int insert(JsmsClientTariff model) {
        return this.clientTariffMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsClientTariff> modelList) {
        return this.clientTariffMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsClientTariff model) {
		JsmsClientTariff old = this.clientTariffMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.clientTariffMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsClientTariff model) {
		JsmsClientTariff old = this.clientTariffMapper.getById(model.getId());
		if(old != null)
        	return this.clientTariffMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsClientTariff getById(Integer id) {
        JsmsClientTariff model = this.clientTariffMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientTariff> list = this.clientTariffMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.clientTariffMapper.count(params);
    }
    
}
