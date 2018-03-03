package com.jsmsframework.middleware.service;

import java.util.Map;
import java.util.List;
import java.util.Set;

import com.jsmsframework.middleware.entity.JsmsComponentConfigure;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 组件配置信息表
 * @author huangwenjie
 * @date 2017-11-04
 */
public interface JsmsComponentConfigureService {

    int insert(JsmsComponentConfigure model);
    
    int insertBatch(List<JsmsComponentConfigure> modelList);

    int update(JsmsComponentConfigure model);
    
    int updateSelective(JsmsComponentConfigure model);

    JsmsComponentConfigure getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsComponentConfigure getByComponentId(Integer componentId);

    int updateSwitch(Set<Integer> ids,Integer comswitch);

    /**
     * @Description  根据组件类型加载对应的所有组件(component_id,component_name)
     * @author yeshiyuan
     * @date 2018/1/8 17:58
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<JsmsComponentConfigure> loadAllForSelectByType(List<String> list);
}
