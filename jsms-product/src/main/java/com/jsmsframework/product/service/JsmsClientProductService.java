package com.jsmsframework.product.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.product.entity.JsmsClientProduct;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 客户产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsClientProductService {

    int insert(JsmsClientProduct model);
    
    int insertBatch(List<JsmsClientProduct> modelList);

    int update(JsmsClientProduct model);
    
    int updateSelective(JsmsClientProduct model);

    JsmsClientProduct getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsClientProduct getByClientidAndProductId(Integer agentId, String clientid, Integer productId);
}
