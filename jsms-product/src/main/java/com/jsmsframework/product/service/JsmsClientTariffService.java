package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsClientTariff;

import java.util.List;
import java.util.Map;

/**
 * @description 国际短信费率表
 * @author huangwenjie
 * @date 2017-11-27
 */
public interface JsmsClientTariffService {

    int insert(JsmsClientTariff model);
    
    int insertBatch(List<JsmsClientTariff> modelList);

    int update(JsmsClientTariff model);
    
    int updateSelective(JsmsClientTariff model);

    JsmsClientTariff getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);
    
}
