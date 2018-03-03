package com.jsmsframework.access.access.mapper;

import com.jsmsframework.access.access.entity.JsmsAccess;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description Access流水表
 * @author huangwenjie
 * @date 2017-10-20
 */
@Repository
public interface JsmsAccessMapper {

	int insert(JsmsAccess model);
	
	int insertBatch(List<JsmsAccess> modelList);

	
	int update(JsmsAccess model);
	
	int updateSelective(JsmsAccess model);

    JsmsAccess getById(String id);

    @SimpleCountSQL
//	List<JsmsAccess> queryOneDayList(@Param("identify") String identify,@Param("date") String date, @Param("jsmsPage") JsmsPage<JsmsAccess> page);
	List<JsmsAccess> queryOneDayList(JsmsPage<JsmsAccess> page);

	int count(Map<String, Object> params);

}