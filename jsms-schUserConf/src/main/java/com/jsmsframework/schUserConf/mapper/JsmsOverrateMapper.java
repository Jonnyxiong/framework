package com.jsmsframework.schUserConf.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.schUserConf.entity.JsmsOverrate;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 模板超频表
 * @author huangwenjie
 * @date 2017-09-27
 */
@Repository
public interface JsmsOverrateMapper{

	int insert(JsmsOverrate model);
	
	int insertBatch(List<JsmsOverrate> modelList);

	
	int update(JsmsOverrate model);
	
	int updateSelective(JsmsOverrate model);

    JsmsOverrate getById(Integer id);

	List<JsmsOverrate> queryList(JsmsPage<JsmsOverrate> page);

	int count(Map<String,Object> params);

	int delete(Integer id);

	int updateState(Map<String,Object> param);

	Map<String, Object> checkExist(Map<String,Object> param);

}