package com.jsmsframework.common.service;

import com.jsmsframework.common.enums.LogConstant.LogType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 视图V_table相关判断表
 * @Time 2017-12-25
 * @author Don
 */
public interface JsmsVtableService {

	/**
	 * 根据多个表名，返回存在的表名集合
	 * @param tableList
	 * @return
	 */
	List<Map<String,Object>> getExistTables(List<String> tableList);

	/**
	 * 根据表名判断是否存在表
	 * @param tableName
	 * @return
	 */
	boolean getExistTable(String tableName);
}
