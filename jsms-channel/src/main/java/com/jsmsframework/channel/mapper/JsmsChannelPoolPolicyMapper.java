package com.jsmsframework.channel.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.channel.entity.JsmsChannelPoolPolicy;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道池策略表
 * @author huangwenjie
 * @date 2017-09-28
 */
@Repository
public interface JsmsChannelPoolPolicyMapper{

	int insert(JsmsChannelPoolPolicy model);
	
	int insertBatch(List<JsmsChannelPoolPolicy> modelList);

	
	int update(JsmsChannelPoolPolicy model);
	
	int updateSelective(JsmsChannelPoolPolicy model);

    JsmsChannelPoolPolicy getByPolicyId(Long policyId);

	List<JsmsChannelPoolPolicy> queryList(JsmsPage<JsmsChannelPoolPolicy> page);

	int count(Map<String,Object> params);

	int updatedefault();

}