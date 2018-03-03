package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsProductInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 产品信息表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsProductInfoService {

    int insert(JsmsProductInfo model);
    
    int insertBatch(List<JsmsProductInfo> modelList);

    int update(JsmsProductInfo model);
    
    int updateSelective(JsmsProductInfo model);

    JsmsProductInfo getByProductId(Integer productId);

    List<JsmsProductInfo> getByProductIds(Set<Integer> productIds);

    List<JsmsProductInfo> getByProductCode(String productCode);

    List<JsmsProductInfo> getByProductCodes(List<String> productCodes);

     /**
     * 查询未代理商的商品
     */
    JsmsPage queryNotAgentProductList(JsmsPage page,Integer agentId);   /**
     * 查询未代理商的商品
     */
    List<JsmsProductInfo> queryNotAgentProductList(Integer agentId);

    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    /**
     * 查询该客户的代理商被代理的产品
     * @param page
     * @param agentId
     * @param clientid
     * @return
     */
    JsmsPage queryProxiedList(JsmsPage page,Integer agentId,String clientid) ;
    
}
