package com.jsmsframework.channel.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.channel.entity.JsmsChannelgroup;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道组明细表
 * @author huangwenjie
 * @date 2017-09-20
 */
public interface JsmsChannelgroupService {

    int insert(JsmsChannelgroup model);
    
    int insertBatch(List<JsmsChannelgroup> modelList);

    int update(JsmsChannelgroup model);
    
    int updateSelective(JsmsChannelgroup model);

    JsmsChannelgroup getByChannelgroupid(Integer channelgroupid);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    Map<String,Object> queryOperatorstypeByChannelId(Integer channelgroupid);
    
}
