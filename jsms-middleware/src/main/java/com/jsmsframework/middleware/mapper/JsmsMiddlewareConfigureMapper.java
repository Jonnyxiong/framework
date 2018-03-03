package com.jsmsframework.middleware.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.middleware.entity.JsmsMiddlewareConfigure;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 中间件配置信息表
 * @author huangwenjie
 * @date 2017-11-04
 */
@Repository
public interface JsmsMiddlewareConfigureMapper{

	int insert(JsmsMiddlewareConfigure model);
	
	int insertBatch(List<JsmsMiddlewareConfigure> modelList);

	
	int update(JsmsMiddlewareConfigure model);
	
	int updateSelective(JsmsMiddlewareConfigure model);

    JsmsMiddlewareConfigure getByMiddlewareId(Integer middlewareId);

	List<JsmsMiddlewareConfigure> queryList(JsmsPage<JsmsMiddlewareConfigure> page);

	int count(Map<String,Object> params);

}