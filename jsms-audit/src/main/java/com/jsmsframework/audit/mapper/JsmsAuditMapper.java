package com.jsmsframework.audit.mapper;

import com.jsmsframework.audit.dto.JsmsAuditidAndCreatetime;
import com.jsmsframework.audit.entity.JsmsAudit;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description 审核内容表
 * @author huangwenjie
 * @date 2017-08-28
 */
@Repository
public interface JsmsAuditMapper{

	int insert(JsmsAudit model);
	
	int insertBatch(List<JsmsAudit> modelList);

	int delete(Long auditid);

	int update(JsmsAudit model);

	int updateSelective(JsmsAudit model);

    JsmsAudit getByAuditid(Long auditid);

	List<JsmsAudit> queryList(JsmsPage<JsmsAudit> page);

	int count(Map<String,Object> params);


	/**
	 * 获取需要备份的数据（包含 总数量、最大ID，最小ID）
	 * @return
	 */
	Map<String, Object> getNeedBakCount(@Param("day") int day);

	List<JsmsAudit> findNeedBakList(@Param("min") int min, @Param("max") int max);

	/**
	 * 批量删除
	 *
	 * @param auditIdList
	 */
	int batchDeleteAudit(int[] auditIdList);

	List<JsmsAudit> queryhisList(JsmsPage<JsmsAudit> page);

	int countHis(JsmsPage page);

	List<Map<String,Object>> queryhisAll(Map<String,Object> params);

	List<Long> hasBakButNotDel();

	/**
	 * 获取审核表主表待备份的auditid和createtime，预测1000W数据为600M左右内存  （16+8+8+32）*1000W / 1024 /1024
	 * @param dataDate  数据时间，小于该时间的则满足条件
	 * @return
	 */
    List<JsmsAuditidAndCreatetime> queryAllRemoveAuditidAndCreatetime(Date dataDate);


	/**
	 * 根据联合主键(auditid,createtime)去获取审核信息
	 * @return
	 */
	JsmsAudit getByAuditidAndCreatetime(Map<String, Object> params);

	/**
	 * 验证码
	 * @param params
	 * @return
	 */
	List<JsmsAudit> queryYZMAuditRecord(Map<String, Object> params);
	/**
	 * 重要客户
	 * @param params
	 * @return
	 */
	List<JsmsAudit> queryMajorAuditRecord(Map<String, Object> params);
	/**
	 * 重要客户
	 * @param params
	 * @return
	 */
	List<JsmsAudit> queryOrdinaryAuditRecord(Map<String, Object> params);

	/**
	 * 根据联合主键(auditid,createtime)去删除审核信息
	 * @return
	 */
	int deleteByAuditidAndCreatetime(Map<String, Object> params);

	/**
	 * 只更新状态相关的字段
	 * @param jsmsAudit
	 * @return
	 */
    int updateStatus(JsmsAudit jsmsAudit);

	/**
	 * 
	 * @param params
	 * @return
	 */
	List<JsmsAudit> findList(Map params);

	int updateStatusBatch(Map updateParams);
}