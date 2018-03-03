package com.jsmsframework.common.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsSystemErrorDesc;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 平台错误码对应表
 * @author huangwenjie
 * @date 2017-11-25
 */
@Repository
public interface JsmsSystemErrorDescMapper{

	int insert(JsmsSystemErrorDesc model);
	
	int insertBatch(List<JsmsSystemErrorDesc> modelList);

	
	int update(JsmsSystemErrorDesc model);
	
	int updateSelective(JsmsSystemErrorDesc model);

    JsmsSystemErrorDesc getById(Integer id);

	List<JsmsSystemErrorDesc> queryList(JsmsPage<JsmsSystemErrorDesc> page);

	int count(Map<String,Object> params);

	JsmsSystemErrorDesc getBySyscode(String syscode);

    List<JsmsSystemErrorDesc> queryAllList(Map<String, Object> params);
}