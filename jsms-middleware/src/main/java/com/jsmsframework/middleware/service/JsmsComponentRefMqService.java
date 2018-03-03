package com.jsmsframework.middleware.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.middleware.dto.ComponentRefMqDTO;
import com.jsmsframework.middleware.entity.JsmsComponentRefMq;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 组件与MQ关联表
 * @author huangwenjie
 * @date 2017-11-04
 */
public interface JsmsComponentRefMqService {

    int insert(JsmsComponentRefMq model);
    
    int insertBatch(List<JsmsComponentRefMq> modelList);

    int update(JsmsComponentRefMq model);
    
    int updateSelective(JsmsComponentRefMq model);

    JsmsComponentRefMq getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsComponentRefMq getByComponentId(Integer componentId);

    /**
     * 组合【组件+消息类型+模式】列表输出
     * @param page
     * @return
     */
    JsmsPage queryList1(JsmsPage page);

    /**
     * 组合【组件+消息类型+模式】总数
     * @param params
     * @return
     */
    int count1(Map<String,Object> params);


    List<ComponentRefMqDTO> queryByParam(Map<String,Object> params);


    /**
     * 编辑回显SMSP组件及MQ配置相同【组件+消息类型+模式】视图
     * @param componentId
     * @param messageType
     * @param mode
     * @return
     */
    ComponentRefMqDTO queryByCMM(Integer componentId,  String messageType,Integer mode);
}
