package com.jsmsframework.channel.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.channel.entity.JsmsChannelPropertyLog;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.channel.ChannelProperty;


/**
 * @description 通道属性变更记录表
 * @author Don
 * @date 2018-01-13
 */
public interface JsmsChannelPropertyLogService {

    int insert(JsmsChannelPropertyLog model);
    
    int insertBatch(List<JsmsChannelPropertyLog> modelList);

    int update(JsmsChannelPropertyLog model);
    
    int updateSelective(JsmsChannelPropertyLog model);

    JsmsChannelPropertyLog getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsChannelPropertyLog> findList(Map params);

    int count(Map<String,Object> params);

    /**
     * 根据通道号+属性名+查看是否已生效
     * 查修改数据是否生效
     *
     * @param channelId
     * @param channelProperty
     * @param isEffect true为查当前生效的,false查未生效的
     * @return
     */
    JsmsChannelPropertyLog getByChannelAndProperty(Integer channelId, ChannelProperty channelProperty,boolean isEffect);
}
