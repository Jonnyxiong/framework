package com.jsmsframework.sale.credit.service;

import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.*;
import com.jsmsframework.common.enums.invoice.InvoiceSourceEnum;
import com.jsmsframework.common.enums.invoice.InvoiceStatusEnum;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import com.jsmsframework.finance.entity.JsmsSaleCreditAccount;
import com.jsmsframework.finance.entity.JsmsSaleCreditBill;
import com.jsmsframework.finance.service.*;
import com.jsmsframework.sale.credit.enums.OauthStatusType;
import com.jsmsframework.sale.credit.exception.JsmsSaleCreditException;
import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import com.jsmsframework.user.entity.JsmsUser;
import com.jsmsframework.user.service.JsmsAccountService;
import com.jsmsframework.user.service.JsmsAgentInfoService;
import com.jsmsframework.user.service.JsmsUserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Don on 2017/11/15.
 */
@Service
public class JsmsSaleCreditServiceImpl implements JsmsSaleCreditService {
    private static final Logger logger = LoggerFactory.getLogger(JsmsSaleCreditService.class);
    @Autowired
    private JsmsAgentInfoService jsmsAgentInfoService;
    @Autowired
    private JsmsAgentAccountService jsmsAgentAccountService;
    @Autowired
    private JsmsAgentBalanceBillService jsmsAgentBalanceBillService;
    @Autowired
    private JsmsUserService jsmsUserService;
    @Autowired
    private JsmsAccountService jsmsAccountService;
    @Autowired
    private JsmsSaleCreditAccountService jsmsSaleCreditAccountService;
    @Autowired
    private JsmsSaleCreditBillService jsmsSaleCreditBillService;
    @Autowired
    private JsmsAgentInvoiceListService jsmsAgentInvoiceListService;

    /**
     * 销售授信代理商变化
     * @param opId
     * @param opType
     * @param agentId
     * @param money
     * @param remark
     * @return
     */
    @Transactional("message")
    @Override
    public R creditForAgent(Long opId, Integer opType,Integer agentId, BigDecimal money, String remark) {
        R r;
        Map<String,Object> results=new HashedMap();

        JsmsAgentInfo agent=jsmsAgentInfoService.getByAgentId(agentId);
        if(agent==null){
            logger.error("代理商ID{}不存在！",agentId);
            return	R.error("代理商不存在！");
        }else {
            JsmsAgentAccount agentAccount=jsmsAgentAccountService.getByAgentId(agent.getAgentId());
            logger.debug("操作之前代理商账号信息={}",JsonUtil.toJson(agentAccount));
            if(agentAccount==null){
                logger.error("代理商ID{}账户信息不存在！",agentId);
                return	R.error("代理商账户信息不存在！");
            }else {

                JsmsAgentBalanceBill bill=new JsmsAgentBalanceBill();

                bill.setAgentId(agentAccount.getAgentId());
                bill.setAmount(money);
                bill.setCreateTime(new Date());
                bill.setAdminId(opId);
                bill.setRemark(remark);

                JsmsAgentAccount agentAcc=new JsmsAgentAccount();
                agentAcc.setAgentId(agentAccount.getAgentId());
                if(Objects.equals(PaymentType.增加授信.getValue(),opType)){
                    //添加授信
                    bill.setPaymentType(PaymentType.增加授信.getValue());
                    bill.setFinancialType(FinancialType.入账.getValue());

                    agentAcc.setCurrentCredit(money);
                    agentAcc.setCreditBalance(money);
                    logger.debug("代理商添加授信开始");
                }else if(Objects.equals(PaymentType.降低授信.getValue(),opType)){
                    //降低授信
                    bill.setPaymentType(PaymentType.降低授信.getValue());
                    bill.setFinancialType(FinancialType.出账.getValue());
                    agentAcc.setCreditBalance(BigDecimal.ZERO.subtract(money));
                    agentAcc.setCurrentCredit(BigDecimal.ZERO.subtract(money));
                    logger.debug("代理商降低授信开始");
                }else  if(Objects.equals(PaymentType.充值.getValue(),opType)){
                    logger.error("代理商操作充值未集成");
                    throw  new JsmsSaleCreditException("代理商操作充值未集成");
                }else if(Objects.equals(PaymentType.回退条数.getValue(),opType)){
                    logger.error("代理商操作回退条数未集成");
                    throw  new JsmsSaleCreditException("代理商操作回退条数未集成");
                }else if(Objects.equals(PaymentType.后付费客户消耗.getValue(),opType)){
                    logger.error("代理商操作后付费客户消耗未集成");
                    throw  new JsmsSaleCreditException("代理商操作后付费客户消耗未集成");
                }else if(Objects.equals(PaymentType.后付费客户失败返还.getValue(),opType)){
                    logger.error("代理商操作后付费客户失败返还未集成");
                    throw  new JsmsSaleCreditException("代理商操作后付费客户失败返还未集成");
                } else {
                    logger.error("代理商账号操作非法,类型为{}",opType);
                    throw  new JsmsSaleCreditException("代理商账号操作非法,类型为"+opType);
                }

                bill.setBalance(agentAccount.getBalance());
                bill.setCreditBalance(agentAcc.getCreditBalance().add(agentAccount.getCreditBalance()));
                bill.setHistoryPayment(agentAccount.getHistoryPayment());
                bill.setCurrentCredit(agentAcc.getCurrentCredit().add(agentAccount.getCurrentCredit()));
                bill.setNoBackPayment(agentAccount.getNoBackPayment());
                //生成账单信息
                int abi=jsmsAgentBalanceBillService.insert(bill);
                if(abi>0){
                    logger.debug("生成代理商账单信息成功,账单bill={}", JsonUtil.toJson(bill));
                    //更新账户信息

                    int aup=jsmsAgentAccountService.updateAccoutForRealTime(agentAcc);
                    if(aup>0){
                        logger.debug("更新代理商账户信息成功,agentAccount={}", JsonUtil.toJson(agentAcc));
                        results.put("status", SysConstant.SUCCESS);
                        results.put("msg","更新代理商账户信息成功！");

                    }else {
                        results.put("status",SysConstant.FAIL);
                        results.put("msg","更新代理商账户信息失败!");
                        logger.error("生成代理商账单信息入库失败,agentAccount={}", JsonUtil.toJson(agentAcc));
                        R.error(results.get("msg").toString());
                        throw  new JsmsSaleCreditException("生成代理商账单信息入库失败,agentAccount="+JsonUtil.toJson(agentAcc));
                    }

                }else {
                    results.put("status",SysConstant.FAIL);
                    results.put("msg","生成代理商账单信息入库失败!");
                    logger.error("生成代理商账单信息入库失败,账单bill={}", JsonUtil.toJson(bill));
                    R.error(results.get("msg").toString());
                    throw  new JsmsSaleCreditException("生成代理商账单信息入库失败,账单bill="+JsonUtil.toJson(bill));
                }


            }
        }

        results.put("status",SysConstant.SUCCESS);
        results.put("msg","代理商账单信息更新入库成功!");

        return R.ok(results.get("msg").toString());
    }


