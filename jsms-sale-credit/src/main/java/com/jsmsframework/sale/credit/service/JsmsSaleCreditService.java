package com.jsmsframework.sale.credit.service;

import com.jsmsframework.common.dto.R;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Don on 2017/11/15.
 */
public interface JsmsSaleCreditService {

    /**
     * 授信代理商相关
     * @param opId
     * @param opType
     * @param agentId
     * @param money
     * @param remark
     * @return
     */
    R creditForAgent(Long opId, Integer opType, Integer agentId, BigDecimal money, String remark);

    /**
     * 授信操作销售相关
     * @param opId
     * @param opType
     * @param agentId
     * @param saleId
     * @param money
     * @param remark
     * @return
     */
    R creditForSale(Long opId, Integer opType, Integer agentId, Long saleId, BigDecimal money, String remark);


    Map<String,Object> agentUpdateByBalanceOP(String bType,Long opId,Integer agnetId, String clientId, BigDecimal money, String remark);


    /**
     * 归属销售转变授信变化
     * @param oldSaleId
     * @param newSaleId
     * @return
     */
    R belongSaleChaned(Long oldSaleId,Long newSaleId);

    /**
     * 单个代理商 归属销售转变授信变化
     * @param oldSaleId
     * @param newSaleId
     * @return
     */
    R singleBelongSaleChaned(Long oldSaleId,Long newSaleId,Integer agentId);

    /**
     *对于代理商余额操作变化
     * 2017年11月30日
     * create by Don
     * 重构方法
     * @param params
     * @return
     */
    Map<String,Object> agentBalanceForOpreation(Map<String, String> params);
}
