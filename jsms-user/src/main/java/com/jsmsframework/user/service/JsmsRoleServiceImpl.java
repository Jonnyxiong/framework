package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsRoleMapper;
import com.jsmsframework.user.entity.JsmsRole;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 角色表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsRoleServiceImpl implements JsmsRoleService{

    @Autowired
    private JsmsRoleMapper roleMapper;
    
    @Override
	@Transactional
    public int insert(JsmsRole model) {
        return this.roleMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsRole> modelList) {
        return this.roleMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsRole model) {
		JsmsRole old = this.roleMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.roleMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsRole model) {
		JsmsRole old = this.roleMapper.getById(model.getId());
		if(old != null)
        	return this.roleMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsRole getById(Integer id) {
        JsmsRole model = this.roleMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsRole> list = this.roleMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.roleMapper.count(params);
    }

    @Override
    public String getSaleRoleId() {
        return roleMapper.getSaleRoleId();
    }

    @Override
    public Integer queryOemRoleId() {
        return roleMapper.queryOemRoleId();
    }

}
