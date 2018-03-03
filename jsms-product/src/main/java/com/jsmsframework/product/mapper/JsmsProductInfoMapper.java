package com.jsmsframework.product.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsProductInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 产品信息表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsProductInfoMapper{

	int insert(JsmsProductInfo model);
	
	int insertBatch(List<JsmsProductInfo> modelList);

	
	int update(JsmsProductInfo model);
	
	int updateSelective(JsmsProductInfo model);

    JsmsProductInfo getByProductId(Integer productId);

	List<JsmsProductInfo> getByProductIds(@Param("productIds") Set<Integer> productIds);

	List<JsmsProductInfo> getByProductCode(@Param("productCode")String productCode);

	List<JsmsProductInfo> queryList(JsmsPage<JsmsProductInfo> page);

	/**
	 * 查询未代理商的商品
	 */
	List<JsmsProductInfo> queryNotAgentProductList(JsmsPage<JsmsProductInfo> page);
    /**
	 * 查询未代理商的商品
	 */
	List<JsmsProductInfo> queryNotAgentProductById(Integer agentId);

	int count(Map<String,Object> params);

    List<JsmsProductInfo> getByProductCodes(List<String> productCodes);
}