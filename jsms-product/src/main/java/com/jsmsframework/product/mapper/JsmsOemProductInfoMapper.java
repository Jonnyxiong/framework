package com.jsmsframework.product.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsOemProductInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description OEM产品信息表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsOemProductInfoMapper{

	int insert(JsmsOemProductInfo model);
	
	int insertBatch(List<JsmsOemProductInfo> modelList);

	
	int update(JsmsOemProductInfo model);
	
	int updateSelective(JsmsOemProductInfo model);

    JsmsOemProductInfo getByProductId(Integer productId);

	List<JsmsOemProductInfo> queryList(JsmsPage<JsmsOemProductInfo> page);

	int count(Map<String,Object> params);

	List<JsmsOemProductInfo> findList(JsmsPage<JsmsOemProductInfo> page);

	//根据产品id获取产品部分信息
	public JsmsOemProductInfo getOemProductInfoByProductId(@Param("productId") int productId);

	List<JsmsOemProductInfo> getProductInfo(Map<String,Object> params);
}