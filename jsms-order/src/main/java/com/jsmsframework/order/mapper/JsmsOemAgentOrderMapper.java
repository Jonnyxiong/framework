package com.jsmsframework.order.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsOemAgentOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商订单
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsOemAgentOrderMapper{

	int insert(JsmsOemAgentOrder model);
	
	int insertBatch(List<JsmsOemAgentOrder> modelList);

	
	int update(JsmsOemAgentOrder model);
	
	int updateSelective(JsmsOemAgentOrder model);

    JsmsOemAgentOrder getByOrderId(Long orderId);

	List<JsmsOemAgentOrder> getSumByOrderType(@Param("orderType") Integer orderType, @Param("agentId")Integer agentId);

	List<JsmsOemAgentOrder> queryList(JsmsPage<JsmsOemAgentOrder> page);

	int count(Map<String,Object> params);

}