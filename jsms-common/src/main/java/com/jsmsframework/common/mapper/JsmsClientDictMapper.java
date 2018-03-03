package com.jsmsframework.common.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsClientDict;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 用户数据字典表
 * @author huangwenjie
 * @date 2017-11-07
 */
@Repository
public interface JsmsClientDictMapper{

	int insert(JsmsClientDict model);
	
	int insertBatch(List<JsmsClientDict> modelList);

	
	int update(JsmsClientDict model);
	
	int updateSelective(JsmsClientDict model);

    JsmsClientDict getByParamId(Integer paramId);

	List<JsmsClientDict> getByParamType(String paramType);

	List<JsmsClientDict> queryList(JsmsPage<JsmsClientDict> page);

	int count(Map<String,Object> params);

}