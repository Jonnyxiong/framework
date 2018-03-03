package com.jsmsframework.channel.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.channel.entity.JsmsKeywordList;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 关键字表
 * @author huangwenjie
 * @date 2017-12-06
 */
@Repository
public interface JsmsKeywordListMapper{

	int insert(JsmsKeywordList model);
	
	int insertBatch(List<JsmsKeywordList> modelList);

	
	int update(JsmsKeywordList model);
	
	int updateSelective(JsmsKeywordList model);

    JsmsKeywordList getById(Long id);

	List<JsmsKeywordList> queryList(JsmsPage<JsmsKeywordList> page);

	int count(Map<String,Object> params);

	List<String> getAllKeywordList();
}