    @Override
    @Transactional("message")
    public R creditForSale(Long opId, Integer opType,Integer agentId,Long saleId, BigDecimal money, String remark) {
        R r;
        Map<String,Object> results=new HashedMap();

        JsmsUser saleUser=jsmsUserService.getById2(saleId);
        if(saleUser==null){
            results.put("status",SysConstant.FAIL);
            results.put("msg","销售ID账户="+saleId+"不存在！");
            logger.error("销售ID{}账户不存在！",saleId);
            return	R.error(results.get("msg").toString());
        }else {
            JsmsSaleCreditAccount saleAccount=jsmsSaleCreditAccountService.getBySaleId(saleUser.getId());
            logger.debug("销售授信更新账户之前，账户信息为={}",JsonUtil.toJson(saleAccount));
            if(saleAccount==null){
                results.put("status",SysConstant.FAIL);
                results.put("msg","销售ID账户="+saleId+"信息不存在！");
                logger.error("销售ID{}账户信息不存在！",saleId);
                return	R.error(results.get("msg").toString());
            }else {

                JsmsSaleCreditBill bill=new JsmsSaleCreditBill();


                JsmsSaleCreditAccount saleAcc=new JsmsSaleCreditAccount();
                saleAcc.setSaleId(saleAccount.getSaleId());
                if(Objects.equals(BusinessType.销售给客户授信.getValue(),opType)){
                    //客户添加授信
                    bill.setBusinessType(BusinessType.销售给客户授信.getValue());
                    bill.setFinancialType(FinancialType.出账.getValue());
                    saleAcc.setSaleHistoryCredit(money);
                    saleAcc.setCurrentCredit(BigDecimal.ZERO.subtract(money));
                    saleAcc.setNoBackPayment(money);
                    bill.setObjectId(Long.valueOf(agentId));
                    bill.setObjectType(ObjectType.代理商.getValue());
                    bill.setFinancialHistoryCredit(saleAccount.getFinancialHistoryCredit());
                    bill.setCurrentCredit(saleAcc.getCurrentCredit().add(saleAccount.getCurrentCredit()));
                    bill.setSaleHistoryCredit(saleAcc.getSaleHistoryCredit().add(saleAccount.getSaleHistoryCredit()));
                    bill.setAgentHistoryPayment(saleAccount.getAgentHistoryPayment());
                    bill.setNoBackPayment(saleAcc.getNoBackPayment().add(saleAccount.getNoBackPayment()));
                    logger.debug("销售给客户授信开始");
                }else if(Objects.equals(BusinessType.销售降低客户授信.getValue(),opType)){
                    //客户降低授信
                    bill.setBusinessType(BusinessType.销售降低客户授信.getValue());
                    bill.setFinancialType(FinancialType.入账.getValue());
                    saleAcc.setSaleHistoryCredit(BigDecimal.ZERO.subtract(money));
                    saleAcc.setCurrentCredit(money);
                    saleAcc.setNoBackPayment(BigDecimal.ZERO.subtract(money));
                    bill.setObjectId(Long.valueOf(agentId));
                    bill.setObjectType(ObjectType.代理商.getValue());

                    bill.setFinancialHistoryCredit(saleAccount.getFinancialHistoryCredit());
                    bill.setCurrentCredit(saleAcc.getCurrentCredit().add(saleAccount.getCurrentCredit()));
                    bill.setSaleHistoryCredit(saleAcc.getSaleHistoryCredit().add(saleAccount.getSaleHistoryCredit()));
                    bill.setAgentHistoryPayment(saleAccount.getAgentHistoryPayment());
                    bill.setNoBackPayment(saleAcc.getNoBackPayment().add(saleAccount.getNoBackPayment()));
                    logger.debug("销售降低客户授信开始");
                }else if(Objects.equals(BusinessType.财务给销售授信.getValue(),opType)){
                    //销售添加授信
                    bill.setBusinessType(BusinessType.财务给销售授信.getValue());
                    bill.setFinancialType(FinancialType.入账.getValue());

                    saleAcc.setCurrentCredit(money);
                    saleAcc.setFinancialHistoryCredit(money);

                    bill.setObjectId(saleId);
                    bill.setObjectType(ObjectType.销售.getValue());
                    bill.setFinancialHistoryCredit(saleAcc.getFinancialHistoryCredit().add(saleAccount.getFinancialHistoryCredit()));
                    bill.setCurrentCredit(saleAcc.getCurrentCredit().add(saleAccount.getCurrentCredit()));
                    bill.setSaleHistoryCredit(saleAccount.getSaleHistoryCredit());
                    bill.setAgentHistoryPayment(saleAccount.getAgentHistoryPayment());
                    bill.setNoBackPayment(saleAccount.getNoBackPayment());
                    logger.debug("财务给销售授信开始");
                }else if(Objects.equals(BusinessType.财务降低销售授信.getValue(),opType)){
                    //销售降低授信
                    bill.setBusinessType(BusinessType.财务降低销售授信.getValue());
                    bill.setFinancialType(FinancialType.出账.getValue());
                    saleAcc.setCurrentCredit(BigDecimal.ZERO.subtract(money));
                    saleAcc.setFinancialHistoryCredit(BigDecimal.ZERO.subtract(money));

                    bill.setObjectId(saleId);
                    bill.setObjectType(ObjectType.销售.getValue());
                    bill.setFinancialHistoryCredit(saleAcc.getFinancialHistoryCredit().add(saleAccount.getFinancialHistoryCredit()));
                    bill.setCurrentCredit(saleAcc.getCurrentCredit().add(saleAccount.getCurrentCredit()));
                    bill.setSaleHistoryCredit(saleAccount.getSaleHistoryCredit());
                    bill.setAgentHistoryPayment(saleAccount.getAgentHistoryPayment());
                    bill.setNoBackPayment(saleAccount.getNoBackPayment());
                    logger.debug("财务降低销售授信开始");
                }else  if(Objects.equals(BusinessType.客户回款.getValue(),opType)){

                    saleAcc.setAgentHistoryPayment(money);
                    saleAcc.setNoBackPayment(BigDecimal.ZERO.subtract(money));

                    bill.setBusinessType(BusinessType.客户回款.getValue());
                    bill.setFinancialType(FinancialType.入账.getValue());
                    bill.setObjectId(Long.valueOf(agentId));
                    bill.setObjectType(ObjectType.代理商.getValue());
                    bill.setFinancialHistoryCredit(saleAccount.getFinancialHistoryCredit());
                    bill.setCurrentCredit(saleAccount.getCurrentCredit());
                    bill.setSaleHistoryCredit(saleAccount.getSaleHistoryCredit());
                    bill.setAgentHistoryPayment(saleAcc.getAgentHistoryPayment().add(saleAccount.getAgentHistoryPayment()));
                    bill.setNoBackPayment(saleAcc.getNoBackPayment().add(saleAccount.getNoBackPayment()));
                    logger.debug("客户回款开始");
                }

                bill.setSaleId(saleUser.getId());
                bill.setAmount(money);

                bill.setAdminId(opId);
                bill.setCreateTime(new Date());
                bill.setRemark(remark);

                //生成账单信息
                int abi=jsmsSaleCreditBillService.insert(bill);
                if(abi>0){
                    logger.debug("生成销售账单信息成功,账单bill={}", JsonUtil.toJson(bill));
                    //更新账户信息
                    int aup=jsmsSaleCreditAccountService.updateAccountForRealTime(saleAcc);
                    if(aup>0){
                        logger.debug("更新销售账户信息成功,agentAccount={}", JsonUtil.toJson(saleAcc));
                        results.put("status",SysConstant.FAIL);
                        results.put("msg","更新销售ID="+saleId+"账户信息成功！");

                    }else {
                        results.put("status",SysConstant.FAIL);
                        results.put("msg","更新销售账户信息失败!");
                        logger.error("更新销售ID={}账户信息失败,agentAccount={}",saleId,JsonUtil.toJson(saleAcc));
                        R.error(results.get("msg").toString());
                        throw new JsmsSaleCreditException("更新销售账户信息失败,更新信息agentAccount="+JsonUtil.toJson(saleAcc));
                    }


                }else {
                    results.put("status",SysConstant.FAIL);
                    results.put("msg","生成销售ID="+saleId+"账单信息入库失败!");
                    logger.error("生成销售账单信息入库失败,账单bill={}", JsonUtil.toJson(bill));
                    R.error(results.get("msg").toString());
                    throw new JsmsSaleCreditException("生成销售账单信息入库失败,账单bill="+JsonUtil.toJson(saleAcc));
                }


            }
        }

        results.put("status",SysConstant.SUCCESS);
        results.put("msg","销售账单信息更新入库成功!");

        return R.ok(results.get("msg").toString());
    }

