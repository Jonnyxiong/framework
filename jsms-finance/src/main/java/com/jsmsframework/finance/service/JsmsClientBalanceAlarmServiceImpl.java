package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsClientBalanceAlarm;
import com.jsmsframework.finance.mapper.JsmsClientBalanceAlarmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 预付费客户余额预警信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsClientBalanceAlarmServiceImpl implements JsmsClientBalanceAlarmService {

    @Autowired
    private JsmsClientBalanceAlarmMapper clientBalanceAlarmMapper;
    
    @Override
	@Transactional
    public int insert(JsmsClientBalanceAlarm model) {
        return this.clientBalanceAlarmMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsClientBalanceAlarm> modelList) {
        return this.clientBalanceAlarmMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsClientBalanceAlarm model) {
		JsmsClientBalanceAlarm old = this.clientBalanceAlarmMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.clientBalanceAlarmMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsClientBalanceAlarm model) {
		JsmsClientBalanceAlarm old = this.clientBalanceAlarmMapper.getById(model.getId());
		if(old != null)
        	return this.clientBalanceAlarmMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsClientBalanceAlarm getById(Integer id) {
        JsmsClientBalanceAlarm model = this.clientBalanceAlarmMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientBalanceAlarm> list = this.clientBalanceAlarmMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsClientBalanceAlarm> findList(JsmsClientBalanceAlarm model) {
        return this.clientBalanceAlarmMapper.findList(model);
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.clientBalanceAlarmMapper.count(params);
    }

    @Override
    public int isAlarm(Map<String, Object> params) {
        return this.clientBalanceAlarmMapper.isAlarm(params);
    }
	
    @Override
    public List<JsmsClientBalanceAlarm> findListAlarm(JsmsClientBalanceAlarm params, Set clientIds) {
        return clientBalanceAlarmMapper.findListAlarm(params, clientIds);
    }


}
