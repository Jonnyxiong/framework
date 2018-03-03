package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsRoleMenuMapper;
import com.jsmsframework.user.entity.JsmsRoleMenu;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 角色菜单表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsRoleMenuServiceImpl implements JsmsRoleMenuService{

    @Autowired
    private JsmsRoleMenuMapper roleMenuMapper;
    
    @Override
	@Transactional
    public int insert(JsmsRoleMenu model) {
        return this.roleMenuMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsRoleMenu> modelList) {
        return this.roleMenuMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsRoleMenu model) {
		JsmsRoleMenu old = this.roleMenuMapper.getByRoleMenuId(model.getRoleMenuId());
		if(old == null){
			return 0;
		}
		return this.roleMenuMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsRoleMenu model) {
		JsmsRoleMenu old = this.roleMenuMapper.getByRoleMenuId(model.getRoleMenuId());
		if(old != null)
        	return this.roleMenuMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsRoleMenu getByRoleMenuId(Integer roleMenuId) {
        JsmsRoleMenu model = this.roleMenuMapper.getByRoleMenuId(roleMenuId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsRoleMenu> list = this.roleMenuMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.roleMenuMapper.count(params);
    }
    
}
