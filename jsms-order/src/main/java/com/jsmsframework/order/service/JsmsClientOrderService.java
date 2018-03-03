package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsClientOrder;
import com.jsmsframework.order.enums.GroupParamsType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 客户订单表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsClientOrderService {

    int insert(JsmsClientOrder model);
    
    int insertBatch(List<JsmsClientOrder> modelList);

    int update(JsmsClientOrder model);
    
    int updateSelective(JsmsClientOrder model);

    /**
     * 幂等更新Service
     * @param oldModel
     * @param newModel
     * @return
     */
    int updateIdempotent(JsmsClientOrder oldModel,JsmsClientOrder newModel);

    /**
     * 失败返还
     * @return
     */
    int updateRemainQuantity(JsmsClientOrder model);

    /**
     * 获取代理商下的所有客户订单
     * @param agentId
     * @return
     */
    List<JsmsClientOrder> getByAgentId(Integer agentId);
    /**
     * 获取客户的所有订单
     * @param clientId
     * @return
     */
    List<JsmsClientOrder> getByClientId(String clientId);
    /**
     * 获取客户的所有订单
     * @param clientId
     * @return
     */
    List<JsmsClientOrder> getOrderRemainQuantity(String clientId, ArrayList<GroupParamsType> groupParams,Set<Integer> productTypes);
    /**
     * 获取客户的所有订单
     * @return
     */
    JsmsPage<JsmsClientOrder> queryRemainQuantityList(JsmsPage page,String clientId, ArrayList<GroupParamsType> groupParams,Set<Integer> productTypes);

    /**
     * @Description: 查询品牌客户剩余短信条数(过滤剩余短信为0的数据)
     * @Author: tanjiangqiang
     * @Date: 2018/1/15 - 16:05
     * @param page 分页参数
     *
     */
    JsmsPage<JsmsClientOrder> queryRemainQuantity(JsmsPage page,String clientId, ArrayList<GroupParamsType> groupParams,Set<Integer> productTypes);

    JsmsClientOrder getBySubId(Long subId);

    List<JsmsClientOrder> getBySubIds(Set<Long> subIds);

    List<JsmsClientOrder> getByOrderIds(Set<Long> orderIds);

    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsClientOrder> findReturnOrderList(JsmsClientOrder clientOrder);
}
