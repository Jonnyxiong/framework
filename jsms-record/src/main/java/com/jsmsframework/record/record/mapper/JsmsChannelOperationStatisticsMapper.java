package com.jsmsframework.record.record.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.record.record.entity.JsmsChannelOperationStatistics;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 通道运营统计表
 * @author huangwenjie
 * @date 2018-01-09
 */
@Repository
public interface JsmsChannelOperationStatisticsMapper{

	int insert(JsmsChannelOperationStatistics model);
	
	int insertBatch(List<JsmsChannelOperationStatistics> modelList);

	
	int update(JsmsChannelOperationStatistics model);
	
	int updateSelective(JsmsChannelOperationStatistics model);

    JsmsChannelOperationStatistics getById(Integer id);

	@SimpleCountSQL
	List<JsmsChannelOperationStatistics> queryList(JsmsPage<JsmsChannelOperationStatistics> page);

	List<JsmsChannelOperationStatistics> findList(Map params);

	int count(Map<String, Object> params);
}