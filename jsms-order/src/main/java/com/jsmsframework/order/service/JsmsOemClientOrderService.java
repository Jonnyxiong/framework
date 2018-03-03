package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.dto.OemClientOrderListTotal;
import com.jsmsframework.order.entity.JsmsOemClientOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description OEM代理商客户订单
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsOemClientOrderService {

    int insert(JsmsOemClientOrder model);
    
    int insertBatch(List<JsmsOemClientOrder> modelList);

    int update(JsmsOemClientOrder model);
    
    int updateSelective(JsmsOemClientOrder model);

    JsmsOemClientOrder getByOrderId(Long orderId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    OemClientOrderListTotal queryOemClientOrderListTotal(Map params);


    JsmsPage queryOemClientOrderDTO(JsmsPage page);

    List<String> getNewBuyer(Date checkTime, Set<String> clientIds);

    List<String> getNewBuyerNew(Date checkTime, List<String> productTypes, String alarmType, Set<String> clientIds);

    Integer queryOEMConsumeTotal(Map params);
}
