package com.jsmsframework.audit.mapper;

import com.jsmsframework.common.dto.R;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsOverrateKeyword;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 超频关键字表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Repository
public interface JsmsOverrateKeywordMapper{

	int insert(JsmsOverrateKeyword model);
	
	int insertBatch(List<JsmsOverrateKeyword> modelList);

	
	int update(JsmsOverrateKeyword model);
	
	int updateSelective(JsmsOverrateKeyword model);

    JsmsOverrateKeyword getById(Integer id);

	List<JsmsOverrateKeyword> queryList(JsmsPage<JsmsOverrateKeyword> page);

	int count(Map<String,Object> params);


	Map<String, Object> checkExist(Map<String,Object> param);

	int delOverrate(Integer id);
}