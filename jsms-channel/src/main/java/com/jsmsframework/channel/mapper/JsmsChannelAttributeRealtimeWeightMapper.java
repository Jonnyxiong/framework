package com.jsmsframework.channel.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.channel.entity.JsmsChannelAttributeRealtimeWeight;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道属性实时权重表
 * @author huangwenjie
 * @date 2017-09-28
 */
@Repository
public interface JsmsChannelAttributeRealtimeWeightMapper{

	int insert(JsmsChannelAttributeRealtimeWeight model);
	
	int insertBatch(List<JsmsChannelAttributeRealtimeWeight> modelList);

	
	int update(JsmsChannelAttributeRealtimeWeight model);
	
	int updateSelective(JsmsChannelAttributeRealtimeWeight model);

    JsmsChannelAttributeRealtimeWeight getById(Long id);

	List<JsmsChannelAttributeRealtimeWeight> queryList(JsmsPage<JsmsChannelAttributeRealtimeWeight> page);

	int count(Map<String,Object> params);

	JsmsChannelAttributeRealtimeWeight getByChannelId(Integer channelid);

	int updateWeightByChannelId(JsmsChannelAttributeRealtimeWeight model);

	List<JsmsChannelAttributeRealtimeWeight> queryAll();

}