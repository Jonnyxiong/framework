package com.jsmsframework.channel.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.channel.entity.JsmsChannelPoolPolicy;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道池策略表
 * @author huangwenjie
 * @date 2017-09-28
 */
public interface JsmsChannelPoolPolicyService {

    int insert(JsmsChannelPoolPolicy model);
    
    int insertBatch(List<JsmsChannelPoolPolicy> modelList);

    int update(JsmsChannelPoolPolicy model);
    
    int updateSelective(JsmsChannelPoolPolicy model);

    JsmsChannelPoolPolicy getByPolicyId(Long policyId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    int updatedefault();
    
}
