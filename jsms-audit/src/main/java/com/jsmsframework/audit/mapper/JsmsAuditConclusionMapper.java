package com.jsmsframework.audit.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.audit.entity.JsmsAuditConclusion;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 审核原因表
 * @author huangwenjie
 * @date 2017-09-12
 */
@Repository
public interface JsmsAuditConclusionMapper{

	int insert(JsmsAuditConclusion model);
	
	int insertBatch(List<JsmsAuditConclusion> modelList);

	
	int update(JsmsAuditConclusion model);
	
	int updateSelective(JsmsAuditConclusion model);

    JsmsAuditConclusion getById(Integer id);

	List<JsmsAuditConclusion> queryList(JsmsPage<JsmsAuditConclusion> page);

	int count(Map<String,Object> params);

	List<JsmsAuditConclusion> findAllList(@Param("remark") String remark);
}