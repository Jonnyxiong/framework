package com.jsmsframework.finance.service;

import java.util.Date;
import java.util.Map;
import java.util.List;

import com.jsmsframework.finance.entity.JsmsOnlinePaymentLog;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 在线支付流水日志表
 * @author huangwenjie
 * @date 2017-12-29
 */
public interface JsmsOnlinePaymentLogService {

    int insert(JsmsOnlinePaymentLog model);
    
    int insertBatch(List<JsmsOnlinePaymentLog> modelList);

    int update(JsmsOnlinePaymentLog model);
    
    int updateSelective(JsmsOnlinePaymentLog model);

    JsmsOnlinePaymentLog getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);

    /**
     * 插入一条支付流水记录
     * @param paymentId
     * @param adminId
     * @param createTime
     * @param paymentState
     * @return
     */
    public int insert(String paymentId, Long adminId, Date createTime,Integer paymentState);
}
