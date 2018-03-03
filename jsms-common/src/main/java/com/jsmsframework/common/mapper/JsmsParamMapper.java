package com.jsmsframework.common.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsParam;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 参数表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsParamMapper{

	int insert(JsmsParam model);
	
	int insertBatch(List<JsmsParam> modelList);

	
	int update(JsmsParam model);
	
	int updateSelective(JsmsParam model);

    JsmsParam getByParamId(Long paramId);

	List<JsmsParam> getByParamKey(String paramKey);

	List<JsmsParam> queryList(JsmsPage<JsmsParam> page);

	int count(Map<String,Object> params);

	/*JsmsParam getByParamKey(String paramKey);*/

}