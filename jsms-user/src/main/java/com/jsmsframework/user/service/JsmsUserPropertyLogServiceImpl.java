package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsUserPropertyLog;
import com.jsmsframework.user.mapper.JsmsUserPropertyLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 用户属性变更记录
 * @author lpjLiu
 * @date 2017-10-11
 */
@Service
public class JsmsUserPropertyLogServiceImpl implements JsmsUserPropertyLogService {

	@Autowired
	private JsmsUserPropertyLogMapper userPropertyLogMapper;

	@Override
	@Transactional
	public int insert(JsmsUserPropertyLog model) {
		return this.userPropertyLogMapper.insert(model);
	}

	@Override
	@Transactional
	public int insertBatch(List<JsmsUserPropertyLog> modelList) {
		return this.userPropertyLogMapper.insertBatch(modelList);
	}

	@Override
	@Transactional
	public int update(JsmsUserPropertyLog model) {
		JsmsUserPropertyLog old = this.userPropertyLogMapper.getById(model.getId());
		if (old == null) {
			return 0;
		}
		return this.userPropertyLogMapper.update(model);
	}

	@Override
	@Transactional
	public int updateSelective(JsmsUserPropertyLog model) {
		JsmsUserPropertyLog old = this.userPropertyLogMapper.getById(model.getId());
		if (old != null)
			return this.userPropertyLogMapper.updateSelective(model);
		return 0;
	}

	@Override
	@Transactional
	public JsmsUserPropertyLog getById(Integer id) {
		JsmsUserPropertyLog model = this.userPropertyLogMapper.getById(id);
		return model;
	}

	@Override
	@Transactional
	public JsmsPage queryList(JsmsPage page) {
		List<JsmsUserPropertyLog> list = this.userPropertyLogMapper.queryList(page);
		page.setData(list);
		return page;
	}

	@Override
	@Transactional
	public int count(Map<String, Object> params) {
		return this.userPropertyLogMapper.count(params);
	}

	@Override
	public Integer getChargeRuleByClientIdAndDate(String clientId, String date) {
		return this.userPropertyLogMapper.getChargeRuleByClientIdAndDate(clientId, date);
	}

	@Override
	public JsmsUserPropertyLog getCanUpdateChargeRuleByClientIdAndEffectDate(String clientId, String date) {
		return this.userPropertyLogMapper.getCanUpdateChargeRuleByClientIdAndEffectDate(clientId, date);
	}

	@Override
	public List<JsmsUserPropertyLog> findLastEffectDateList(String property, String value) {
		return this.userPropertyLogMapper.findLastEffectDateList(property, value);
	}
}
