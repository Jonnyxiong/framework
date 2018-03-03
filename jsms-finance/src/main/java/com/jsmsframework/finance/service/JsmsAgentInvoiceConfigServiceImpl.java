package com.jsmsframework.finance.service;

import java.util.*;

import com.jsmsframework.finance.dto.JsmsAgentInvoiceConfigDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import com.jsmsframework.finance.mapper.JsmsAgentInvoiceConfigMapper;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceConfig;
import com.jsmsframework.finance.service.JsmsAgentInvoiceConfigService;


import com.jsmsframework.common.dto.JsmsPage;  //分页dto



/**
* @author huangwenjie
* @since 2018-01-23
*/
@Service
public class JsmsAgentInvoiceConfigServiceImpl implements JsmsAgentInvoiceConfigService{

    @Autowired
    private JsmsAgentInvoiceConfigMapper jsmsAgentInvoiceConfigMapper;

    @Override
    public int insert(JsmsAgentInvoiceConfig model) {
        return this.jsmsAgentInvoiceConfigMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsAgentInvoiceConfig> modelList) {
        return this.jsmsAgentInvoiceConfigMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsAgentInvoiceConfig model) {
        JsmsAgentInvoiceConfig old = this.jsmsAgentInvoiceConfigMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
        //理应将old输出日志，保证当时操作时数据是这样
        BeanUtils.copyProperties(model,old);
		return this.jsmsAgentInvoiceConfigMapper.update(old);
    }

    @Override
    public int updateSelective(JsmsAgentInvoiceConfig model) {
        JsmsAgentInvoiceConfig old = this.jsmsAgentInvoiceConfigMapper.getById(model.getId());
		if(old != null)
        	return this.jsmsAgentInvoiceConfigMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsAgentInvoiceConfig getById(Integer id){
        JsmsAgentInvoiceConfig model = this.jsmsAgentInvoiceConfigMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentInvoiceConfig> list = this.jsmsAgentInvoiceConfigMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.jsmsAgentInvoiceConfigMapper.count(params);
    }

    @Override
    public List<JsmsAgentInvoiceConfig> findList(Map<String,Object> params){
        return this.jsmsAgentInvoiceConfigMapper.findList(params);
    }

    @Override
    public List<JsmsAgentInvoiceConfigDto> queryListForInvoice(JsmsPage page){
        return jsmsAgentInvoiceConfigMapper.queryListForInvoice(page);
    }


}
