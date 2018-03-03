package com.jsmsframework.channel.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.channel.entity.JsmsChannelAttributeWeightConfig;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 通道属性区间权重配置表
 * @author Don
 * @date 2017-09-28
 */
@Repository
public interface JsmsChannelAttributeWeightConfigMapper{

	int insert(JsmsChannelAttributeWeightConfig model);
	
	int insertBatch(List<JsmsChannelAttributeWeightConfig> modelList);

	
	int update(JsmsChannelAttributeWeightConfig model);
	
	int updateSelective(JsmsChannelAttributeWeightConfig model);

    JsmsChannelAttributeWeightConfig getById(Integer id);

	List<JsmsChannelAttributeWeightConfig> queryList(JsmsPage<JsmsChannelAttributeWeightConfig> page);

	List<JsmsChannelAttributeWeightConfig> queryList1(JsmsPage<JsmsChannelAttributeWeightConfig> page);
	int count(Map<String,Object> params);

	List<Map<String,Object>> checkAll(Map<String,String> params);

	int delete(Integer id);

	List<JsmsChannelAttributeWeightConfig> queryAllBySmSType();
}