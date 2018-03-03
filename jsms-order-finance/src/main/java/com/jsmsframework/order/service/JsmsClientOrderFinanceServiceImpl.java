package com.jsmsframework.order.service;

import com.jsmsframework.access.access.entity.JsmsClientFailReturn;
import com.jsmsframework.access.enums.RefundStateType;
import com.jsmsframework.access.service.JsmsClientFailReturnService;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.service.JsmsLogService;
import com.jsmsframework.finance.entity.JsmsClientConsumerList;
import com.jsmsframework.finance.enums.ClientConsumeOperateType;
import com.jsmsframework.finance.service.JsmsClientConsumerListService;
import com.jsmsframework.order.entity.JsmsClientOrder;
import com.jsmsframework.order.entity.JsmsOemClientOrder;
import com.jsmsframework.order.entity.JsmsOemClientPool;
import com.jsmsframework.order.enums.OEMClientOrderType;
import com.jsmsframework.order.exception.JsmsOrderFinanceException;
import com.ucpaas.sms.common.util.Collections3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class JsmsClientOrderFinanceServiceImpl implements JsmsClientOrderFinanceService {

    private static final Logger logger = LoggerFactory.getLogger(JsmsClientOrderFinanceServiceImpl.class);

    @Autowired
    private JsmsLogService logService;

    @Autowired
    private JsmsClientConsumerListService jsmsClientConsumerListService;

    @Autowired
    private JsmsClientOrderService jsmsClientOrderService;

    @Autowired
    private JsmsOemClientPoolService jsmsOemClientPoolService;
    @Autowired
    private JsmsOemClientOrderService jsmsOemClientOrderService;
    @Autowired
    private JsmsClientFailReturnService jsmsClientFailReturnService;

    @Override
    public ResultVO returnSendFail(JsmsClientFailReturn clientFailReturn, Long orderId, Long adminId, Date updateTime) {

        // 1.添加 订单记录
        JsmsClientConsumerList clientConsumer = new JsmsClientConsumerList();
        clientConsumer.setClientid(clientFailReturn.getClientid());
        clientConsumer.setAreaCode(clientFailReturn.getAreaCode());
        clientConsumer.setConsumerDate(clientFailReturn.getDate());
        clientConsumer.setDueTime(clientFailReturn.getDueTime());
        clientConsumer.setOperateTime(updateTime);
        clientConsumer.setOperateType(ClientConsumeOperateType.短信失败返还.getValue());
        clientConsumer.setOperator(adminId);
        clientConsumer.setOperatorCode(clientFailReturn.getOperatorCode());
        clientConsumer.setOrderId(orderId);
        clientConsumer.setProductType(clientFailReturn.getProductType());
        clientConsumer.setRemark(formatRemark(clientFailReturn.getDate(), clientFailReturn.getReturnNumber()).toString());
        clientConsumer.setSmsNumber(clientFailReturn.getReturnNumber());
        clientConsumer.setUnitPrice(clientFailReturn.getUnitPrice());
        clientConsumer.setSubId(Long.valueOf(clientFailReturn.getSubId()));

        // 先插入 后更新
        int insert = jsmsClientConsumerListService.insert(clientConsumer);
        if (insert < 1) {
            throw new JsmsOrderFinanceException("添加返还记录失败");
        }
        // 2. 更新剩余量
        JsmsClientOrder updateMode = new JsmsClientOrder();
        updateMode.setSubId(clientConsumer.getSubId().toString());
        updateMode.setStatus(1);
        updateMode.setUpdateTime(updateTime);
        updateMode.setRemainQuantity(BigDecimal.valueOf(clientConsumer.getSmsNumber()));
        int updateRemainQuantity = jsmsClientOrderService.updateRemainQuantity(updateMode);
        if (updateRemainQuantity < 1) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        // 3 更新为未返还状态为已返还
        JsmsClientFailReturn model = new JsmsClientFailReturn();
        model.setId(clientFailReturn.getId());
        model.setRefundState(RefundStateType.已退费.getValue());
        int update = jsmsClientFailReturnService.updateSelective(model);
        if (update < 1) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        return ResultVO.successDefault();
    }

    @Override
    public ResultVO returnSendFail(JsmsClientFailReturn clientFailReturn, List<Integer> waitUpdateFailIds, Long orderId, Long adminId, Date updateTime) {
        if (Collections3.isEmpty(waitUpdateFailIds)) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        // 1.添加 订单记录
        JsmsClientConsumerList clientConsumer = new JsmsClientConsumerList();
        clientConsumer.setClientid(clientFailReturn.getClientid());
        clientConsumer.setAreaCode(clientFailReturn.getAreaCode());
        clientConsumer.setConsumerDate(clientFailReturn.getDate());
        clientConsumer.setDueTime(clientFailReturn.getDueTime());
        clientConsumer.setOperateTime(updateTime);
        clientConsumer.setOperateType(ClientConsumeOperateType.短信失败返还.getValue());
        clientConsumer.setOperator(adminId);
        clientConsumer.setOperatorCode(clientFailReturn.getOperatorCode());
        clientConsumer.setOrderId(orderId);
        clientConsumer.setProductType(clientFailReturn.getProductType());
        clientConsumer.setRemark(formatRemark(clientFailReturn.getDate(), clientFailReturn.getReturnNumber()).toString());
        clientConsumer.setSmsNumber(clientFailReturn.getReturnNumber());
        clientConsumer.setUnitPrice(clientFailReturn.getUnitPrice());
        clientConsumer.setSubId(Long.valueOf(clientFailReturn.getSubId()));

        // 先插入 后更新
        int insert = jsmsClientConsumerListService.insert(clientConsumer);
        if (insert < 1) {
            throw new JsmsOrderFinanceException("添加返还记录失败");
        }

        // 2. 更新剩余量
        JsmsClientOrder updateMode = new JsmsClientOrder();
        updateMode.setSubId(clientConsumer.getSubId().toString());
        updateMode.setStatus(1);
        updateMode.setUpdateTime(updateTime);
        updateMode.setRemainQuantity(BigDecimal.valueOf(clientConsumer.getSmsNumber()));
        int updateRemainQuantity = jsmsClientOrderService.updateRemainQuantity(updateMode);
        if (updateRemainQuantity < 1) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        // 3 更新为未返还状态为已返还
        int update = jsmsClientFailReturnService.updateStatus(RefundStateType.已退费.getValue(), waitUpdateFailIds);
        if (update != waitUpdateFailIds.size()) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        return ResultVO.successDefault();
    }

    @Override
    public ResultVO returnOemSendFail(JsmsClientFailReturn clientFailReturn, Integer agentId, Long orderId, Long orderNo, Long adminId, Date updateTime) {

        long subId = Long.valueOf(clientFailReturn.getSubId());
        // 1.添加 订单记录
        JsmsOemClientOrder oemClientOrder = new JsmsOemClientOrder();
        oemClientOrder.setAreaCode(clientFailReturn.getAreaCode());
        oemClientOrder.setAgentId(agentId);
        oemClientOrder.setClientId(clientFailReturn.getClientid());
        oemClientOrder.setClientPoolId(Long.valueOf(clientFailReturn.getSubId()));
        oemClientOrder.setCreateTime(updateTime);
        oemClientOrder.setDueTime(clientFailReturn.getDueTime());
        oemClientOrder.setOperatorCode(clientFailReturn.getOperatorCode());
        oemClientOrder.setOrderId(orderId);
        oemClientOrder.setOrderNo(orderNo);
        oemClientOrder.setOrderNumber(clientFailReturn.getReturnNumber());
        oemClientOrder.setConsumerDate(clientFailReturn.getDate());
        oemClientOrder.setOperator(adminId);
        oemClientOrder.setOrderType(OEMClientOrderType.短信失败返还.getValue());
        oemClientOrder.setProductType(clientFailReturn.getProductType());
        oemClientOrder.setUnitPrice(clientFailReturn.getUnitPrice());

        oemClientOrder.setRemark(formatRemark(clientFailReturn.getDate(), clientFailReturn.getReturnNumber()).toString());
        int insert = jsmsOemClientOrderService.insert(oemClientOrder);
        if (insert < 1)
            throw new JsmsOrderFinanceException("添加返还记录失败");

        // 2. 更新剩余量
        JsmsOemClientPool oemClientPool = jsmsOemClientPoolService.getByClientPoolId(subId);

        oemClientPool.setStatus(0);
        oemClientPool.setUpdateTime(updateTime);
        oemClientPool.setClientPoolId(subId);
        oemClientPool.setRemainNumber(clientFailReturn.getReturnNumber());

        int updateRemainNumber = jsmsOemClientPoolService.updateRemainNumber(oemClientPool);
        if (updateRemainNumber < 1) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        // 3 更新为未返还状态为已返还
        JsmsClientFailReturn model = new JsmsClientFailReturn();
        model.setId(clientFailReturn.getId());
        model.setRefundState(RefundStateType.已退费.getValue());
        int update = jsmsClientFailReturnService.updateSelective(model);
        if (update < 1) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        return ResultVO.successDefault();
    }

    @Override
    public ResultVO returnOemSendFail(JsmsClientFailReturn clientFailReturn, List<Integer> waitUpdateFailIds,
                                      Integer agentId, Long orderId, Long orderNo, Long adminId, Date updateTime) {
        if (Collections3.isEmpty(waitUpdateFailIds)) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        long subId = Long.valueOf(clientFailReturn.getSubId());

        // 1.添加 订单记录
        JsmsOemClientOrder oemClientOrder = new JsmsOemClientOrder();
        oemClientOrder.setAreaCode(clientFailReturn.getAreaCode());
        oemClientOrder.setAgentId(agentId);
        oemClientOrder.setClientId(clientFailReturn.getClientid());
        oemClientOrder.setClientPoolId(Long.valueOf(clientFailReturn.getSubId()));
        oemClientOrder.setCreateTime(updateTime);
        oemClientOrder.setDueTime(clientFailReturn.getDueTime());
        oemClientOrder.setOperatorCode(clientFailReturn.getOperatorCode());
        oemClientOrder.setOrderId(orderId);
        oemClientOrder.setOrderNo(orderNo);
        oemClientOrder.setOrderNumber(clientFailReturn.getReturnNumber());
        oemClientOrder.setConsumerDate(clientFailReturn.getDate());
        oemClientOrder.setOperator(adminId);
        oemClientOrder.setOrderType(OEMClientOrderType.短信失败返还.getValue());
        oemClientOrder.setProductType(clientFailReturn.getProductType());
        oemClientOrder.setUnitPrice(clientFailReturn.getUnitPrice());

        oemClientOrder.setRemark(formatRemark(clientFailReturn.getDate(), clientFailReturn.getReturnNumber()).toString());
        int insert = jsmsOemClientOrderService.insert(oemClientOrder);
        if (insert < 1)
            throw new JsmsOrderFinanceException("添加返还记录失败");

        // 2. 更新剩余量
        JsmsOemClientPool oemClientPool = jsmsOemClientPoolService.getByClientPoolId(subId);
        oemClientPool.setStatus(0);
        oemClientPool.setUpdateTime(updateTime);
        oemClientPool.setClientPoolId(subId);
        oemClientPool.setRemainNumber(clientFailReturn.getReturnNumber());

        int updateRemainNumber = jsmsOemClientPoolService.updateRemainNumber(oemClientPool);
        if (updateRemainNumber < 1) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        // 3 更新为未返还状态为已返还
        int update = jsmsClientFailReturnService.updateStatus(RefundStateType.已退费.getValue(), waitUpdateFailIds);
        if (update != waitUpdateFailIds.size()) {
            throw new JsmsOrderFinanceException("返还失败");
        }

        return ResultVO.successDefault();
    }

    private StringBuilder formatRemark(Integer date, int returnNum) {
        if (date != null) {
            StringBuilder dateFormat = new StringBuilder(date.toString());
            dateFormat = dateFormat.insert(4, "-");
            dateFormat = dateFormat.insert(7, "-");
            StringBuilder stringBuilder = new StringBuilder("返还");
            stringBuilder.append(dateFormat)
                    .append("失败条数")
                    .append(returnNum);
            return stringBuilder;
        }
        return null;
    }

}
