package com.jsmsframework.sysConfig.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.jsmsframework.common.enums.balckList.SmstypesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.sysConfig.mapper.JsmsWhiteListMapper;
import com.jsmsframework.sysConfig.entity.JsmsWhiteList;
import com.jsmsframework.sysConfig.service.JsmsWhiteListService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 黑名单表
 * @author Don
 * @date 2018-01-10
 */
@Service
public class JsmsWhiteListServiceImpl implements JsmsWhiteListService{

    @Autowired
    private JsmsWhiteListMapper whiteListMapper;
    
    @Override
    public int insert(JsmsWhiteList model) {
        return this.whiteListMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsWhiteList> modelList) {
        return this.whiteListMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsWhiteList model) {
		JsmsWhiteList old = this.whiteListMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.whiteListMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsWhiteList model) {
		JsmsWhiteList old = this.whiteListMapper.getById(model.getId());
		if(old != null)
        	return this.whiteListMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsWhiteList getById(Long id) {
        JsmsWhiteList model = this.whiteListMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsWhiteList> list = this.whiteListMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsWhiteList> findList(Map params) {
        List<JsmsWhiteList> list = this.whiteListMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.whiteListMapper.count(params);
    }

    @Override
    public JsmsWhiteList getByPhone(String phone) {
        return this.whiteListMapper.getByPhone(phone);
    }

    @Override
    public int deleteWhiteList(Long id) {
        return this.whiteListMapper.deleteWhiteList(id);
    }


}
