package com.jsmsframework.record.record.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.record.record.entity.JsmsRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 短信记录流水表
 * @author Don
 * @date 2018-01-11
 */
@Repository
public interface JsmsRecordMapper {

	int insert(JsmsRecord model);
	
	int insertBatch(List<JsmsRecord> modelList);

	
	int update(JsmsRecord model);
	
	int updateSelective(JsmsRecord model);

    JsmsRecord getBySmsuuid(@Param("smsuuid") String smsuuid, @Param("tableName") String tableName);

	@SimpleCountSQL
	List<JsmsRecord> queryList(JsmsPage<JsmsRecord> page);

	List<JsmsRecord> findList(Map params);

	int count(Map<String, Object> params);

	/**
	 * 获取当前数据库名称
	 * @return
	 */
	String getCurrentDatabaseName();

	/**
	 * 根据数据库名和表名查询表所有的字段
	 * @param tableName
	 * @param recordDatabaseName
	 * @return
	 */
	List<Map<String, String>> getRecordTableSchema(@Param("table_name") String tableName,@Param("record_database_name") String recordDatabaseName);

	/**
	 * 查找失败重发记录
	 * @param page
	 * @return
	 */
	List<JsmsRecord> queryFailRecord(JsmsPage page);
}