package com.jsmsframework.sms.send.mapper;


import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.sms.send.entity.JsmsSubmitProgress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description 短信提交进度表
 * @author Don
 * @date 2018-01-18
 */
@Repository
public interface JsmsSubmitProgressMapper{

	int insert(JsmsSubmitProgress model);
	
	int insertBatch(List<JsmsSubmitProgress> modelList);

	
	int update(JsmsSubmitProgress model);
	
	int updateSelective(JsmsSubmitProgress model);

    JsmsSubmitProgress getById(Integer id);

	@SimpleCountSQL
	List<JsmsSubmitProgress> queryList(JsmsPage<JsmsSubmitProgress> page);

	List<JsmsSubmitProgress> findList(Map params);

	int count(Map<String, Object> params);

	@SimpleCountSQL
	List<JsmsSubmitProgress> queryPageList(JsmsPage<JsmsSubmitProgress> page);

    int updateProgress(@Param("id") int id,@Param("sendNum") int sendNum);

    int finishSubmitState(@Param("now") Date now);
}