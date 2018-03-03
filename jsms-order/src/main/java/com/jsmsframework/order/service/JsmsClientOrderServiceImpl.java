package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsClientOrder;
import com.jsmsframework.order.enums.GroupParamsType;
import com.jsmsframework.order.mapper.JsmsClientOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description 客户订单表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsClientOrderServiceImpl implements JsmsClientOrderService {

	@Autowired
	private JsmsClientOrderMapper clientOrderMapper;

	@Override
	@Transactional
	public int insert(JsmsClientOrder model) {
		return this.clientOrderMapper.insert(model);
	}

	@Override
	@Transactional
	public int insertBatch(List<JsmsClientOrder> modelList) {
		return this.clientOrderMapper.insertBatch(modelList);
	}

	@Override
	@Transactional
	public int update(JsmsClientOrder model) {
		JsmsClientOrder old = this.clientOrderMapper.getBySubId(Long.parseLong(model.getSubId()));
		if (old == null) {
			return 0;
		}
		return this.clientOrderMapper.update(model);
	}

	@Override
	@Transactional
	public int updateSelective(JsmsClientOrder model) {
		JsmsClientOrder old = this.clientOrderMapper.getBySubId(Long.parseLong(model.getSubId()));
		if (old != null)
			return this.clientOrderMapper.updateSelective(model);
		return 0;
	}

	@Override
	@Transactional
	public int updateIdempotent(JsmsClientOrder oldModel, JsmsClientOrder newModel) {
		JsmsClientOrder old = this.clientOrderMapper.getBySubId(Long.parseLong(oldModel.getSubId()));
		if (old != null) {
			Map<String, JsmsClientOrder> idempotentParams = new HashMap<>();
			idempotentParams.put("oldModel", oldModel);
			idempotentParams.put("newModel", newModel);
			return this.clientOrderMapper.updateIdempotent(idempotentParams);
		}
		return 0;
	}

	@Override
	public int updateRemainQuantity(JsmsClientOrder model) {
		JsmsClientOrder old = this.clientOrderMapper.getBySubId(Long.parseLong(model.getSubId()));
		if (old != null) {
			return this.clientOrderMapper.updateRemainQuantity(model);
		}
		return 0;
	}

	@Override
	public JsmsPage<JsmsClientOrder> queryRemainQuantity(JsmsPage jsmsPage,String clientId, ArrayList<GroupParamsType> groupParams,Set<Integer> productTypes) {

		jsmsPage.getParams().put("clientId", clientId);
		String groupParamsStr = null;
		if (!groupParams.isEmpty()) {
			StringBuffer temp = new StringBuffer();
			for (int i = 0; i < groupParams.size(); i++) {
				temp.append(groupParams.get(i).getValue()).append(",");
			}
			groupParamsStr = temp.substring(0, temp.length() - 1);
			jsmsPage.getParams().put("groupParams", groupParamsStr);
		}
		if (productTypes == null) {

		} else if (productTypes.isEmpty()) {
			productTypes = null;
		} else {
			jsmsPage.getParams().put("productTypes", productTypes);
		}
		List<JsmsClientOrder> list = this.clientOrderMapper.queryRemainQuantity(jsmsPage);
		jsmsPage.setData(list);
		return jsmsPage;
	}

	@Override
	@Transactional
	public List<JsmsClientOrder> getByAgentId(Integer agentId) {
		List<JsmsClientOrder> models = this.clientOrderMapper.getByAgentId(agentId);
		return models;
	}

	@Override
	public List<JsmsClientOrder> getByClientId(String clientId) {
		List<JsmsClientOrder> models = this.clientOrderMapper.getByClientId(clientId);
		return models;
	}

	@Override
	public List<JsmsClientOrder> getOrderRemainQuantity(String clientId, ArrayList<GroupParamsType> groupParams,
			Set<Integer> productTypes) {
		String groupParamsStr = null;
		if (!groupParams.isEmpty()) {
			StringBuffer temp = new StringBuffer();
			for (int i = 0; i < groupParams.size(); i++) {
				temp.append(groupParams.get(i).getValue()).append(",");
			}
			groupParamsStr = temp.substring(0, temp.length() - 1);
		}
		if (productTypes == null) {
		} else if (productTypes.isEmpty()) {
			productTypes = null;
		}
		List<JsmsClientOrder> models = this.clientOrderMapper.getOrderRemainQuantity(clientId,
				groupParamsStr.toString(), productTypes);
		return models;
	}

	@Override
	public JsmsPage<JsmsClientOrder> queryRemainQuantityList(JsmsPage jsmsPage, String clientId,
			ArrayList<GroupParamsType> groupParams, Set<Integer> productTypes) {

		jsmsPage.getParams().put("clientId", clientId);
		String groupParamsStr = null;
		if (!groupParams.isEmpty()) {
			StringBuffer temp = new StringBuffer();
			for (int i = 0; i < groupParams.size(); i++) {
				temp.append(groupParams.get(i).getValue()).append(",");
			}
			groupParamsStr = temp.substring(0, temp.length() - 1);
			jsmsPage.getParams().put("groupParams", groupParamsStr);
		}
		if (productTypes == null) {

		} else if (productTypes.isEmpty()) {
			productTypes = null;
		} else {
			jsmsPage.getParams().put("productTypes", productTypes);
		}
		List<JsmsClientOrder> list = this.clientOrderMapper.queryRemainQuantityList(jsmsPage);
		jsmsPage.setData(list);
		return jsmsPage;
	}

	@Override
	@Transactional
	public JsmsClientOrder getBySubId(Long subId) {
		JsmsClientOrder model = this.clientOrderMapper.getBySubId(subId);
		return model;
	}

	@Override
	@Transactional
	public List<JsmsClientOrder> getBySubIds(Set<Long> subIds) {
		List<JsmsClientOrder> modelList = this.clientOrderMapper.getBySubIds(subIds);
		return modelList;
	}

	@Override
	@Transactional
	public List<JsmsClientOrder> getByOrderIds(Set<Long> orderIds) {
		List<JsmsClientOrder> modelList = this.clientOrderMapper.getByOrderIds(orderIds);
		return modelList;
	}

	@Override
	@Transactional
	public JsmsPage queryList(JsmsPage page) {
		List<JsmsClientOrder> list = this.clientOrderMapper.queryList(page);
		page.setData(list);
		return page;
	}

	@Override
	@Transactional
	public int count(Map<String, Object> params) {
		return this.clientOrderMapper.count(params);
	}

	@Override
	public List<JsmsClientOrder> findReturnOrderList(JsmsClientOrder clientOrder) {
		if (clientOrder == null) {
			return null;
		}
		return this.clientOrderMapper.findReturnOrderList(clientOrder);
	}

}
