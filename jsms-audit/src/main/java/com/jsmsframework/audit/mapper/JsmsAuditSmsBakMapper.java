package com.jsmsframework.audit.mapper;

import com.jsmsframework.audit.entity.JsmsAuditSms;
import com.jsmsframework.audit.entity.JsmsAuditSmsBak;
import com.jsmsframework.common.dto.JsmsPage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 审核明细表（备份）
 * @author huangwenjie
 * @date 2017-08-29
 */
@Repository
public interface JsmsAuditSmsBakMapper{

	int insert(JsmsAuditSmsBak model);
	
	int insertBatch(List<JsmsAuditSmsBak> modelList);

	int insertFromAuditSms(JsmsAuditSms model);

	int insertBatchFromAuditSms(List<JsmsAuditSms> modelList);

	int delete(Long id);

	int update(JsmsAuditSmsBak model);
	
	int updateSelective(JsmsAuditSmsBak model);

    JsmsAuditSmsBak getById(Long id);

	List<JsmsAuditSmsBak> queryList(JsmsPage<JsmsAuditSmsBak> page);

	int count(Map<String,Object> params);

	/**
	 * 审核备份重复记录
	 * 没有索引，查多少条都一样，这里默认就2000条
	 */
	List<JsmsAuditSmsBak> findRepeatList();

	int batchDeleteAuditSms(int[] auditSmsIdList);

}