package com.jsmsframework.access.access.mapper;

import com.jsmsframework.access.access.entity.JsmsAccessSendStat;
import com.jsmsframework.common.dto.JsmsPage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 客户发送量表
 * @author huangwenjie
 * @date 2017-10-16
 */
@Repository
public interface JsmsAccessSendStatMapper{

	int insert(JsmsAccessSendStat model);
	
	int insertBatch(List<JsmsAccessSendStat> modelList);

	
	int update(JsmsAccessSendStat model);
	
	int updateSelective(JsmsAccessSendStat model);

    JsmsAccessSendStat getById(Integer id);

	List<JsmsAccessSendStat> queryList(JsmsPage<JsmsAccessSendStat> page);

	int count(Map<String, Object> params);

    List<JsmsAccessSendStat> querySumList(JsmsPage<JsmsAccessSendStat> page);
}