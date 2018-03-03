package com.jsmsframework.audit.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsAutoBlackTemplate;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 智能黑模板表
 * @author huangwenjie
 * @date 2017-11-30
 */
@Repository
public interface JsmsAutoBlackTemplateMapper{

	int insert(JsmsAutoBlackTemplate model);
	
	int insertBatch(List<JsmsAutoBlackTemplate> modelList);

	
	int update(JsmsAutoBlackTemplate model);
	
	int updateSelective(JsmsAutoBlackTemplate model);

    JsmsAutoBlackTemplate getByTemplateId(Integer templateId);

	List<JsmsAutoBlackTemplate> queryList(JsmsPage<JsmsAutoBlackTemplate> page);

	int count(Map<String,Object> params);

	int isExist(JsmsAutoBlackTemplate balckTemplate);

	List<JsmsAutoBlackTemplate> queryAll(JsmsAutoBlackTemplate model);

	 int updateState(JsmsAutoBlackTemplate model);

}