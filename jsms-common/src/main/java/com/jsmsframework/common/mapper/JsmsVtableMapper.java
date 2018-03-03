package com.jsmsframework.common.mapper;

import com.jsmsframework.common.entity.JsmsLog;
import com.jsmsframework.common.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface JsmsVtableMapper {

	List<Map<String,Object>> getExistTables(List<String> tableList);

	int getExistTable(@Param("tableName") String tableName);
}