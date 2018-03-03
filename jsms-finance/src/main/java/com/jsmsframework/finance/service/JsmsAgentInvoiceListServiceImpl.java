package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.dto.JsmsAgentInvoiceListDTO;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceList;
import com.jsmsframework.finance.mapper.JsmsAgentInvoiceListMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;



/**
* @author huangwenjie
* @since 2018-01-23
*/
@Service
public class JsmsAgentInvoiceListServiceImpl implements JsmsAgentInvoiceListService{

    @Autowired
    private JsmsAgentInvoiceListMapper jsmsAgentInvoiceListMapper;
    
    @Override
    public int insert(JsmsAgentInvoiceList model) {
        return this.jsmsAgentInvoiceListMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsAgentInvoiceList> modelList) {
        return this.jsmsAgentInvoiceListMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsAgentInvoiceList model) {
        JsmsAgentInvoiceList old = this.jsmsAgentInvoiceListMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
        //理应将old输出日志，保证当时操作时数据是这样
        BeanUtils.copyProperties(model,old);
		return this.jsmsAgentInvoiceListMapper.update(old);
    }

    @Override
    public int updateSelective(JsmsAgentInvoiceList model) {
        JsmsAgentInvoiceList old = this.jsmsAgentInvoiceListMapper.getById(model.getId());
		if(old != null)
        	return this.jsmsAgentInvoiceListMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsAgentInvoiceList getById(Integer id){
        JsmsAgentInvoiceList model = this.jsmsAgentInvoiceListMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentInvoiceList> list = this.jsmsAgentInvoiceListMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.jsmsAgentInvoiceListMapper.count(params);
    }

    @Override
    public List<JsmsAgentInvoiceList> findList(Map<String,Object> params){
        return this.jsmsAgentInvoiceListMapper.findList(params);
    }

    @Override
    public BigDecimal getWaitInvoiceListAmount(Map<String, Object> params) {
        return this.jsmsAgentInvoiceListMapper.getWaitInvoiceListAmount(params);
    }

    @Override
    public JsmsAgentInvoiceList getByInvoiceId(String invoiceId) {
        return this.jsmsAgentInvoiceListMapper.getByInvoiceId(invoiceId);
    }

    @Override
    public int updateStatus(JsmsAgentInvoiceList newModel, JsmsAgentInvoiceList oldModel) {
        return this.jsmsAgentInvoiceListMapper.updateStatus(newModel, oldModel);
    }
    @Override
    public List<JsmsAgentInvoiceListDTO> queryListForInvoice(JsmsPage page) {
        return this.jsmsAgentInvoiceListMapper.queryListForInvoice(page);
    }

    @Override
    public int cancelApply(JsmsAgentInvoiceList model) {
        return jsmsAgentInvoiceListMapper.cancelApply( model);
    }
}
