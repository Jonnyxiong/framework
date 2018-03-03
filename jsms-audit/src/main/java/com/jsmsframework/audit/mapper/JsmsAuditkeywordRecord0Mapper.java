package com.jsmsframework.audit.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsAuditkeywordRecord0;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 短信审核与关键字记录分表原始表
 * @author fanghaidong
 * @date 2017-12-25
 */
@Repository
public interface JsmsAuditkeywordRecord0Mapper{

	int insert(JsmsAuditkeywordRecord0 model);
	
	int insertBatch(List<JsmsAuditkeywordRecord0> modelList);

	
	int update(JsmsAuditkeywordRecord0 model);
	
	int updateSelective(JsmsAuditkeywordRecord0 model);

    JsmsAuditkeywordRecord0 getById(Integer id);

	List<JsmsAuditkeywordRecord0> queryList(JsmsPage<JsmsAuditkeywordRecord0> page);

	int count(Map<String,Object> params);

	List<JsmsAuditkeywordRecord0> queryAll(Map<String,Object> params);

}