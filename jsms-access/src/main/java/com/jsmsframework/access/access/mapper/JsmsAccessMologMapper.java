package com.jsmsframework.access.access.mapper;

import com.jsmsframework.access.access.entity.JsmsAccessMolog;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 客户上行明细表
 * @author huangwenjie
 * @date 2017-11-28
 */
@Repository
public interface JsmsAccessMologMapper{

	int insert(JsmsAccessMolog model);
	
	int insertBatch(List<JsmsAccessMolog> modelList);

	int update(JsmsAccessMolog model);
	
	int updateSelective(JsmsAccessMolog model);

    JsmsAccessMolog getByMoid(String moid);

    JsmsAccessMolog getByMoidWithSuffix(@Param("moid") String moid,@Param("dateSuffix") String dateSuffix);

    @SimpleCountSQL
	List<JsmsAccessMolog> queryList(JsmsPage<JsmsAccessMolog> page);

	int count(Map<String, Object> params);

}