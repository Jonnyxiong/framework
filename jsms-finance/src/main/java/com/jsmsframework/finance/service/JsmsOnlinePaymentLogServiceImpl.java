package com.jsmsframework.finance.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.finance.mapper.JsmsOnlinePaymentLogMapper;
import com.jsmsframework.finance.entity.JsmsOnlinePaymentLog;
import com.jsmsframework.finance.service.JsmsOnlinePaymentLogService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 在线支付流水日志表
 * @author huangwenjie
 * @date 2017-12-29
 */
@Service
public class JsmsOnlinePaymentLogServiceImpl implements JsmsOnlinePaymentLogService{

    @Autowired
    private JsmsOnlinePaymentLogMapper onlinePaymentLogMapper;
    
    @Override
    public int insert(JsmsOnlinePaymentLog model) {
        return this.onlinePaymentLogMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsOnlinePaymentLog> modelList) {
        return this.onlinePaymentLogMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsOnlinePaymentLog model) {
		JsmsOnlinePaymentLog old = this.onlinePaymentLogMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.onlinePaymentLogMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsOnlinePaymentLog model) {
		JsmsOnlinePaymentLog old = this.onlinePaymentLogMapper.getById(model.getId());
		if(old != null)
        	return this.onlinePaymentLogMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsOnlinePaymentLog getById(Integer id) {
        JsmsOnlinePaymentLog model = this.onlinePaymentLogMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOnlinePaymentLog> list = this.onlinePaymentLogMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.onlinePaymentLogMapper.count(params);
    }


    @Override
    public int insert(String paymentId, Long adminId, Date createTime, Integer paymentState) {
        JsmsOnlinePaymentLog jsmsOnlinePaymentLog = new JsmsOnlinePaymentLog();
        jsmsOnlinePaymentLog.setCreateTime(createTime);
        jsmsOnlinePaymentLog.setAdminId(adminId);
        jsmsOnlinePaymentLog.setPaymentState(paymentState);
        jsmsOnlinePaymentLog.setPaymentId(paymentId);
        return this.insert(jsmsOnlinePaymentLog);
    }
}
