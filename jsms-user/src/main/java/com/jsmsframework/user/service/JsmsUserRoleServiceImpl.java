package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsUserRoleMapper;
import com.jsmsframework.user.entity.JsmsUserRole;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 用户角色关系表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsUserRoleServiceImpl implements JsmsUserRoleService{

    @Autowired
    private JsmsUserRoleMapper userRoleMapper;
    
    @Override
	@Transactional
    public int insert(JsmsUserRole model) {
        return this.userRoleMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsUserRole> modelList) {
        return this.userRoleMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsUserRole model) {
		JsmsUserRole old = this.userRoleMapper.getByRuId(model.getRuId());
		if(old == null){
			return 0;
		}
		return this.userRoleMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsUserRole model) {
		JsmsUserRole old = this.userRoleMapper.getByRuId(model.getRuId());
		if(old != null)
        	return this.userRoleMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsUserRole getByRuId(Integer ruId) {
        JsmsUserRole model = this.userRoleMapper.getByRuId(ruId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsUserRole> list = this.userRoleMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.userRoleMapper.count(params);
    }

    @Override
    public List<JsmsUserRole> getUserIdFromUserRoleByRoleId(String roleId) {
        return userRoleMapper.getUserIdFromUserRoleByRoleId(roleId);
    }

}
