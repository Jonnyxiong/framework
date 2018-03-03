package com.jsmsframework.middleware.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.middleware.entity.JsmsMqConfigure;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description MQ配置表
 * @author huangwenjie
 * @date 2017-11-04
 */
@Repository
public interface JsmsMqConfigureMapper{

	int insert(JsmsMqConfigure model);
	
	int insertBatch(List<JsmsMqConfigure> modelList);

	
	int update(JsmsMqConfigure model);
	
	int updateSelective(JsmsMqConfigure model);

    JsmsMqConfigure getByMqId(Integer mqId);

	List<JsmsMqConfigure> queryList(JsmsPage<JsmsMqConfigure> page);

	int count(Map<String,Object> params);

}