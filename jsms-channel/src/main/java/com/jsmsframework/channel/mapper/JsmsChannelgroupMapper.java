package com.jsmsframework.channel.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.channel.entity.JsmsChannelgroup;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道组明细表
 * @author huangwenjie
 * @date 2017-09-20
 */
@Repository
public interface JsmsChannelgroupMapper{

	int insert(JsmsChannelgroup model);
	
	int insertBatch(List<JsmsChannelgroup> modelList);

	
	int update(JsmsChannelgroup model);
	
	int updateSelective(JsmsChannelgroup model);

    JsmsChannelgroup getByChannelgroupid(Integer channelgroupid);

	List<JsmsChannelgroup> queryList(JsmsPage<JsmsChannelgroup> page);

	int count(Map<String,Object> params);

	Map<String, Object> queryOperatorstypeByChannelId(Integer channelgroupid);
}