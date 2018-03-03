package com.jsmsframework.order.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsOemAgentPool;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商短信池
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsOemAgentPoolMapper {

	int insert(JsmsOemAgentPool model);

	int insertBatch(List<JsmsOemAgentPool> modelList);

	int update(JsmsOemAgentPool model);

	int updateSelective(JsmsOemAgentPool model);

	JsmsOemAgentPool getByAgentPoolId(Long agentPoolId);

	JsmsOemAgentPool getByAgentPoolInfo(JsmsOemAgentPool jsmsOemAgentPool);

    List<JsmsOemAgentPool> getListByAgentPoolInfo(JsmsOemAgentPool jsmsOemAgentPool);

	List<JsmsOemAgentPool> queryList(JsmsPage<JsmsOemAgentPool> page);

	int count(Map<String, Object> params);

	List<JsmsOemAgentPool> findList(JsmsOemAgentPool agentPool);

	int reduceAgentPoolRemainNum(Map<String, Object> sqlParams);

    int updateForAddAgentPoolRemainNum(JsmsOemAgentPool model);

	int returnQuantity(@Param("oemAgentPool") JsmsOemAgentPool oemAgentPool, @Param("quantity") Integer quantity);
}