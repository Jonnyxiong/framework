package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsAgentProduct;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsAgentProductService {

    int insert(JsmsAgentProduct model);
    
    int insertBatch(List<JsmsAgentProduct> modelList);

    int update(JsmsAgentProduct model);
    
    int updateSelective(JsmsAgentProduct model);

    JsmsAgentProduct getById(Integer id);

    List<JsmsAgentProduct> getByAgentId(Integer agentId);

    List<JsmsAgentProduct> getByAgentIds(Set<Integer> agentIds);

    JsmsPage queryList(JsmsPage page);

    int count(Map<String,Object> params);

    /**
     * 判断是否未代理改产品
     * @param agentId
     * @param productId
     * @return true:未代理,false:已代理
     */
    boolean isNotSaleForThisAgent(Integer agentId, Integer productId);
}
