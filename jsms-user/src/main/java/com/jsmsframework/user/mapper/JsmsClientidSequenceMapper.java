package com.jsmsframework.user.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsClientidSequence;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 客户账号clientid序列表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsClientidSequenceMapper{

	int insert(JsmsClientidSequence model);
	
	int insertBatch(List<JsmsClientidSequence> modelList);

	
	int update(JsmsClientidSequence model);
	
	int updateSelective(JsmsClientidSequence model);

    JsmsClientidSequence getById(Long id);

	List<JsmsClientidSequence> queryList(JsmsPage<JsmsClientidSequence> page);

	int count(Map<String,Object> params);

	JsmsClientidSequence getUnusedRandom();

	String getMax();

	int batchAdd(List<String> list);

	int lock(String clientId);

	int updateStatus(String clientId);
}