    @Override
    @Transactional("message")
    public Map<String, Object> agentUpdateByBalanceOP(String bType,Long opId,Integer agentId, String clientId, BigDecimal money, String remark) {
        Map<String,Object> data=new HashedMap();

        JsmsAgentInfo agent=jsmsAgentInfoService.getByAgentId(agentId);
        if(agent==null){
            data.put("status",SysConstant.FAIL);
            data.put("msg","代理商不存在！");
            logger.error("操作金额类型{},代理商ID{}不存在",bType, agentId);
            return	data;
        }
        JsmsAgentAccount agentAcc=jsmsAgentAccountService.getByAgentId(agentId);
        if(agentAcc==null){
            data.put("status",SysConstant.FAIL);
            data.put("msg","代理商不存在！");
            logger.error("操作金额类型{},代理商ID{}账户信息不存在",bType, agentId);
            return	data;
        }
        logger.debug("操作之前代理商账号信息={}",JsonUtil.toJson(agentAcc));
        JsmsAgentAccount agentAccount=new JsmsAgentAccount();
        agentAccount.setAgentId(agentAcc.getAgentId());
        JsmsAgentBalanceBill bill=new JsmsAgentBalanceBill();
        bill.setAgentId(agentAcc.getAgentId());
        bill.setAdminId(opId);

        if(Objects.equals(CreditRemarkType.返还.getValue(),bType)){
            BigDecimal backPay=BigDecimal.ZERO;//回款金额

            if(agentAcc.getBalance().compareTo(BigDecimal.ZERO)==-1){

                if(agentAcc.getNoBackPayment().compareTo(money)==-1){
                    //充值金额大于欠款
                    agentAccount.setNoBackPayment(BigDecimal.ZERO.subtract(agentAcc.getNoBackPayment()));
                    backPay=agentAcc.getNoBackPayment();
                }else {

                    agentAccount.setNoBackPayment(BigDecimal.ZERO.subtract(money));
                    backPay=money;
                }
                agentAccount.setBalance(money);
                agentAccount.setHistoryPayment(backPay);
                bill.setNoBackPayment(agentAccount.getNoBackPayment().add(agentAcc.getNoBackPayment()));
                bill.setHistoryPayment(agentAccount.getHistoryPayment().add(agentAcc.getHistoryPayment()));
            }else {
                agentAccount.setBalance(money);

                //	bill.setNoBackPayment(agentAcc.getNoBackPayment());
                //	bill.setCurrentCredit(agentAcc.getCurrentCredit());
                bill.setNoBackPayment(agentAcc.getNoBackPayment());
                bill.setHistoryPayment(agentAcc.getHistoryPayment());
            }

            bill.setCurrentCredit(agentAcc.getCurrentCredit());
            bill.setCreditBalance(agentAcc.getCreditBalance());
            bill.setPaymentType(PaymentType.后付费客户失败返还.getValue());
            bill.setFinancialType(FinancialType.入账.getValue());
            agentAccount.setAccumulatedConsume(BigDecimal.ZERO.subtract(money));
            bill.setAmount(money);
            bill.setBalance(agentAcc.getBalance().add(agentAccount.getBalance()));
            bill.setRemark(remark);
            bill.setOrderId(null);
            bill.setClientId(clientId);
            bill.setCreateTime(new Date());

            logger.debug("【后付费客户失败返还】返还账单bill{},更新账户account{}",JsonUtil.toJson(bill),JsonUtil.toJson(agentAccount));
            int binsert=jsmsAgentBalanceBillService.insert(bill);

            if(binsert>0){
                int accUpdate=jsmsAgentAccountService.updateAccoutForRealTime(agentAccount);
                logger.debug("【后付费客户失败返还】生成代理商账单信息成功,账单bill={}", JsonUtil.toJson(bill));
                if(accUpdate<=0){
                    data.put("status", SysConstant.FAIL);
                    data.put("msg", "更新代理商账号金额失败！");
                    logger.error("【后付费客户失败返还】更新代理商账号金额失败,账户account={}", JsonUtil.toJson(agentAccount));
                    throw new JsmsSaleCreditException("【后付费客户失败返还】更新代理商账号金额失败,账户account="+JsonUtil.toJson(agentAccount));
                }
                logger.debug("【后付费客户失败返还】更新代理商账号金额成功,账户account={}", JsonUtil.toJson(agentAccount));
            }else {
                data.put("status", SysConstant.FAIL);
                data.put("msg", "生成代理商账单失败！");
                logger.error("【后付费客户失败返还】生成代理商账单信息失败,账单bill={}", JsonUtil.toJson(bill));
                throw new JsmsSaleCreditException("【后付费客户失败返还】生成代理商账单信息失败,账单bill="+JsonUtil.toJson(bill));
            }
            if(backPay.compareTo(BigDecimal.ZERO)!=0){
                logger.debug("开始【后付费客户失败返还】回款代理商ID={},回款金额={}",agentId,backPay);
                remark= CreditRemarkType.返还.getDesc();
                R r=this.creditForSale(SysConstant.SYS_ID,BusinessType.客户回款.getValue(),agentId,agent.getBelongSale(),backPay,remark);
                if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                    logger.error("【后付费客户失败返还】回款代理商ID={},回款金额={},失败原因={}",agentId,backPay,JsonUtil.toJson(r));
                    data.put("status",SysConstant.FAIL);
                    data.put("msg",r.getMsg());
                    throw new JsmsSaleCreditException("【后付费客户失败返还】生成代理商回款信息失败,原因:"+JsonUtil.toJson(r));
                }
            }

        }else if(Objects.equals(CreditRemarkType.超频.getValue(),bType)){
            BigDecimal needCreditMoney=BigDecimal.ZERO;	//需要额外授信金额
            BigDecimal vailBalance=BigDecimal.ZERO;//可用金额
            BigDecimal needPayCredit=BigDecimal.ZERO;//需要消耗授信额度
            if(agentAcc.getBalance().compareTo(BigDecimal.ZERO)==-1){
                vailBalance=agentAcc.getCurrentCredit();
            }else {
                vailBalance=agentAcc.getBalance().add(agentAcc.getCurrentCredit());
            }
            if(agentAcc.getBalance().compareTo(money)==-1){

                if(vailBalance.compareTo(money)==-1){
                    //重新授信补全消费金额
                    needCreditMoney=money.subtract(vailBalance);
                    data=this.replenish(needCreditMoney,agentAcc.getAgentId(),agent.getBelongSale());
                    if(Objects.equals(SysConstant.FAIL,data.get("status"))){
                        logger.error("【后付费消耗超频】重新授信补全消费金额失败,原因data={}",JsonUtil.toJson(data));
                        throw new JsmsSaleCreditException("【后付费客户消耗】重新授信补全消费金额失败,原因:"+JsonUtil.toJson(data));
                    }
                    //更新后,重新取代理商账号信息
                    agentAcc=jsmsAgentAccountService.getByAgentId(agentAcc.getAgentId());

                }
                if(agentAcc.getBalance().compareTo(BigDecimal.ZERO)==-1){
                    agentAccount.setCurrentCredit(BigDecimal.ZERO.subtract(money));
                    needPayCredit=money;
                }else {
                    needPayCredit=money.subtract(agentAcc.getBalance());
                    agentAccount.setCurrentCredit((BigDecimal.ZERO.subtract(needPayCredit)));
                }

                agentAccount.setNoBackPayment(needPayCredit);


                agentAccount.setBalance(BigDecimal.ZERO.subtract(money));


                bill.setCurrentCredit(agentAccount.getCurrentCredit().add(agentAcc.getCurrentCredit()));
                bill.setNoBackPayment(agentAccount.getNoBackPayment().add(agentAcc.getNoBackPayment()));
            }else {
                agentAccount.setBalance(BigDecimal.ZERO.subtract(money));
                bill.setNoBackPayment(agentAcc.getNoBackPayment());
                bill.setCurrentCredit(agentAcc.getCurrentCredit());

            }
            bill.setCreditBalance(agentAcc.getCreditBalance());
            bill.setHistoryPayment(agentAcc.getHistoryPayment());
            bill.setPaymentType(PaymentType.后付费客户消耗.getValue());
            bill.setFinancialType(FinancialType.出账.getValue());
            agentAccount.setAccumulatedConsume(money);
            bill.setAmount(money);
            bill.setBalance(agentAccount.getBalance().add(agentAcc.getBalance()));
            bill.setRemark(remark);
            bill.setOrderId(null);
            bill.setClientId(clientId);
            bill.setCreateTime(new Date());
            int binsert=jsmsAgentBalanceBillService.insert(bill);
            if(binsert>0){
                logger.debug("【后付费客户消耗】生成代理商账单成功！,bill={}",JsonUtil.toJson(bill));
                int accUpdate=jsmsAgentAccountService.updateAccoutForRealTime(agentAccount);
                if(accUpdate<=0){
                    data.put("status", SysConstant.FAIL);
                    data.put("msg", "更新代理商账号金额失败！");
                    logger.debug("【后付费客户消耗】更新代理商账号金额失败！,account={}",JsonUtil.toJson(agentAccount));
                    throw new JsmsSaleCreditException("【后付费客户消耗】更新代理商账号金额失败,account:"+JsonUtil.toJson(agentAccount));
                }
                logger.debug("【后付费客户消耗】更新代理商账号金额成功！,account={}",JsonUtil.toJson(agentAccount));
            }else {
                data.put("status",SysConstant.FAIL);
                data.put("msg", "生成代理商账单失败！");
                logger.error("【后付费客户消耗】生成代理商账单失败！,bill={}",JsonUtil.toJson(bill));
                throw new JsmsSaleCreditException("【后付费客户消耗】生成代理商账单失败,账单:"+JsonUtil.toJson(bill));
            }

        }




