package com.jsmsframework.finance.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsUserPriceLog;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 后付费用户价格变更记录
 * @author huangwenjie
 * @date 2017-08-08
 */
@Repository
public interface JsmsUserPriceLogMapper {

	int insert(JsmsUserPriceLog model);
	
	int insertBatch(List<JsmsUserPriceLog> modelList);

	
	int update(JsmsUserPriceLog model);
	
	int updateSelective(JsmsUserPriceLog model);
	
	JsmsUserPriceLog getById(Integer id);
	
	List<JsmsUserPriceLog> queryList(JsmsPage<JsmsUserPriceLog> page);
	
	int count(Map<String, Object> params);

	int updatePrice(JsmsUserPriceLog userPriceLog);

	JsmsUserPriceLog getLatestPrice(JsmsUserPriceLog userPriceLog);

	JsmsUserPriceLog getPriceOnUpdate(JsmsUserPriceLog userPriceLog);

	List<JsmsUserPriceLog> getuserPrice(@Param("clientid") String  clientid, @Param("smstype") Integer smstype);
}