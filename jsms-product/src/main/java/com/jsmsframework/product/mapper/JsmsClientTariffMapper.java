package com.jsmsframework.product.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsClientTariff;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 国际短信费率表
 * @author huangwenjie
 * @date 2017-11-27
 */
@Repository
public interface JsmsClientTariffMapper{

	int insert(JsmsClientTariff model);
	
	int insertBatch(List<JsmsClientTariff> modelList);

	
	int update(JsmsClientTariff model);
	
	int updateSelective(JsmsClientTariff model);

    JsmsClientTariff getById(Integer id);

	List<JsmsClientTariff> queryList(JsmsPage<JsmsClientTariff> page);

	int count(Map<String, Object> params);

}