package com.jsmsframework.access.access.mapper;

import com.jsmsframework.access.access.entity.JsmsClientOperationStatistics;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 客户运营统计表
 * @author huangwenjie
 * @date 2018-01-09
 */
@Repository
public interface JsmsClientOperationStatisticsMapper{

	int insert(JsmsClientOperationStatistics model);
	
	int insertBatch(List<JsmsClientOperationStatistics> modelList);

	int update(JsmsClientOperationStatistics model);
	
	int updateSelective(JsmsClientOperationStatistics model);

    JsmsClientOperationStatistics getById(Integer id);

	@SimpleCountSQL
	List<JsmsClientOperationStatistics> queryList(JsmsPage<JsmsClientOperationStatistics> page);

	List<JsmsClientOperationStatistics> findList(Map params);

	int count(Map<String, Object> params);

}