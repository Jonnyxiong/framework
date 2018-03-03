package com.jsmsframework.common.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.common.entity.JsmsDict;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 数据字典表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsDictMapper{

	int insert(JsmsDict model);
	
	int insertBatch(List<JsmsDict> modelList);

	
	int update(JsmsDict model);
	
	int updateSelective(JsmsDict model);

    JsmsDict getByParamId(Integer paramId);

	List<JsmsDict> queryList(JsmsPage<JsmsDict> page);

	int count(Map<String,Object> params);

	List<JsmsDict> findList(Map<String,Object> params);

}