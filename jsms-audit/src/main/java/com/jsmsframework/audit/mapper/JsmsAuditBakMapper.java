package com.jsmsframework.audit.mapper;

import com.jsmsframework.audit.entity.JsmsAudit;
import com.jsmsframework.audit.entity.JsmsAuditBak;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 审核内容表（备份）
 * @author huangwenjie
 * @date 2017-08-29
 */
@Repository
public interface JsmsAuditBakMapper{

	int insert(JsmsAuditBak model);

	int delete(@Param("auditid") Long auditid);

	int insertBatch(List<JsmsAuditBak> modelList);

	int insertFromAudit(JsmsAudit model);

	int insertBatchFromAudit(List<JsmsAudit> modelList);

	int update(JsmsAuditBak model);
	
	int updateSelective(JsmsAuditBak model);

    JsmsAuditBak getByAuditid(Long auditid);

	List<JsmsAuditBak> queryList(JsmsPage<JsmsAuditBak> page);

	int count(Map<String,Object> params);

	/**
	 * 审核备份重复记录
	 * 没有索引，查多少条都一样，这里默认就2000条
	 */
	List<JsmsAuditBak> findRepeatList();

	/**
	 * 批量删除
	 *
	 * @param auditIdList
	 */
	int batchDeleteAudit(int[] auditIdList);
	List<Map<String,Object>> queryhisAll(Map<String,Object> params);

    int insertWithTableName(Map params);
}