package com.jsmsframework.finance.service;


import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.exception.JsmsAgentAccountException;
import com.jsmsframework.finance.mapper.JsmsAgentAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商帐户表
 * @author huangwenjie
 * @date 2017-08-08
 */
@Service
public class JsmsAgentAccountServiceImpl implements JsmsAgentAccountService {

    protected static Logger logger = LoggerFactory.getLogger(JsmsAgentAccountServiceImpl.class);

    @Autowired
    private JsmsAgentAccountMapper agentAccountMapper;
    
    @Override
    public int insert(JsmsAgentAccount model) {
        return this.agentAccountMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsAgentAccount> modelList) {
        return this.agentAccountMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentAccount model) {
		JsmsAgentAccount old = this.agentAccountMapper.getByAgentId(model.getAgentId());
		if(old == null){
			return 0;
		}
		return this.agentAccountMapper.update(model); 
    }

	@Override
	@Transactional
    public int updateAfterConsume(JsmsAgentAccount model) {
		JsmsAgentAccount old = this.agentAccountMapper.getByAgentId(model.getAgentId());
		if(old == null){
			return 0;
		}
		return this.agentAccountMapper.updateAfterConsume(model);
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentAccount model) {
		JsmsAgentAccount old = this.agentAccountMapper.getByAgentId(model.getAgentId());
		if(old != null)
        	return this.agentAccountMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsAgentAccount getByAgentId(Integer agentId) {
        JsmsAgentAccount model = this.agentAccountMapper.getByAgentId(agentId);
		return model;
    }

    @Override
	@Transactional
    public ResultVO reduceBalance(Integer agentId, BigDecimal reduceNum){
        JsmsAgentAccount model = this.agentAccountMapper.getByAgentId(agentId);

        /**
         * 可用额度：（1）当余额大于0的时候，可用额度=余额+授信余额；（2）当余额小于0的时候，可用额度=授信余额
         */
        BigDecimal availableAmount;
        BigDecimal balance = model.getBalance();
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            availableAmount = model.getCurrentCredit();
        }else{
            availableAmount = model.getBalance().add(model.getCurrentCredit());
        }
        if(reduceNum.compareTo(availableAmount) > 0 ){
            return ResultVO.failure("您的账户余额不足，请充值!");
        }

        //accumulated_consume += 实际消耗金额(保证事物在sql中进行累加)
        model.setAccumulatedConsume(reduceNum);

        /**
         * 剩余的余额 = 代理商账户余额 - 订单实际金额
         */
//        BigDecimal remainBalance = model.getBalance().subtract(reduceNum);
        BigDecimal currentCredit = null;
        BigDecimal noBackPayment = null;
        if (!(model.getBalance().compareTo(reduceNum) >= 0)) {
            /**
             * 代理商账户余额小于消费余额
             *
             */
            if (model.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                /**
                 * 代理商账户余额大于0
                 * 1、授信余额=当前授信余额-（购买金额-原余额）
                 * 2、欠款=购买金额-原余额
                 */
                //(保证事物在sql中进行相减)
                currentCredit = reduceNum.subtract(model.getBalance());
                //(保证事物在sql中进行相加)
                noBackPayment = reduceNum.subtract(model.getBalance());
            }else {
                /**
                 * 1、授信余额=原授信余额-购买金额
                 * 2、欠款=原欠款+购买金额
                 */
                //(保证事物在sql中进行相减)
                currentCredit = reduceNum;
                //(保证事物在sql中进行相加)
                noBackPayment = reduceNum;
            }
        }
        model.setBalance(reduceNum);
        model.setCurrentCredit(currentCredit);
        model.setNoBackPayment(noBackPayment);

        //int update = this.agentAccountMapper.reduceBalance(agentId,reduceNum);
        int update = this.agentAccountMapper.updateAfterConsume(model);
        if (update != 1) {
            throw new JsmsAgentAccountException("扣减余额失败");
        }
        model = this.agentAccountMapper.getByAgentId(agentId);
		return ResultVO.successDefault(model);
    }

