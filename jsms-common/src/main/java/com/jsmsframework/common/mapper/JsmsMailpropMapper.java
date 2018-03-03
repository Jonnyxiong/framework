package com.jsmsframework.common.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.common.entity.JsmsMailprop;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 邮件配置表
 * @author huangwenjie
 * @date 2017-11-27
 */
@Repository
public interface JsmsMailpropMapper{

	int insert(JsmsMailprop model);
	
	int insertBatch(List<JsmsMailprop> modelList);

	
	int update(JsmsMailprop model);
	
	int updateSelective(JsmsMailprop model);

    JsmsMailprop getById(Long id);

	List<JsmsMailprop> queryList(JsmsPage<JsmsMailprop> page);

	int count(Map<String,Object> params);

	JsmsMailprop querySmsMailprop(Integer id);
}