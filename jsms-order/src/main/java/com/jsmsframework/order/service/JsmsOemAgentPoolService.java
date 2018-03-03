package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.order.entity.JsmsOemAgentPool;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description OEM代理商短信池
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsOemAgentPoolService {

    int insert(JsmsOemAgentPool model);
    
    int insertBatch(List<JsmsOemAgentPool> modelList);

    int update(JsmsOemAgentPool model);
    
    int updateSelective(JsmsOemAgentPool model);

    JsmsOemAgentPool getByAgentPoolId(Long agentPoolId);


    /**
     * 根据 agentId、status、productType、operatorCode、areaCode、dueTime、unitPrice 获得单个OEM代理商短信池<br>
     *     存在多个池子的时候返回最后创建的那个（池子ID最大的那个）
     * @param jsmsOemAgentPool
     * @return
     */
    JsmsOemAgentPool getByAgentPoolInfo(JsmsOemAgentPool jsmsOemAgentPool);

    /**
     * 根据 agentId、status、productType、operatorCode、areaCode、dueTime、unitPrice 获得多个OEM代理商短信池<br>
     * @param jsmsOemAgentPool
     * @return
     */
    List<JsmsOemAgentPool> getListByAgentPoolInfo(JsmsOemAgentPool jsmsOemAgentPool);

    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    /**
     * 扣减OEM代理商短信池剩余条数（国际产品扣除剩余金额）
     * @param oemAgentPoolId
     * @param reduceNum
     * @param productType
     */
    BigDecimal updateForReduceAgentPoolRemainNum(Long oemAgentPoolId, BigDecimal reduceNum, Integer productType, Date updateTime);

    /**
     * 增加OEM代理商短信池剩余条数（国际产品扣除增加金额）
     * @param oemAgentPoolId
     * @param reduceNum
     * @param productType
     */
    int updateForAddAgentPoolRemainNum(Long oemAgentPoolId, BigDecimal reduceNum, Integer productType, Date updateTime);
    

    List<JsmsOemAgentPool> findList(JsmsOemAgentPool jsmsOemAgentPool);

    R findPriceList(JsmsOemAgentPool jsmsOemAgentPool);

    R findDuetimeList(JsmsOemAgentPool jsmsOemAgentPool);

}
