package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsOemAgentProduct;

import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsOemAgentProductService {

    int insert(JsmsOemAgentProduct model);
    
    int insertBatch(List<JsmsOemAgentProduct> modelList);

    int update(JsmsOemAgentProduct model);
    
    int updateSelective(JsmsOemAgentProduct model);

    JsmsOemAgentProduct getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsOemAgentProduct getByAgentIdAndProductId(JsmsOemAgentProduct model);
}
