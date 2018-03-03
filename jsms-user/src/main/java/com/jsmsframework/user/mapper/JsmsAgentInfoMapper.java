package com.jsmsframework.user.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsAgentInfoMapper{

	int insert(JsmsAgentInfo model);
	
	int insertBatch(List<JsmsAgentInfo> modelList);

	int update(JsmsAgentInfo model);
	
	int updateSelective(JsmsAgentInfo model);

    JsmsAgentInfo getByAgentId(Integer agentId);

    JsmsAgentInfo getByAdminId(Long adminId);

	List<JsmsAgentInfo> getByAgentIds(@Param("agentIds") Set agentIds);

	List<JsmsAgentInfo> getByAgentIdsAndType(@Param("agentType") Integer agentType, @Param("agentIds") Set agentIds);

	List<JsmsAgentInfo> queryList(JsmsPage<JsmsAgentInfo> page);

	List<JsmsAgentInfo> findList(JsmsAgentInfo model);

	// 根据权限获取
	List<JsmsAgentInfo> findListByRight(Map<String, Object> params);

	int count(Map<String,Object> params);

	List<JsmsAgentInfo> getByAgentName(String agentName);

    List<JsmsAgentInfo> queryListForSale(JsmsPage<JsmsAgentInfo> page);

    List<Integer> queryAgentIdsByParams(Map<String,Object> params);

	List<JsmsAgentInfo> queryAll(Map<String,Object> params);

	//根据代理商id获取代理商类型
	int getAgentTypeByAgentId(@Param("agentId") Integer agentId);

	List<JsmsAgentInfo> getByBelongSale(Long belongSale);

	List<JsmsAgentInfo> getAgentInfoNotInInvoiceAuth(JsmsPage<JsmsAgentInfo> page);

	/**
	 * 根据代理商类型（webId）加载所有的代理商信息
	 * @param webId
	 * @return
	 */
	List<JsmsAgentInfo> loadAllForSelect(@Param("webId") String webId);
}