    @Override
    public List<JsmsAgentAccount> getByAgentIds(Set agentIds) {
        List<JsmsAgentAccount> models = this.agentAccountMapper.getByAgentIds(agentIds);
		return models;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentAccount> list = this.agentAccountMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List queryList(Map params) {
        List<JsmsAgentAccount> list = this.agentAccountMapper.queryListByMap(params);
        return list;
    }
    /**
     * 获取代理商的基本财务信息(可用余额)
     * @param agentId
     * @return
     */
    @Override
    public AgentFinance queryBaseFianceInfo(Integer agentId) {
        if(agentId == null){
            return null;
        }
        JsmsAgentAccount agentAccount = this.getByAgentId(agentId);
        AgentFinance agentFinance = this.new AgentFinance().build(agentAccount);
        return agentFinance;
    }


    public class AgentFinance extends JsmsAgentAccount{
        private BigDecimal availableBalance;
        private AgentFinance(){}
        public BigDecimal getAvailableBalance() {
            return availableBalance;
        }

        public void setAvailableBalance(BigDecimal availableBalance) {
            this.availableBalance = availableBalance;
        }

        public AgentFinance build(JsmsAgentAccount agentAccount){
            if (agentAccount == null){
                return null;
            }
            AgentFinance thiss = new AgentFinance();
            BeanUtil.copyProperties(agentAccount,thiss);
            if(BigDecimal.ZERO.compareTo(thiss.getBalance()) >= 0){ // 余额 <= 0, 可用额度 = 授信余额
                thiss.availableBalance = thiss.getCurrentCredit();
            }else { // 余额 > 0, 可用额度 = 余额+ 授信余额
                thiss.availableBalance = thiss.getBalance().add(thiss.getCurrentCredit());
            }

            return thiss;
        }
    }

    public static void main(String[] args) {
        JsmsAgentAccount jsmsAgentAccount = new JsmsAgentAccount();
        jsmsAgentAccount.setAgentId(123);
        jsmsAgentAccount.setBalance(BigDecimal.ONE);
        jsmsAgentAccount.setCurrentCredit(BigDecimal.TEN);
        JsmsAgentAccountServiceImpl thiss = new JsmsAgentAccountServiceImpl();
        AgentFinance agentFinance = thiss.new AgentFinance().build(jsmsAgentAccount);
        System.out.println(agentFinance.getAvailableBalance());

    }

    @Override
    public int count(Map<String,Object> params) {
		return this.agentAccountMapper.count(params);
    }
    @Override
    public Map<String, Object> querySumBlance(List agentIds) {
        return this.agentAccountMapper.querySumBlance(agentIds);
    }

    @Override
    public List<Map<String, Object>> querySumCreditBySale(Map params) {
        return this.agentAccountMapper.querySumCreditBySale(params);
    }

    /**
     * 考虑事务更新字段,字段正为加，负为减
     *
     * @param model
     * @return
     */
    @Override
    public int updateAccoutForRealTime(JsmsAgentAccount model) {
        return this.agentAccountMapper.updateAccoutForRealTime(model);
    }

    @Override
    public List<JsmsAgentAccount> findList(Map<String,Object> params){
        return this.agentAccountMapper.findList(params);
    }

    @Override
    public int reduceHasTakeInvoice(Integer agentId, BigDecimal hasTakeInvoice, boolean isReduceInit) {
        JsmsAgentAccount jsmsAgentAccount = this.agentAccountMapper.getByAgentId(agentId);
        logger.debug("更新客户+" + agentId + "+已开票金额has_take_invoice,更新前的已开票金额:" + jsmsAgentAccount.getHasTakeInvoice() + "减去:" + hasTakeInvoice);
        // 当代理商的已开票初始化金额大于0时才更新
        if (jsmsAgentAccount.getHasTakeInvoiceInit().compareTo(BigDecimal.ZERO) == 1 && isReduceInit) {
            logger.debug("更新客户+" + agentId + "+已开票初始化金额has_take_invoice_init,更新前的已开票初始化金额:" + jsmsAgentAccount.getHasTakeInvoice() + "减去:" + hasTakeInvoice);
            return this.agentAccountMapper.updateHasTakeInvoice(agentId, hasTakeInvoice, hasTakeInvoice);
        }

        return this.agentAccountMapper.updateHasTakeInvoice(agentId, hasTakeInvoice, null);
    }
}
