package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsOemClientPool;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description OEM代理商客户短信池
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsOemClientPoolService {

    int insert(JsmsOemClientPool model);
    
    int insertBatch(List<JsmsOemClientPool> modelList);

    int update(JsmsOemClientPool model);

    int updateRemainNumber(JsmsOemClientPool model);

    int updateSelective(JsmsOemClientPool model);

    JsmsOemClientPool getByClientPoolId(Long clientPoolId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    /**
     * 根据 clientId、status、productType、operatorCode、areaCode、dueTime、unitPrice 获得OEM代理商客户短信池<br>
     *     存在多个池子的时候返回最后创建的那个（池子ID最大的那个）
     * @param jsmsOemClientPool
     * @return
     */
    JsmsOemClientPool getByClientPoolInfo(JsmsOemClientPool jsmsOemClientPool);

    List<JsmsOemClientPool> getListByClientPoolInfo(JsmsOemClientPool jsmsOemClientPool);

    /**
     * @Description: 查询子账户剩余短信数量详情(过滤剩余数量小于0)
     * @Author: tanjiangqiang
     * @Date: 2018/1/15 - 17:18
     * @param model
     *
     */
    List<JsmsOemClientPool> queryRemainQuantityClientPoolInfo(JsmsOemClientPool model);

    /**
     * 根据OEMClientPool信息和到期时间区间查询信息
     * @param jsmsOemClientPool
     * @param beginStartTime 可空
     * @param endStartTime 可空
     * @return
     */
    List<JsmsOemClientPool> getListByPoolInfoAndDueTimeRange(JsmsOemClientPool jsmsOemClientPool,Date beginStartTime, Date endStartTime);

    void updateForAddClientPoolRemainNum(Long clientPoolId, BigDecimal addNum, Integer productType, Date date);

    BigDecimal updateForReduceClientPoolRemainNum(Long clientPoolId, BigDecimal reduceNum, Integer productType, Date date);

    List<JsmsOemClientPool> findSUMTotal(Set clientIds);
}
