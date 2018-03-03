package com.jsmsframework.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsMenu;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 菜单表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsMenuMapper{

	int insert(JsmsMenu model);
	
	int insertBatch(List<JsmsMenu> modelList);

	
	int update(JsmsMenu model);
	
	int updateSelective(JsmsMenu model);

    JsmsMenu getByMenuId(Integer menuId);

	List<JsmsMenu> queryList(JsmsPage<JsmsMenu> page);

	List<JsmsMenu> findList(JsmsMenu jsmsMenu);

	int count(Map<String,Object> params);

}