package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsOemProductInfo;

import java.util.List;
import java.util.Map;

/**
 * @description OEM产品信息表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsOemProductInfoService {

    int insert(JsmsOemProductInfo model);
    
    int insertBatch(List<JsmsOemProductInfo> modelList);

    int update(JsmsOemProductInfo model);
    
    int updateSelective(JsmsOemProductInfo model);

    JsmsOemProductInfo getByProductId(Integer productId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsPage findList(JsmsPage<JsmsOemProductInfo> page);

    List<JsmsOemProductInfo> getProductInfo(Map<String,Object> params);
}
