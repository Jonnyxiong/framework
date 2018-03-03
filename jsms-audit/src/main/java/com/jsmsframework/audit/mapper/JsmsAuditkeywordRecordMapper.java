package com.jsmsframework.audit.mapper;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsAuditkeywordRecord;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 短信审核与关键字记录表
 * @author huangwenjie
 * @date 2017-09-12
 */
@Repository
public interface JsmsAuditkeywordRecordMapper{

	int insert(JsmsAuditkeywordRecord model);
	
	int insertBatch(List<JsmsAuditkeywordRecord> modelList);

	
	int update(JsmsAuditkeywordRecord model);
	
	int updateSelective(JsmsAuditkeywordRecord model);

    JsmsAuditkeywordRecord getById(Integer id);

	List<JsmsAuditkeywordRecord> queryList(JsmsPage<JsmsAuditkeywordRecord> page);

	int count(Map<String,Object> params);

	List<JsmsAuditkeywordRecord> queryAll(Map<String,Object> params);

	List<JsmsAuditkeywordRecord> queryAllRemoveRecordAndCreatetime(Date date);

	JsmsAuditkeywordRecord queryByIdAndCreateTime(Map<String, Object> params);

	int deleteByIdAndCreatetime(Map<String, Object> params);

	int insertWithTableName(Map params);
}