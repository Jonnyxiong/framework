package com.jsmsframework.channel.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.channel.entity.JsmsChannelAttributeRealtimeWeight;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道属性实时权重表
 * @author huangwenjie
 * @date 2017-09-28
 */
public interface JsmsChannelAttributeRealtimeWeightService {

    int insert(JsmsChannelAttributeRealtimeWeight model);
    
    int insertBatch(List<JsmsChannelAttributeRealtimeWeight> modelList);

    int update(JsmsChannelAttributeRealtimeWeight model);
    
    int updateSelective(JsmsChannelAttributeRealtimeWeight model);

    JsmsChannelAttributeRealtimeWeight getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsChannelAttributeRealtimeWeight getByChannelId(Integer channelid);

    int updateWeightByChannelId(JsmsChannelAttributeRealtimeWeight model);

    List<JsmsChannelAttributeRealtimeWeight> queryAll();
    
}
