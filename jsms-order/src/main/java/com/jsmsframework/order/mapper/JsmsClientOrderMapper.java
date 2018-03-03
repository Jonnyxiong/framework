package com.jsmsframework.order.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsClientOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 客户订单表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsClientOrderMapper{

	int insert(JsmsClientOrder model);
	
	int insertBatch(List<JsmsClientOrder> modelList);

	
	int update(JsmsClientOrder model);
	
	int updateSelective(JsmsClientOrder model);
	/**
	 * 幂等更新
	 * @param idempotentParams
	 * @return
	 */
	int updateIdempotent(Map<String,JsmsClientOrder> idempotentParams);

	/**
	 * 失败返还
	 * @return
	 */
	int updateRemainQuantity(JsmsClientOrder model);

	List<JsmsClientOrder> getByAgentId(Integer agentId);

	List<JsmsClientOrder> getByClientId(String clientId);

	List<JsmsClientOrder> getOrderRemainQuantity(@Param("clientId")String clientId,@Param("groupParams") String groupParams,@Param("productTypes") Set<Integer> productTypes);

    JsmsClientOrder getBySubId(Long subId);

	List<JsmsClientOrder> getBySubIds(@Param("subIds") Set<Long> subIds);

	List<JsmsClientOrder> getByOrderIds(@Param("orderIds") Set<Long> orderIds);

	List<JsmsClientOrder> queryList(JsmsPage<JsmsClientOrder> page);

	List<JsmsClientOrder> queryRemainQuantityList(JsmsPage<JsmsClientOrder> page);

	/**
	  * @Description: 查询品牌客户剩余短信条数(过滤剩余短信为0的数据)
	  * @Author: tanjiangqiang
	  * @Date: 2018/1/15 - 16:05
	  * @param page 分页参数
	  *
	  */
	List<JsmsClientOrder> queryRemainQuantity(JsmsPage<JsmsClientOrder> page);

	int count(Map<String,Object> params);

	List<JsmsClientOrder> findReturnOrderList(JsmsClientOrder clientOrder);

	int returnQuantity(@Param("clientOrder") JsmsClientOrder clientOrder, @Param("quantity") Integer quantity);
}