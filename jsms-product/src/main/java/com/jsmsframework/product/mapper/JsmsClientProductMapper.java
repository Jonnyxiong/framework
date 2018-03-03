package com.jsmsframework.product.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.product.entity.JsmsClientProduct;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 客户产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsClientProductMapper{

	int insert(JsmsClientProduct model);
	
	int insertBatch(List<JsmsClientProduct> modelList);

	
	int update(JsmsClientProduct model);
	
	int updateSelective(JsmsClientProduct model);

    JsmsClientProduct getById(Integer id);

	List<JsmsClientProduct> queryList(JsmsPage<JsmsClientProduct> page);

	int count(Map<String,Object> params);

}