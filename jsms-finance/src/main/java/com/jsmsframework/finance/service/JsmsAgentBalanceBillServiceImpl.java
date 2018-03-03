package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentBalanceAlarm;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import com.jsmsframework.finance.enums.PaymentType;
import com.jsmsframework.finance.mapper.JsmsAgentBalanceBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author huangwenjie
 * @description 代理商帐户余额收支明细
 * @date 2017-08-08
 */
@Service
public class JsmsAgentBalanceBillServiceImpl implements JsmsAgentBalanceBillService {

    @Autowired
    private JsmsAgentBalanceBillMapper agentBalanceBillMapper;


    @Autowired
    private JsmsAgentBalanceAlarmService jsmsAgentBalanceAlarmService;

    @Autowired
    private JsmsAgentCreditRecordService jsmsAgentCreditRecordService;

    @Override
    @Transactional
    public int insert(JsmsAgentBalanceBill model) {
        return this.agentBalanceBillMapper.insert(model);
    }

    @Override
    @Transactional
    public int insertBatch(List<JsmsAgentBalanceBill> modelList) {
        return this.agentBalanceBillMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int update(JsmsAgentBalanceBill model) {
        JsmsAgentBalanceBill old = this.agentBalanceBillMapper.getById(model.getId());
        if (old == null) {
            return 0;
        }
        return this.agentBalanceBillMapper.update(model);
    }

    @Override
    @Transactional
    public int updateSelective(JsmsAgentBalanceBill model) {
        JsmsAgentBalanceBill old = this.agentBalanceBillMapper.getById(model.getId());
        if (old != null)
            return this.agentBalanceBillMapper.updateSelective(model);
        return 0;
    }

    @Override
    @Transactional
    public JsmsAgentBalanceBill getById(Integer id) {
        JsmsAgentBalanceBill model = this.agentBalanceBillMapper.getById(id);
        return model;
    }

    @Override
    @Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentBalanceBill> list = this.agentBalanceBillMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    @Transactional
    public List queryList(Map params) {
        List<JsmsAgentBalanceBill> list = this.agentBalanceBillMapper.queryListByMap(params);
        return list;
    }

    @Override
    @Transactional
    public int count(Map<String, Object> params) {
        return this.agentBalanceBillMapper.count(params);
    }


    @Override
    public List<Integer> getJustChargeAgentIds(Date checkTime) {
        Set<Integer> agentIds = new HashSet<>();
        JsmsPage page = new JsmsPage();

        page.getParams().put("checkTime", checkTime);
        page.setRows(9999);

        //1. 查找充值和赠送记录的
        page.getParams().put("paymentTypes", Arrays.asList(PaymentType.充值.getValue(), PaymentType.赠送.getValue(), PaymentType.增加授信.getValue()));
        List<JsmsAgentBalanceBill> agentBalanceBills = this.agentBalanceBillMapper.queryList(page);
        List<JsmsAgentBalanceAlarm> agentBalanceAlarms = jsmsAgentBalanceAlarmService.queryList(page).getData();

        for(JsmsAgentBalanceAlarm jsmsAgentBalanceAlarm : agentBalanceAlarms){ //先遍历告警信息
            for (JsmsAgentBalanceBill agentBalanceBill : agentBalanceBills) {  //再遍历余额和赠送记录
                if(jsmsAgentBalanceAlarm.getAgentId().equals(agentBalanceBill.getAgentId())) {
                    if (agentBalanceBill.getCreateTime().getTime() > jsmsAgentBalanceAlarm.getResetTime().getTime()) {
                        agentIds.add(agentBalanceBill.getAgentId());
                        break;
                    }
                }
            }

        }


        //2. 查找授信记录的
//        List<JsmsAgentCreditRecord> agentCreditRecords = jsmsAgentCreditRecordService.queryList(page).getData();
//
//        for(JsmsAgentBalanceAlarm jsmsAgentBalanceAlarm : agentBalanceAlarms) { //先遍历告警信息
//            for (JsmsAgentCreditRecord agentCreditRecord : agentCreditRecords) {//再遍历授信记录
//                if (jsmsAgentBalanceAlarm.getAgentId().equals(agentCreditRecord.getAgentId())) {
//                    if (agentCreditRecord.getCreateTime().getTime() > jsmsAgentBalanceAlarm.getResetTime().getTime()) {
//                        agentIds.add(agentCreditRecord.getAgentId());
//                        break;
//                    }
//                }
//            }
//        }

        return new ArrayList<>(agentIds);
    }

    @Override
    public JsmsPage queryHisCreditList(JsmsPage page) {
        List<JsmsAgentBalanceBill> list = this.agentBalanceBillMapper.queryHisCreditList(page);
        page.setData(list);
        return page;
    }

    /**
     * 获取当前代理商最接近当前流水数据
     *
     * @param agentId
     */
    @Override
    public JsmsAgentBalanceBill getBill4Max(Integer agentId) {
        return this.agentBalanceBillMapper.getBill4Max(agentId);
    }

    @Override
    public JsmsAgentBalanceBill total(Map params) {
        return agentBalanceBillMapper.total(params);
    }

}
