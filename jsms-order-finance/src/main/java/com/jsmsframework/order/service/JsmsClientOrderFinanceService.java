package com.jsmsframework.order.service;

import com.jsmsframework.access.access.entity.JsmsClientFailReturn;
import com.jsmsframework.common.dto.ResultVO;

import java.util.Date;
import java.util.List;

public interface JsmsClientOrderFinanceService {

    @Deprecated
    public ResultVO returnSendFail(JsmsClientFailReturn clientFailReturn, Long orderId, Long adminId, Date updateTime);

    public ResultVO returnSendFail(JsmsClientFailReturn clientFailReturn, List<Integer> waitUpdateFailIds, Long orderId, Long adminId, Date updateTime);

    @Deprecated
    public ResultVO returnOemSendFail(JsmsClientFailReturn jsmsClientFailReturn, Integer agentId, Long orderId, Long orderNo, Long adminId, Date updateTime);

    public ResultVO returnOemSendFail(JsmsClientFailReturn jsmsClientFailReturn, List<Integer> waitUpdateFailIds, Integer agentId, Long orderId, Long orderNo, Long adminId, Date updateTime);

}
