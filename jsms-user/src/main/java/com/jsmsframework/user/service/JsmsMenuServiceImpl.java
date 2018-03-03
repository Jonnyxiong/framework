package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsMenuMapper;
import com.jsmsframework.user.entity.JsmsMenu;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 菜单表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsMenuServiceImpl implements JsmsMenuService{

    @Autowired
    private JsmsMenuMapper menuMapper;
    
    @Override
	@Transactional
    public int insert(JsmsMenu model) {
        return this.menuMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsMenu> modelList) {
        return this.menuMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsMenu model) {
		JsmsMenu old = this.menuMapper.getByMenuId(model.getMenuId());
		if(old == null){
			return 0;
		}
		return this.menuMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsMenu model) {
		JsmsMenu old = this.menuMapper.getByMenuId(model.getMenuId());
		if(old != null)
        	return this.menuMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsMenu getByMenuId(Integer menuId) {
        JsmsMenu model = this.menuMapper.getByMenuId(menuId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsMenu> list = this.menuMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsMenu> findList(JsmsMenu jsmsMenu) {
        List<JsmsMenu> list = menuMapper.findList(jsmsMenu);
        return list;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.menuMapper.count(params);
    }
    
}
