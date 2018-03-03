package com.jsmsframework.sysConfig.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.sysConfig.entity.JsmsWhiteList;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 黑名单表
 * @author Don
 * @date 2018-01-10
 */
@Repository
public interface JsmsWhiteListMapper{

	int insert(JsmsWhiteList model);
	
	int insertBatch(List<JsmsWhiteList> modelList);

	
	int update(JsmsWhiteList model);
	
	int updateSelective(JsmsWhiteList model);

    JsmsWhiteList getById(Long id);

	@SimpleCountSQL
	List<JsmsWhiteList> queryList(JsmsPage<JsmsWhiteList> page);

	List<JsmsWhiteList> findList(Map params);

	int count(Map<String,Object> params);

	JsmsWhiteList getByPhone(@Param("phone") String phone);


	int deleteWhiteList(Long id);

}