package com.jsmsframework.order.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.dto.OemClientOrderListTotal;
import com.jsmsframework.order.entity.JsmsOemClientOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description OEM代理商客户订单
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsOemClientOrderMapper{

	int insert(JsmsOemClientOrder model);
	
	int insertBatch(List<JsmsOemClientOrder> modelList);

	
	int update(JsmsOemClientOrder model);
	
	int updateSelective(JsmsOemClientOrder model);

    JsmsOemClientOrder getByOrderId(Long orderId);

	List<JsmsOemClientOrder> queryList(JsmsPage<JsmsOemClientOrder> page);

	int count(Map<String,Object> params);

    OemClientOrderListTotal queryOemClientOrderListTotal(Map params);

	List<String> getNewBuyer(@Param("checkTime") Date checkTime, @Param("clientIds") Set clientIds);

	List<String> getNewBuyerNew(@Param("checkTime") Date checkTime, @Param("productTypes") List<String> productTypes, @Param("alarmType") String alarmType, @Param("clientIds") Set clientIds);

	Integer queryOEMConsumeTotal(Map params);
}