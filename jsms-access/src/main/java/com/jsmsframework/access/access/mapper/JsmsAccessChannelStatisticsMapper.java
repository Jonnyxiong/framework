package com.jsmsframework.access.access.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.access.access.entity.JsmsAccessChannelStatistics;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 客户发送统计表
 * @author huangwenjie
 * @date 2017-10-16
 */
@Repository
public interface JsmsAccessChannelStatisticsMapper{

	int insert(JsmsAccessChannelStatistics model);
	
	int insertBatch(List<JsmsAccessChannelStatistics> modelList);

	
	int update(JsmsAccessChannelStatistics model);
	
	int updateSelective(JsmsAccessChannelStatistics model);

    JsmsAccessChannelStatistics getById(Long id);

	List<JsmsAccessChannelStatistics> queryList(JsmsPage<JsmsAccessChannelStatistics> page);

	int count(Map<String, Object> params);

}