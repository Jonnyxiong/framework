package com.jsmsframework.channel.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.channel.entity.JsmsChannelAttributeWeightConfig;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道属性区间权重配置表
 * @author huangwenjie
 * @date 2017-09-28
 */
public interface JsmsChannelAttributeWeightConfigService {

    int insert(JsmsChannelAttributeWeightConfig model);
    
    int insertBatch(List<JsmsChannelAttributeWeightConfig> modelList);

    int update(JsmsChannelAttributeWeightConfig model);
    
    int updateSelective(JsmsChannelAttributeWeightConfig model);

    JsmsChannelAttributeWeightConfig getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    JsmsPage queryList1(JsmsPage page);
    
    int count(Map<String,Object> params);

    int delete(Integer id);

    Map<String, Object> channelConfigCheck(Map<String, String> params);

    List<JsmsChannelAttributeWeightConfig> queryAllBySmSType();
    
}
