package com.jsmsframework.channel.mapper;

import com.jsmsframework.common.enums.channel.ChannelProperty;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.channel.entity.JsmsChannelPropertyLog;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道属性变更记录表
 * @author Don
 * @date 2018-01-13
 */
@Repository
public interface JsmsChannelPropertyLogMapper{

	int insert(JsmsChannelPropertyLog model);
	
	int insertBatch(List<JsmsChannelPropertyLog> modelList);

	
	int update(JsmsChannelPropertyLog model);
	
	int updateSelective(JsmsChannelPropertyLog model);

    JsmsChannelPropertyLog getById(Integer id);

	@SimpleCountSQL
	List<JsmsChannelPropertyLog> queryList(JsmsPage<JsmsChannelPropertyLog> page);

	List<JsmsChannelPropertyLog> findList(Map params);

	int count(Map<String,Object> params);



	JsmsChannelPropertyLog getByChannelAndProperty(@Param("channelId") Integer channelId,@Param("channelProperty") String channelProperty,@Param("isEffectStr") String isEffectStr);

}