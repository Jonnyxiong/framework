package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.finance.entity.JsmsAgentAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商帐户表
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAgentAccountService {

    int insert(JsmsAgentAccount model);
    
    int insertBatch(List<JsmsAgentAccount> modelList);

    int update(JsmsAgentAccount model);

    int updateAfterConsume(JsmsAgentAccount model);

    int updateSelective(JsmsAgentAccount model);

    JsmsAgentAccount getByAgentId(Integer agentId);

    ResultVO reduceBalance(Integer agentId, BigDecimal reduceNum);

    public List<JsmsAgentAccount> getByAgentIds(Set agentIds);

    JsmsPage queryList(JsmsPage page);

    List queryList(Map params);

    /**
     * 获取代理商的基本财务信息(可用余额)
     * @param agentId
     * @return
     */
    JsmsAgentAccountServiceImpl.AgentFinance queryBaseFianceInfo(Integer agentId);

    int count(Map<String, Object> params);

    Map<String,Object> querySumBlance(List agentIds);

    List<Map<String,Object>> querySumCreditBySale(Map params);

    /**
     * 考虑事务更新字段,字段正为加，负为减
     * @param model
     * @return
     */
    int updateAccoutForRealTime(JsmsAgentAccount model);

    //不分页使用的list(注意：存风险查全库)
    List<JsmsAgentAccount> findList(Map<String,Object> params);

    /**
     * @param agentId        代理商id
     * @param hasTakeInvoice 已开票金额
     * @param isReduceInit   是否减去已开票初始化金额
     * @Description: 更新代理商已开票金额
     * @Author: tanjiangqiang
     * @Date: 2018/1/24 - 16:19
     */
    int reduceHasTakeInvoice(Integer agentId, BigDecimal hasTakeInvoice, boolean isReduceInit);
}
