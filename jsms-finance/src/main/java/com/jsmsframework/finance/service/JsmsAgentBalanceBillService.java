package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huangwenjie
 * @description 代理商帐户余额收支明细
 * @date 2017-08-08
 */
public interface JsmsAgentBalanceBillService {

    int insert(JsmsAgentBalanceBill model);

    int insertBatch(List<JsmsAgentBalanceBill> modelList);

    int update(JsmsAgentBalanceBill model);

    int updateSelective(JsmsAgentBalanceBill model);

    JsmsAgentBalanceBill getById(Integer id);

    JsmsPage queryList(JsmsPage page);

    List queryList(Map params);

    int count(Map<String, Object> params);


    /**
     * 获取刚刚充值(授信)的代理商id
     *
     * @param checkTime
     * @return
     */
    List<Integer> getJustChargeAgentIds(Date checkTime);

/*
* 获取历史授信数据
* */
    JsmsPage queryHisCreditList(JsmsPage page);
    /*
    * 历史授信合计
    * */
    JsmsAgentBalanceBill total(Map<String, String> params);
    /**
     *获取当前代理商最接近当前流水数据
     */
    JsmsAgentBalanceBill getBill4Max(Integer agentId);
}