        data.put("status", SysConstant.SUCCESS);
        data.put("msg", "更新代理商余额成功！");
        return data;
    }

    /**
     * 归属销售转变授信变化
     *
     * @param oldSaleId
     * @param newSaleId
     * @return
     */
    @Transactional("message")
    @Override
    public R belongSaleChaned(Long oldSaleId, Long newSaleId) {
        R r;
        //1.查所有归属销售为旧销售名下的代理商
        Map<String,Object> params=new HashedMap();
        params.put("belongSale",oldSaleId);
        List<JsmsAgentInfo> agents=jsmsAgentInfoService.queryAll(params);
        if(agents==null ||agents.size()==0){
            return R.error("该销售名下无代理商客户");
        }
        Set<Integer> agentIds=new HashSet<>();
        for (JsmsAgentInfo agent : agents) {
            agentIds.add(agent.getAgentId());
        }
        List<JsmsAgentAccount> agentAcc=jsmsAgentAccountService.getByAgentIds(agentIds);
        logger.debug("操作之前代理商账号信息={}",JsonUtil.toJson(agentAcc));
        for (JsmsAgentAccount agentAccount : agentAcc) {
            if(agentAccount.getBalance().compareTo(BigDecimal.ZERO)!=-1 && agentAccount.getCurrentCredit().compareTo(BigDecimal.ZERO)==0){
                logger.debug("代理商ID={}未欠款且授信余额为0",agentAccount.getAgentId());
            }else {
                BigDecimal backMoney=agentAccount.getCurrentCredit().add(agentAccount.getNoBackPayment());
                String remark=CreditRemarkType.变更.getDesc();
                //执行原销售客户回款流程
                r=this.creditForSale(SysConstant.SYS_ID,BusinessType.客户回款.getValue(),agentAccount.getAgentId(),oldSaleId,backMoney,remark);
                if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                    throw new JsmsSaleCreditException("【归属销售转变】原销售客户回款失败,原因:"+JsonUtil.toJson(r));
                }
                //执行财务给新归属销售授信流程
                r=this.creditForSale(SysConstant.SYS_ID,BusinessType.财务给销售授信.getValue(),null,newSaleId,backMoney,remark);
                if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                    throw new JsmsSaleCreditException("【归属销售转变】财务给新归属销售授信失败,原因:"+JsonUtil.toJson(r));
                }
                //执行新归属销售给用户授信流程
                r=this.creditForSale(SysConstant.SYS_ID,BusinessType.销售给客户授信.getValue(),agentAccount.getAgentId(),newSaleId,backMoney,remark);
                if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                    throw new JsmsSaleCreditException("【归属销售转变】新归属销售给用户授信失败,原因:"+JsonUtil.toJson(r));
                }

            }
        }
        logger.info("-------------------------------------客户转交债务移交成功------------------------------------------------");

        return R.ok("客户转交债务成功！");
    }

    /**
     * 单个代理商 归属销售转变授信变化
     *
     * @param oldSaleId
     * @param newSaleId
     * @param agentId
     * @return
     */
    @Transactional("message")
    @Override
    public R singleBelongSaleChaned(Long oldSaleId, Long newSaleId, Integer agentId) {
        R r;
        JsmsAgentAccount agentAcc=jsmsAgentAccountService.getByAgentId(agentId);

        logger.debug("操作之前代理商账号信息={}",JsonUtil.toJson(agentAcc));
            if(agentAcc.getBalance().compareTo(BigDecimal.ZERO)!=-1 && agentAcc.getCurrentCredit().compareTo(BigDecimal.ZERO)==0){
                logger.debug("代理商ID={}未欠款且授信余额为0",agentAcc.getAgentId());
            }else {
                BigDecimal backMoney=agentAcc.getCurrentCredit().add(agentAcc.getNoBackPayment());
                String remark=CreditRemarkType.变更.getDesc();
                //执行原销售客户回款流程
                r=this.creditForSale(SysConstant.SYS_ID,BusinessType.客户回款.getValue(),agentAcc.getAgentId(),oldSaleId,backMoney,remark);
                if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                    throw new JsmsSaleCreditException("【归属销售转变】原销售客户回款失败,原因:"+JsonUtil.toJson(r));
                }
                //执行财务给新归属销售授信流程
                r=this.creditForSale(SysConstant.SYS_ID,BusinessType.财务给销售授信.getValue(),null,newSaleId,backMoney,remark);
                if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                    throw new JsmsSaleCreditException("【归属销售转变】财务给新归属销售授信失败,原因:"+JsonUtil.toJson(r));
                }
                //执行新归属销售给用户授信流程
                r=this.creditForSale(SysConstant.SYS_ID,BusinessType.销售给客户授信.getValue(),agentAcc.getAgentId(),newSaleId,backMoney,remark);
                if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                    throw new JsmsSaleCreditException("【归属销售转变】新归属销售给用户授信失败,原因:"+JsonUtil.toJson(r));
                }

            }

        logger.info("-------------------------------------客户转交债务移交成功------------------------------------------------");

        return R.ok("客户转交债务成功！");
    }

    /**
     * 对于代理商余额操作变化
     * 2017年11月30日
     * create by Don
     * 重构方法
     *
     * @param params
     * @return
     */
    @Transactional("message")
    @Override
    public Map<String, Object> agentBalanceForOpreation(Map<String, String> params) {
        Map<String,Object> data=new HashedMap();

        //校验
        data=this.checkBalanceOpreation(params);
        if(Objects.equals(SysConstant.FAIL,data.get("result"))){
            return data;
        }else {
            data=this.BalanceOpreation(params);
        }

        return data;
    }


    /**
     *
     * @param params
     * @return
     */
    public  Map<String,Object> BalanceOpreation(Map<String, String> params){
        Map<String, Object> data = new HashedMap();

        JsmsAgentInfo agentInfo=jsmsAgentInfoService.getByAgentId(Integer.valueOf(params.get("agent_id")));
        if(agentInfo==null){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "代理商信息不存在！");
            return data;
        }

        JsmsAgentAccount agentAcc = jsmsAgentAccountService.getByAgentId(Integer.valueOf(params.get("agent_id")));
        logger.debug("操作之前代理商账号信息={}",JsonUtil.toJson(agentAcc));
        if (agentAcc == null) {
            data.put("result", SysConstant.FAIL);
            data.put("msg", "代理商账号信息不存在！");
            return data;
        } else {
            if (Objects.equals(PaymentType.充值.getDesc(), params.get("operateType"))) {


                if (agentAcc.getBalance().compareTo(BigDecimal.ZERO) != -1) {
                    //未欠款 代理商
                    data = this.agentUpdateByBalanceOP(params, agentAcc, PaymentType.充值.getValue(), false);
                    if (Objects.equals(data.get("result"), SysConstant.FAIL)) {
                        return data;
                    }

                } else {
                    //欠款 回款
                    //代理商
                    data = this.agentUpdateByBalanceOP(params, agentAcc, PaymentType.充值.getValue(), true);
                    params.put("backPay", data.get("backPay").toString());
                    if (Objects.equals(data.get("result"),SysConstant.FAIL)) {
                        return data;
                    } else {
                        //销售 回款
                        data = this.saleUpdateByBalanceOP(params, agentAcc);
                        if (Objects.equals(data.get("result"), SysConstant.FAIL)) {
                            throw new JsmsSaleCreditException("【代理商财务】代理商充值回款失败,原因:"+JsonUtil.toJson(data));
                        }

                    }

                }


                data.put("msg", "充值成功！");
            }else if (Objects.equals(PaymentType.在线充值.getDesc(), params.get("operateType"))) {


                if (agentAcc.getBalance().compareTo(BigDecimal.ZERO) != -1) {
                    //未欠款 代理商
                    data = this.agentUpdateByBalanceOP(params, agentAcc, PaymentType.在线充值.getValue(), false);
                    if (Objects.equals(data.get("result"), SysConstant.FAIL)) {
                        return data;
                    }

                } else {
                    //欠款 回款
                    //代理商
                    data = this.agentUpdateByBalanceOP(params, agentAcc, PaymentType.在线充值.getValue(), true);
                    params.put("backPay", data.get("backPay").toString());
                    if (Objects.equals(data.get("result"),SysConstant.FAIL)) {
                        return data;
                    } else {
                        //销售 回款
                        data = this.saleUpdateByBalanceOP(params, agentAcc);
                        if (Objects.equals(data.get("result"), SysConstant.FAIL)) {
                            throw new JsmsSaleCreditException("【代理商财务】代理商充值回款失败,原因:"+JsonUtil.toJson(data));
                        }

                    }

                }


                data.put("msg", "充值成功！");
            }
            else if (Objects.equals(PaymentType.退款.getDesc(), params.get("operateType"))) {
                // add by lpjLiu 2018-01-26 v3.5  smso-5.19.0.0 发票需求
                if (agentInfo.getAgentType().equals(AgentType.OEM代理商.getValue()))
                {
                    BigDecimal amount = new BigDecimal(params.get("operateAmount"));
                    BigDecimal canReturn = getCanReturnAmount(agentAcc, agentInfo);
                    if (canReturn.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(canReturn) > 0)
                    {
                        data.put("result", SysConstant.FAIL);
                        data.put("msg", "退款失败，可退金额不足！");
                        return data;
                    }
                }

                data = this.agentUpdateByBalanceOP(params, agentAcc, PaymentType.退款.getValue(), false);
                if (Objects.equals(data.get("result"), SysConstant.FAIL)) {
                    throw new JsmsSaleCreditException("【代理商财务】代理商退款失败,原因:"+JsonUtil.toJson(data));
                }
                data.put("msg", "退款成功！");
            } else if (Objects.equals(PaymentType.扣减.getDesc(), params.get("operateType"))) {
                data = this.agentUpdateByBalanceOP(params, agentAcc, PaymentType.扣减.getValue(), false);
                if (Objects.equals(data.get("result"), SysConstant.FAIL)) {
                    throw new JsmsSaleCreditException("【代理商财务】代理商扣减失败,原因:"+JsonUtil.toJson(data));
                }
                data.put("msg", "扣减成功！");
            }

            data.put("result", SysConstant.SUCCESS);
        }
        return data;
    }



    /**
     * 补全授信额度
     * @param needCreditMoney
     * @param agentId
     * @return
     */
    @Transactional("message")
    public Map<String,Object> replenish(BigDecimal needCreditMoney,Integer agentId,Long saleId){

        Map<String,Object> results=new HashedMap();
        //1.财务授信销售
        R r=this.creditForSale(SysConstant.SYS_ID,BusinessType.财务给销售授信.getValue(),null,saleId,needCreditMoney,CreditRemarkType.超频.getDesc());
        if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
            results.put("status",SysConstant.FAIL);
            results.put("msg",r.getMsg());
            logger.error("【后付费消耗超频】财务授信销售失败,原因={}",JsonUtil.toJson(results));
            throw new JsmsSaleCreditException("【后付费消耗超频】财务授信销售失败,原因:"+JsonUtil.toJson(r));
        }else {
            logger.debug("【后付费消耗超频】财务授信销售成功！");
            //2.销售授信代理商相关changed
            r=this.creditForAgent(SysConstant.SYS_ID,PaymentType.增加授信.getValue(),agentId,needCreditMoney,CreditRemarkType.超频.getDesc());
            if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                results.put("status",SysConstant.FAIL);
                results.put("msg",r.getMsg());
                logger.error("【后付费消耗超频】销售授信代理商失败,原因={}",JsonUtil.toJson(results));
                throw new JsmsSaleCreditException("【后付费消耗超频】销售授信代理商失败,原因:"+JsonUtil.toJson(r));
            }else{
                logger.debug("【后付费消耗超频】销售授信代理商成功！");
                //3.销售授信代理商,销售相关changed
                 r=this.creditForSale(SysConstant.SYS_ID,BusinessType.销售给客户授信.getValue(),agentId,saleId,needCreditMoney,CreditRemarkType.超频.getDesc());
                if(Objects.equals(SysConstant.FAIL_CODE,r.getCode())){
                    results.put("status",SysConstant.FAIL);
                    results.put("msg",r.getMsg());
                    logger.error("【后付费消耗超频】销售授信代理商,销售相关信息生成失败,原因={}",JsonUtil.toJson(results));
                    throw new JsmsSaleCreditException("【后付费消耗超频】销售授信代理商,销售相关信息生成失败,原因:"+JsonUtil.toJson(r));
                }
                logger.debug("【后付费消耗超频】销售授信代理商,销售相关信息生成成功！");
            }

        }
        results.put("status",SysConstant.SUCCESS);
        logger.debug("金额补充授信成功，代理商ID={}",agentId);
        results.put("msg","金额补充授信成功！");
        return  results;
    }


    /**
     * 校验
     * @param params
     * @return
     */
    private  Map<String,Object> checkBalanceOpreation(Map<String, String> params){
        Map<String,Object> data=new HashedMap();
        String operateType = params.get("operateType");// 充值 退款 赠送 扣减
        String paymentId = params.get("paymentId");// 在线充值单号
        if(StringUtils.isBlank(operateType)){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "非法操作！");
            return data;
        }
        if(operateType.toString().equals(PaymentType.在线充值.getDesc())){
            if(StringUtils.isBlank(paymentId)){
                data.put("result", SysConstant.FAIL);
                data.put("msg", "非法操作！");
                return data;
            }
        }
        if(StringUtils.isBlank(params.get("agent_id"))){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "代理商ID不能为空！");
            return data;
        }
        if(params.get("operateAmount") == null || params.get("operateAmount") == ""){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "请输入金额");
            return data;
        }
        if(BigDecimal.ZERO.compareTo(new BigDecimal(params.get("operateAmount")))==1){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "请输入正确的金额");
            return data;
        }
        if(BigDecimal.ZERO.compareTo(new BigDecimal(params.get("operateAmount")))==0){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "操作金额不能为0");
            return data;
        }


        if(params.get("agentType") != null && "1".equals(params.get("agentType")) && params.get("clientId") == null){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "<b>请选择待"+operateType+ "客户</b>");
            return data;
        }
        // 判断客户状态
        if(params.get("agentType") != null && "1".equals(params.get("agentType")) ){
            JsmsAccount clientInfo = jsmsAccountService.getByClientId(params.get("clientId"));
            if(clientInfo != null && !Objects.equals(clientInfo.getStatus().toString(),AgentStatus.注册完成.getValue())){
                data.put("result", SysConstant.FAIL);
                data.put("msg", "<b>待"+operateType+ "客户状态："+AgentStatus.getDescByValue(clientInfo.getStatus().toString())+"</b>");
                return data;
            }
            if(clientInfo != null &&  !clientInfo.getOauthStatus().equals(3)){
                data.put("result", SysConstant.FAIL);
                data.put("msg", "<b>待"+operateType+ "客户认证状态："+ OauthStatusType.getDescByValue(clientInfo.getOauthStatus())+"</b>");
                return data;
            }


        }


        data.put("result", SysConstant.SUCCESS);
        data.put("msg", "校验成功！");
        return data;
    }


    public BigDecimal getCanReturnAmount(JsmsAgentAccount agentAccount, JsmsAgentInfo agentInfo) {
        // 可开票金额=累计充值金额-已开票金额-待开票金额-历史退款金额
        BigDecimal recharge = agentAccount.getAccumulatedRecharge();
        Assert.notNull(recharge, "客户累计充值金额不能为空");

        BigDecimal hasTake = agentAccount.getHasTakeInvoice();
        Assert.notNull(hasTake, "客户已开票金额不能为空");

        BigDecimal hisBack = agentAccount.getAccumulatedRefund();
        Assert.notNull(hisBack, "客户历史退款金额不能为空");

        // 查询待开票金额
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentAccount.getAgentId());
        params.put("source", InvoiceSourceEnum.发票申请.getValue());
        params.put("statusList", Arrays.asList(InvoiceStatusEnum.待审核.getValue(), InvoiceStatusEnum.开票中.getValue()));
        BigDecimal waitAnvoice = jsmsAgentInvoiceListService.getWaitInvoiceListAmount(params);
        Assert.notNull(waitAnvoice, "客户待开票金额不能为空");

        // 可开票金额
        BigDecimal canOpen = recharge.subtract(hasTake).subtract(waitAnvoice).subtract(hisBack);
        if (BigDecimal.ZERO.compareTo(canOpen) >= 0) {
            canOpen = BigDecimal.ZERO;
        }

        // 客户余额  >= 可开票金额，   可退金额 = 可开票金额。 >0 可退，否则不能退
        // 客户余额  <  可开票金额，   可退金额 = 余额。      >0 可退，否则不能退
        BigDecimal canReturn = agentAccount.getBalance().compareTo(canOpen) >= 0 ? canOpen : agentAccount.getBalance();

        return canReturn;
    }

    /**
     *代理商变化
     * @param params
     * @param type
     * @return
     */
    @Transactional("message")
    public Map<String,Object> agentUpdateByBalanceOP(Map<String, String> params,JsmsAgentAccount agentAcc,Integer type,Boolean isDebt){
        Map<String,Object> data=new HashedMap();
        JsmsAgentAccount agentAccount=new JsmsAgentAccount();
        agentAccount.setAgentId(agentAcc.getAgentId());
        BigDecimal amount=new BigDecimal(params.get("operateAmount"));
        JsmsAgentBalanceBill bill=new JsmsAgentBalanceBill();
        bill.setAgentId(agentAcc.getAgentId());
        bill.setAdminId(Long.valueOf(params.get("userId")));

        if(Objects.equals(PaymentType.充值.getValue(),type)){
            if(isDebt){
                if(agentAcc.getNoBackPayment().compareTo(amount)==1){
                    agentAccount.setNoBackPayment(BigDecimal.ZERO.subtract(amount));
                    agentAccount.setHistoryPayment(amount);
                    data.put("backPay",amount);
                }else {
                    agentAccount.setNoBackPayment(BigDecimal.ZERO.subtract(agentAcc.getNoBackPayment()));
                    agentAccount.setHistoryPayment(agentAcc.getNoBackPayment());
                    data.put("backPay",agentAcc.getNoBackPayment());
                }

                agentAccount.setAccumulatedRecharge(amount);
                bill.setNoBackPayment(agentAccount.getNoBackPayment().add(agentAcc.getNoBackPayment()));
                bill.setHistoryPayment(agentAccount.getHistoryPayment().add(agentAcc.getHistoryPayment()));
            }else {
                agentAccount.setAccumulatedRecharge(amount);
                bill.setHistoryPayment(agentAcc.getHistoryPayment());
                bill.setNoBackPayment(agentAcc.getNoBackPayment());
            }
            agentAccount.setBalance(amount);

            bill.setPaymentType(PaymentType.充值.getValue());
            bill.setFinancialType(FinancialType.入账.getValue());

        }else  if(Objects.equals(PaymentType.在线充值.getValue(),type)){
            if(isDebt){
                if(agentAcc.getNoBackPayment().compareTo(amount)==1){
                    agentAccount.setNoBackPayment(BigDecimal.ZERO.subtract(amount));
                    agentAccount.setHistoryPayment(amount);
                    data.put("backPay",amount);
                }else {
                    agentAccount.setNoBackPayment(BigDecimal.ZERO.subtract(agentAcc.getNoBackPayment()));
                    agentAccount.setHistoryPayment(agentAcc.getNoBackPayment());
                    data.put("backPay",agentAcc.getNoBackPayment());
                }

                agentAccount.setAccumulatedRecharge(amount);
                bill.setNoBackPayment(agentAccount.getNoBackPayment().add(agentAcc.getNoBackPayment()));
                bill.setHistoryPayment(agentAccount.getHistoryPayment().add(agentAcc.getHistoryPayment()));
            }else {
                agentAccount.setAccumulatedRecharge(amount);
                bill.setHistoryPayment(agentAcc.getHistoryPayment());
                bill.setNoBackPayment(agentAcc.getNoBackPayment());
            }
            agentAccount.setBalance(amount);

            bill.setPaymentType(PaymentType.在线充值.getValue());
            bill.setFinancialType(FinancialType.入账.getValue());
            bill.setPaymentId(params.get("paymentId").toString());


        }else if(Objects.equals(PaymentType.扣减.getValue(),type)){
            if(agentAcc.getBalance().compareTo(amount)==-1){
                data.put("result", SysConstant.FAIL);
                data.put("msg", "代理商账号余额不足！");
                return data;
            }else {
                agentAccount.setBalance(BigDecimal.ZERO.subtract(amount));
            }
            bill.setPaymentType(PaymentType.扣减.getValue());
            bill.setFinancialType(FinancialType.出账.getValue());
            bill.setNoBackPayment(agentAcc.getNoBackPayment());
            bill.setHistoryPayment(agentAcc.getHistoryPayment());
        }else if(Objects.equals(PaymentType.退款.getValue(),type)){
            if(agentAcc.getBalance().compareTo(amount)==-1){
                data.put("result", SysConstant.FAIL);
                data.put("msg", "代理商账号余额不足！");
                return data;
            }else {
                agentAccount.setBalance(BigDecimal.ZERO.subtract(amount));
                agentAccount.setAccumulatedRefund(amount);
            }
            bill.setPaymentType(PaymentType.退款.getValue());
            bill.setFinancialType(FinancialType.出账.getValue());
            bill.setNoBackPayment(agentAcc.getNoBackPayment());
            bill.setHistoryPayment(agentAcc.getHistoryPayment());
        }

        bill.setAmount(amount);
        bill.setBalance(agentAccount.getBalance().add(agentAcc.getBalance()));
        bill.setRemark(params.get("remark"));
        bill.setCreditBalance(agentAcc.getCreditBalance());
        bill.setCurrentCredit(agentAcc.getCurrentCredit());


        bill.setCreateTime(new Date());

        int binsert=jsmsAgentBalanceBillService.insert(bill);

        if(binsert>0){
            logger.debug("生成代理商{}账单成功,更新信息{}！",bill.getAgentId(), JsonUtil.toJson(bill));
            int accUpdate=jsmsAgentAccountService.updateAccoutForRealTime(agentAccount);
            if(accUpdate<=0){
                data.put("result", SysConstant.FAIL);
                data.put("msg", "更新代理商账号金额失败,请联系管理员！");
                logger.error("更新代理商{}对应账户信息失败！,更新信息{}",bill.getAgentId(), JsonUtil.toJson(agentAccount));
                throw new JsmsSaleCreditException("【代理商财务操作】更新代理商账号金额失败,更新内容:"+JsonUtil.toJson(agentAccount));
            }
            logger.debug("更新代理商{}对应账户信息成功！,更新信息{}",bill.getAgentId(), JsonUtil.toJson(agentAccount));
        }else {
            data.put("result", SysConstant.FAIL);
            data.put("msg", "生成代理商账单失败,请联系管理员！");
            logger.error("生成代理商{}账单失败,更新信息{}！",bill.getAgentId(), JsonUtil.toJson(bill));
            throw new JsmsSaleCreditException("【代理商财务操作】生成代理商账单失败,更新内容:"+JsonUtil.toJson(bill));
        }
        data.put("result", SysConstant.SUCCESS);
        data.put("msg", "更新代理商余额成功！");
        return data;
    }


    /**
     *
     * @param params
     * @param
     * @return
     */
    @Transactional("message")
    public Map<String,Object> saleUpdateByBalanceOP(Map<String, String> params,JsmsAgentAccount agentAcc){
        Map<String,Object> data=new HashedMap();
        JsmsSaleCreditAccount saleAccount=new JsmsSaleCreditAccount();
        JsmsAgentInfo agent=jsmsAgentInfoService.getByAgentId(agentAcc.getAgentId());
        if(agent==null){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "代理商信息不存在！");
            return data;
        }
        if(agent.getBelongSale()==null){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "代理商不存在归属销售！");
            return data;
        }
        JsmsSaleCreditAccount saleAcc=jsmsSaleCreditAccountService.getBySaleId(agent.getBelongSale());
        logger.debug("操作之前销售账号信息={}",JsonUtil.toJson(saleAcc));
        if(saleAcc==null){
            data.put("result", SysConstant.FAIL);
            data.put("msg", "该代理商归属销售不存在！");
            return data;
        }
        BigDecimal backmoney=new BigDecimal(params.get("backPay"));

        saleAccount.setSaleId(saleAcc.getSaleId());
        saleAccount.setAgentHistoryPayment(backmoney);
        saleAccount.setNoBackPayment(BigDecimal.ZERO.subtract(backmoney));


        JsmsSaleCreditBill bill=new JsmsSaleCreditBill();
        bill.setSaleId(saleAcc.getSaleId());
        bill.setBusinessType(BusinessType.客户回款.getValue());
        bill.setFinancialType(FinancialType.入账.getValue());
        bill.setAdminId(Long.valueOf(params.get("userId")));
        bill.setAmount(backmoney);
        bill.setObjectId(agent.getAgentId().longValue());
        bill.setObjectType(ObjectType.代理商.getValue());

        bill.setRemark(params.get("remark"));
        bill.setCurrentCredit(saleAcc.getCurrentCredit());
        bill.setNoBackPayment(saleAccount.getNoBackPayment().add(saleAcc.getNoBackPayment()));
        bill.setAgentHistoryPayment(saleAccount.getAgentHistoryPayment().add(saleAcc.getAgentHistoryPayment()));
        bill.setFinancialHistoryCredit(saleAcc.getFinancialHistoryCredit());
        bill.setSaleHistoryCredit(saleAcc.getSaleHistoryCredit());
        bill.setCreateTime(new Date());


        int binsert=jsmsSaleCreditBillService.insert(bill);
        if(binsert>0){
            logger.debug("生成代理商{}回款销售账单成功,更新信息{}！",bill.getObjectId(), JsonUtil.toJson(bill));
            int accUpdate=jsmsSaleCreditAccountService.updateAccountForRealTime(saleAccount);
            if(accUpdate<=0){
                data.put("result", SysConstant.FAIL);
                data.put("msg", "更新销售授信账号失败,请联系管理员！");
                logger.error("更新代理商{}对应销售回款信息失败！,更新信息{}",bill.getObjectId(), JsonUtil.toJson(saleAccount));
                throw new JsmsSaleCreditException("【代理商财务操作】更新销售授信账号失败,更新内容:"+JsonUtil.toJson(saleAccount));
            }
            logger.debug("更新代理商{}对应销售回款账户信息成功！,更新信息{}",bill.getObjectId(), JsonUtil.toJson(saleAccount));
        }else {
            data.put("result", SysConstant.FAIL);
            data.put("msg", "生成销售授信账单失败,请联系管理员！");
            logger.error("生成代理商{}回款销售账单失败,更新信息{}！",bill.getObjectId(), JsonUtil.toJson(bill));
            throw new JsmsSaleCreditException("【代理商财务操作】回款销售账单失败,更新内容:"+JsonUtil.toJson(bill));
        }
        data.put("result", SysConstant.SUCCESS);
        data.put("msg", "更新销售回款成功！");
        return data;
    }
}
