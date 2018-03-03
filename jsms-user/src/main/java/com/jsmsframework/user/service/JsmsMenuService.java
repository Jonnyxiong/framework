package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsMenu;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 菜单表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsMenuService {

    int insert(JsmsMenu model);
    
    int insertBatch(List<JsmsMenu> modelList);

    int update(JsmsMenu model);
    
    int updateSelective(JsmsMenu model);

    JsmsMenu getByMenuId(Integer menuId);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsMenu> findList(JsmsMenu jsmsMenu);

    int count(Map<String,Object> params);
    
}
