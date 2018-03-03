package com.jsmsframework.order.product.service;

import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.entity.JsmsAgentClientParam;
import com.jsmsframework.common.enums.FinancialType;
import com.jsmsframework.common.enums.PaymentType;
import com.jsmsframework.common.enums.ProductType;
import com.jsmsframework.common.service.JsmsAgentClientParamService;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import com.jsmsframework.finance.entity.JsmsAgentRebateBill;
import com.jsmsframework.finance.entity.JsmsOemAgentAccountStatistics;
import com.jsmsframework.finance.service.JsmsAgentAccountService;
import com.jsmsframework.finance.service.JsmsAgentBalanceBillService;
import com.jsmsframework.finance.service.JsmsAgentRebateBillService;
import com.jsmsframework.finance.service.JsmsOemAgentAccountStatisticsService;
import com.jsmsframework.order.entity.JsmsOemAgentOrder;
import com.jsmsframework.order.entity.JsmsOemAgentPool;
import com.jsmsframework.order.product.entity.OEMAgentPurchaseInfo;
import com.jsmsframework.order.product.exception.JsmsOEMAgentOrderProductException;
import com.jsmsframework.order.service.JsmsOemAgentOrderService;
import com.jsmsframework.order.service.JsmsOemAgentPoolService;
import com.jsmsframework.product.entity.JsmsOemAgentProduct;
import com.jsmsframework.product.entity.JsmsOemProductInfo;
import com.jsmsframework.product.service.JsmsOemAgentProductService;
import com.jsmsframework.product.service.JsmsOemProductInfoService;
import com.jsmsframework.user.service.JsmsAgentInfoService;
import com.jsmsframework.user.service.JsmsOemDataConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class JsmsOEMAgentOrderProductServiceImpl implements JsmsOEMAgentOrderProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsOEMAgentOrderProductServiceImpl.class);

    @Autowired
    private JsmsOemProductInfoService jsmsOemProductInfoService;
    @Autowired
    private JsmsAgentAccountService jsmsAgentAccountService;
    @Autowired
    private JsmsAgentBalanceBillService jsmsAgentBalanceBillService;
    @Autowired
    private JsmsAgentInfoService jsmsAgentInfoService;
    @Autowired
    private JsmsOemDataConfigService jsmsOemDataConfigService;
    @Autowired
    private JsmsAgentClientParamService jsmsAgentClientParamService;
    @Autowired
    private JsmsOemAgentPoolService jsmsOemAgentPoolService;
    @Autowired
    private JsmsOemAgentOrderService jsmsOemAgentOrderService;
    @Autowired
    private JsmsAgentRebateBillService jsmsAgentRebateBillService;
    @Autowired
    private JsmsOemAgentAccountStatisticsService jsmsOemAgentAccountStatisticsService;

    @Autowired
    private JsmsOemAgentProductService jsmsOemAgentProductService;


    @Override
    @Transactional
    public ResultVO purchaseOrder(Integer agentId, BigDecimal rebateUseRadio, Integer productId, BigDecimal purchaseNum, Long adminId , JsmsOemAgentOrder jsmsOemAgentOrder) {

        LOGGER.debug("oem代理商平台-短信购买-确认下单开始======================================");

        ResultVO resultVO = ResultVO.successDefault();
        JsmsOemProductInfo oemProductInfo = jsmsOemProductInfoService.getByProductId(productId);
        // 检查订单是否已经到期，到期的不可继续购买
        Date now = new Date();
        if (oemProductInfo.getDueTime() != null && now.getTime() > oemProductInfo.getDueTime().getTime()){
            StringBuilder msg = new StringBuilder("产品代码为[").append(oemProductInfo.getProductCode()).append("]的产品已过期!");
            setFail(resultVO, msg.toString());
            return resultVO;
        }
        // 求出账号余额和授信额度
        JsmsAgentAccount agentAccount = jsmsAgentAccountService.getByAgentId(agentId);

        // 可使用的返点金额 = 返点比例 * 返点收入
        BigDecimal rebateUseable = agentAccount.getRebateIncome().multiply(rebateUseRadio);

        LOGGER.debug("当前账户的返点比例为：----> {}",rebateUseRadio.toString());
        LOGGER.debug("当前账户的可以使用返点金额为: ----> {}",rebateUseable.toString());
        LOGGER.debug("即将检查是否可以使用 ----------------------");

        // 检查订单
        LOGGER.debug("检查订单开始========================================================");
        OEMAgentPurchaseInfo commonPurchaseInfo = this.checkOrder(agentId,oemProductInfo, agentAccount, purchaseNum,rebateUseable,resultVO);
        LOGGER.debug("检查订单结束========================================================");

        if (!resultVO.isSuccess()) {
            return resultVO;
        }

        LOGGER.debug("订单金额: {} =  折扣价*数量 ;订单实际消耗金额 : {} ; 是否使用返点 = {}------------->{}",commonPurchaseInfo.getOrderAmount(),commonPurchaseInfo.getActualOrderPrice(),commonPurchaseInfo.isUseRebate());

        // 代理商短信池
        LOGGER.debug("更新代理商短信池开始========================================================");

        // 短信池需要填充单价，并返回池ID
        Long agentPoolId = this.updateOemAgentPool(agentId, oemProductInfo, now, purchaseNum);
        LOGGER.debug("更新代理商短信池结束========================================================");

        // 生成订单
        LOGGER.debug("创建订单开始========================================================");
        // 生成规则和原来的一样，只是换了订单的标识4
//        StringBuffer orderId = new StringBuffer("");

        // 普通短信需要设置单价、订单金额，order_no需要填充订单ID字段
//        this.createOEMAgentOrder(productInfo, params, order_amount, agent_id, now, order_id, agent_pool_id);
        JsmsOemAgentOrder oemAgentOrder = this.createOEMAgentOrder(agentId, oemProductInfo, jsmsOemAgentOrder, commonPurchaseInfo.getOrderAmount(), purchaseNum, now, agentPoolId);
        LOGGER.debug("创建订单结束========================================================");

        // 更新代理商的账户
        // 判断是否使用返点
        if (commonPurchaseInfo.isUseRebate()) {
            LOGGER.debug("该笔订单使用返点比例为：======= {}",rebateUseRadio);
            LOGGER.debug("该笔订单使用返点金额为========={}",rebateUseable);
        } else {
            rebateUseable = BigDecimal.ZERO;
            LOGGER.debug("该笔订单没有使用返点========================================================");

        }

        LOGGER.debug("更新账户余额开始========================================================");

        /**
         * 剩余的余额 = 代理商账户余额 - 订单实际金额
         */
        //BigDecimal remainBalance = agentAccount.getBalance().subtract(commonPurchaseInfo.getActualOrderPrice());
        BigDecimal currentCredit = null;
        BigDecimal noBackPayment = null;
        if (!(agentAccount.getBalance().compareTo(commonPurchaseInfo.getActualOrderPrice()) >= 0)) {
            /**
             * 代理商账户余额小于购买余额
             *
             */
            if (agentAccount.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                /**
                 * 代理商账户余额大于0
                 * 1、授信余额=当前授信余额-（购买金额-原余额）
                 * 2、欠款=购买金额-原余额
                 */
                //(保证事物在sql中进行相减)
                currentCredit = commonPurchaseInfo.getActualOrderPrice().subtract(agentAccount.getBalance());
                //(保证事物在sql中进行相加)
                noBackPayment = commonPurchaseInfo.getActualOrderPrice().subtract(agentAccount.getBalance());
            }else {
                /**
                 * 1、授信余额=原授信余额-购买金额
                 * 2、欠款=原欠款+购买金额
                 */
                //(保证事物在sql中进行相减)
                currentCredit = commonPurchaseInfo.getActualOrderPrice();
                //(保证事物在sql中进行相加)
                noBackPayment = commonPurchaseInfo.getActualOrderPrice();
            }
        }

        //共计消费 =  折后总价 + 实际消耗金额
//        BigDecimal accumulatedConsume = commonPurchaseInfo.getOrderAmount().add(commonPurchaseInfo.getActualOrderPrice());
        this.updateAgentAccount(agentId, commonPurchaseInfo.getActualOrderPrice(), rebateUseable, commonPurchaseInfo.getActualOrderPrice(), rebateUseable, currentCredit, noBackPayment);
        LOGGER.debug("更新账户余额结束========================================================");


        // 生成余额账单
        LOGGER.debug("生成余额账单开始========================================================");
        // 获取最新代理商账户表
        agentAccount = jsmsAgentAccountService.getByAgentId(agentId);
        this.insertAgentBalanceBill(now, oemAgentOrder.getOrderId(), adminId, commonPurchaseInfo, agentAccount, oemProductInfo);
        LOGGER.debug("生成余额账单结束========================================================");

        // 剩余的返点金额
        BigDecimal remainRebatAmount = agentAccount.getRebateIncome().subtract(rebateUseable);

        /**
         * 生成返点账单
         */
        if (commonPurchaseInfo.isUseRebate()) {
            LOGGER.debug("生成返点账单开始========================================================");
            this.insertAgentRebateBill(now, agentId, jsmsOemAgentOrder.getOrderId(), rebateUseable, remainRebatAmount);
            LOGGER.debug("生成返点账单结束========================================================");
        }

        // 生累计充值记录
        LOGGER.debug("生成累计充值记录开始========================================================");
        this.updateOemAgentAccountStatistics(agentId, purchaseNum, oemProductInfo.getProductType());
        LOGGER.debug("生成累计充值记录结束========================================================");

        LOGGER.debug("oem代理商平台-短信购买-确认下单结束======================================");

        resultVO.setSuccess(true);
        resultVO.setMsg("购买成功!");
        return resultVO;

    }

    /**
     * 检查订单
     */
    private OEMAgentPurchaseInfo checkOrder(Integer agentId, JsmsOemProductInfo oemProductInfo, JsmsAgentAccount agentAccount,
                                            BigDecimal purchaseNum, BigDecimal rebateUseable, ResultVO resultVO){

        OEMAgentPurchaseInfo commonPurchaseInfo = new OEMAgentPurchaseInfo();

        // 求出折扣之后的价格
        BigDecimal priceDiscount = this.queryPriceForDiscount(oemProductInfo, agentId);
        /**
         * 订单金额(折后总价) = 折扣价*数量
         */
        commonPurchaseInfo.setOrderAmount(priceDiscount.multiply(purchaseNum));

        /**
         * 区分普通产品、国际产品
         * (1) 国际产品不使用返点
         * (2) 普通产品折后价 大于 10
         */
        if (ProductType.国际.getValue().equals(oemProductInfo.getProductType())) {
            /**
             * 国际产品
             * 折扣之后的价格*数量
             * 订单实际消耗金额
             */
            commonPurchaseInfo.setActualOrderPrice(commonPurchaseInfo.getOrderAmount());
            commonPurchaseInfo.setUseRebate(false);
            LOGGER.debug("该笔订单产品类型为：国际 ===================================================【不能】使用返点");

        } else {
            /**
             * 普通产品
             * 折扣之后的价格*数量 - 可以使用的返点
             * 订单实际消耗金额
             */
            if (commonPurchaseInfo.getOrderAmount().compareTo(new BigDecimal("10"))  >= 0) {
                if(commonPurchaseInfo.getOrderAmount().compareTo(rebateUseable) >= 0){
                    commonPurchaseInfo.setActualOrderPrice(commonPurchaseInfo.getOrderAmount().subtract(rebateUseable));
                }else{
                    rebateUseable = commonPurchaseInfo.getOrderAmount();
                    commonPurchaseInfo.setActualOrderPrice(BigDecimal.ZERO);
                }
                commonPurchaseInfo.setUseRebate(true);
                LOGGER.debug("该笔订单【可以】使用返点========================================================");
            } else {
                commonPurchaseInfo.setActualOrderPrice(commonPurchaseInfo.getOrderAmount());
                commonPurchaseInfo.setUseRebate(false);
                LOGGER.debug("该笔订单【不能】使用返点，普通产品折后价需大于10 ， 当前折后价为 ------------> {}",commonPurchaseInfo.getOrderAmount());
            }
        }

        /**
         * 可用额度：（1）当余额大于0的时候，可用额度=余额+授信余额；（2）当余额小于0的时候，可用额度=授信余额
         */
        BigDecimal availableAmount;
        BigDecimal balance = agentAccount.getBalance();
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            //（1）当余额小于等于0的时候，可用额度=授信余额
            availableAmount = agentAccount.getCurrentCredit();
        }else{
            //（2）当余额大于0的时候，可用额度=余额+授信余额；
            availableAmount = balance.add(agentAccount.getCurrentCredit());
        }
        if (commonPurchaseInfo.getActualOrderPrice().compareTo(availableAmount) > 0) { // 如果 实际消费金额大于 可用金额
            setFail(resultVO,"您的可用余额不足，请调整购买短信数量或者金额!");
        }
        LOGGER.debug("resultVO --> {}",resultVO);
        return commonPurchaseInfo;
    }

    // 获取产品折扣之后的价格(对于普通产品是折后的价格，对于国际产品是 2个折扣率相乘)
    private BigDecimal queryPriceForDiscount(JsmsOemProductInfo oemProductInfo, Integer agentId) {

        BigDecimal priceDiscount = BigDecimal.ZERO;
        JsmsOemAgentProduct tempParam = new JsmsOemAgentProduct();
        tempParam.setAgentId(agentId);
        tempParam.setProductId(oemProductInfo.getProductId());
        /*JsmsOemAgentProduct oemAgentProduct = jsmsOemAgentProductService.getByAgentIdAndProductId(tempParam);
        if(oemAgentProduct!=null)
            oemProductInfo.setUnitPrice(oemAgentProduct.getDiscountPrice());*/
        JsmsOemAgentProduct oemAgentProduct = jsmsOemAgentProductService.getByAgentIdAndProductId(tempParam); // todo 新产品包没有折扣率

        if(ProductType.国际.getValue().equals(oemProductInfo.getProductType())) {
            // 国际短信t_sms_oem_agent_product.gj_sms_discount
            JsmsAgentClientParam oemGjSmsDiscount = jsmsAgentClientParamService.getByParamKey("OEM_GJ_SMS_DISCOUNT");

            BigDecimal OEM_GJ_SMS_DISCOUNT = oemGjSmsDiscount != null ? new BigDecimal(oemGjSmsDiscount.getParamValue()) : BigDecimal.ONE;
            priceDiscount = oemAgentProduct != null ?  oemAgentProduct.getGjSmsDiscount().multiply(OEM_GJ_SMS_DISCOUNT):OEM_GJ_SMS_DISCOUNT;
        }else {
            // 普通短信
            if(oemAgentProduct != null){
                oemProductInfo.setUnitPrice(oemAgentProduct.getDiscountPrice());
            }
            priceDiscount = oemProductInfo.getUnitPrice(); // agentId + productId
        }
        return priceDiscount;
    }

    /**
     * 修改OEM代理商短信池
     * 购买订单时, 修改对应的代理商短信信息, 有则修改,无则添加
     */
    private Long updateOemAgentPool(Integer agentId, JsmsOemProductInfo oemProductInfo, Date now, BigDecimal purchaseNum) {
        JsmsOemAgentPool queryObjParams = new JsmsOemAgentPool();
        queryObjParams.setStatus(0); // 状态，0：正常，1：停用
        queryObjParams.setAgentId(agentId);
        queryObjParams.setAreaCode(oemProductInfo.getAreaCode());
        queryObjParams.setOperatorCode(oemProductInfo.getOperatorCode());
        queryObjParams.setProductType(oemProductInfo.getProductType());
        queryObjParams.setDueTime(oemProductInfo.getDueTime());
        // 非国际短信，根据单价去查询
        if (!ProductType.国际.getValue().equals(oemProductInfo.getProductType())) {

            JsmsOemAgentProduct tempParam = new JsmsOemAgentProduct();
            tempParam.setAgentId(agentId);
            tempParam.setProductId(oemProductInfo.getProductId());
            /*JsmsOemAgentProduct oemAgentProduct = jsmsOemAgentProductService.getByAgentIdAndProductId(tempParam);
            if(oemAgentProduct!=null)
                oemProductInfo.setUnitPrice(oemAgentProduct.getDiscountPrice());*/
            queryObjParams.setUnitPrice(oemProductInfo.getUnitPrice() != null ? oemProductInfo.getUnitPrice() : BigDecimal.ZERO);
        }
        JsmsOemAgentPool oldOEMAgentPool = jsmsOemAgentPoolService.getByAgentPoolInfo(queryObjParams);
        /**
         * 未查询到记录 添加
         */
        if (oldOEMAgentPool == null) {

            JsmsOemAgentPool insertObj = new JsmsOemAgentPool();
            insertObj.setAgentId(agentId);
            insertObj.setProductType(oemProductInfo.getProductType());
            insertObj.setOperatorCode(oemProductInfo.getOperatorCode());
            insertObj.setAreaCode(oemProductInfo.getAreaCode());
            insertObj.setDueTime(oemProductInfo.getDueTime());
            insertObj.setStatus(0); // 状态，△ 0：正常，1：停用
            if (ProductType.国际.getValue().equals(oemProductInfo.getProductType())) {
                insertObj.setRemainNumber(0);
                insertObj.setRemainAmount(purchaseNum);// 国际产品，买多少，短信池就增加多少
            } else {
                // 普通
                insertObj.setRemainNumber(purchaseNum.intValue()); // 添加购买数量
                insertObj.setRemainAmount(BigDecimal.ZERO);
                //增加单价

                insertObj.setUnitPrice(queryObjParams.getUnitPrice());
            }

            insertObj.setUpdateTime(now);
            int insert = jsmsOemAgentPoolService.insert(insertObj);
            LOGGER.debug("OEM代理商短信池 t_sms_oem_agent_pool.insert = {} , insertObj -->{}",insert, JsonUtil.toJson(insertObj));
            return insertObj.getAgentPoolId();
        } else {
            /**
             * 查询到记录 更新
             */

            Long agentPoolId = oldOEMAgentPool.getAgentPoolId();
            Integer productType = oemProductInfo.getProductType();
            int update = jsmsOemAgentPoolService.updateForAddAgentPoolRemainNum(agentPoolId, purchaseNum, productType, now);
            LOGGER.debug("OEM代理商短信池 t_sms_oem_agent_pool.更新记录数 = {} 更新条件：agentPoolId = {}, purchaseNum = {}, productType = {}, updateTime = {}",
                            update, agentPoolId, purchaseNum, productType, now);
            return oldOEMAgentPool.getAgentPoolId();
        }
    }

    /**
     * 创建订单
     */
    private JsmsOemAgentOrder createOEMAgentOrder(Integer agentId, JsmsOemProductInfo oemProductInfo, JsmsOemAgentOrder jsmsOemAgentOrder,
                                                  BigDecimal orderAmount, BigDecimal purchaseNum, Date now, Long agentPoolId) {

        jsmsOemAgentOrder.setOrderType(0); // 0：OEM代理商购买，1：OEM代理商分发，2：OEM代理商回退
        jsmsOemAgentOrder.setProductId(oemProductInfo.getProductId());
        jsmsOemAgentOrder.setProductCode(oemProductInfo.getProductCode());
        jsmsOemAgentOrder.setProductType(oemProductInfo.getProductType());
        jsmsOemAgentOrder.setOperatorCode(oemProductInfo.getOperatorCode());
        jsmsOemAgentOrder.setAreaCode(oemProductInfo.getAreaCode());
        jsmsOemAgentOrder.setAgentPoolId(agentPoolId);// 添加订单的短信池ID
        jsmsOemAgentOrder.setProductName(oemProductInfo.getProductName());
        /**
         * 代理商订单价格为: 订单金额(折后总价)
         */
        jsmsOemAgentOrder.setOrderAmount(orderAmount);
        if (ProductType.国际.getValue().equals(oemProductInfo.getProductType())) {
            // 国际产品为页面填写的购买价格，普通产品为null
            jsmsOemAgentOrder.setUnitPrice(null);
            jsmsOemAgentOrder.setProductPrice(purchaseNum); // 国际产品的  数量(国际即是原价)
            jsmsOemAgentOrder.setOrderNumber(null);
        }else{
            jsmsOemAgentOrder.setUnitPrice(oemProductInfo.getUnitPrice());
            jsmsOemAgentOrder.setOrderNumber(purchaseNum.intValue()); // 购买数量
            jsmsOemAgentOrder.setProductPrice(null);
        }
        jsmsOemAgentOrder.setAgentId(agentId);
        jsmsOemAgentOrder.setClientId("00000");// 订单类型为1、2时填用户帐号，订单类型为0时填'00000'
        jsmsOemAgentOrder.setName("云之讯"); // 订单类型为1、2时填用户名称，订单类型为0时填'云之讯'
        jsmsOemAgentOrder.setDueTime(oemProductInfo.getDueTime());
        jsmsOemAgentOrder.setCreateTime(now);
        int insert = jsmsOemAgentOrderService.insert(jsmsOemAgentOrder);
        LOGGER.debug("创建订单 t_sms_oem_agent_order.insert = {} , insertObj --> {}",insert, JsonUtil.toJson(jsmsOemAgentOrder));
        return jsmsOemAgentOrder;
    }

    /**
     * 生成余额账单
     */
    private JsmsAgentBalanceBill insertAgentBalanceBill(Date now, Long orderId, Long adminId, OEMAgentPurchaseInfo commonPurchaseInfo, JsmsAgentAccount agentAccount, JsmsOemProductInfo oemProductInfo) {
        JsmsAgentBalanceBill insertAgentBalanceBill = new JsmsAgentBalanceBill();
        insertAgentBalanceBill.setAgentId(agentAccount.getAgentId());
        // 购买产品包都属于3
        insertAgentBalanceBill.setPaymentType(PaymentType.购买产品包.getValue());// 业务类型，0：充值，1：扣减，2：佣金转余额，3：购买产品包，4：余额转结算
        // 退款，5：赠送
        insertAgentBalanceBill.setFinancialType(FinancialType.出账.getValue());// '财务类型，0：入账，1：出账',
        insertAgentBalanceBill.setAmount(commonPurchaseInfo.getActualOrderPrice()); // 金额，使用实际金额
        insertAgentBalanceBill.setBalance(agentAccount.getBalance());// 剩余余额

        insertAgentBalanceBill.setCreateTime(now);
        insertAgentBalanceBill.setOrderId(orderId);
        insertAgentBalanceBill.setAdminId(adminId);

        insertAgentBalanceBill.setCreditBalance(agentAccount.getCreditBalance());
        insertAgentBalanceBill.setHistoryPayment(agentAccount.getHistoryPayment());
        insertAgentBalanceBill.setCurrentCredit(agentAccount.getCurrentCredit());
        insertAgentBalanceBill.setNoBackPayment(agentAccount.getNoBackPayment());

        // oem 不用填写 client_id
        String remark = null;

        if (ProductType.国际.getValue().equals(oemProductInfo.getProductType())) {
            remark = new StringBuilder("购买")
                    .append(ProductType.getDescByValue(oemProductInfo.getProductType()))
                    .append("短信，实际订单价格为：")
                    .append(commonPurchaseInfo.getOrderAmount().setScale(4, BigDecimal.ROUND_DOWN))
                    .append("元")
                    .toString();
        } else {
            remark = new StringBuilder("购买")
                    .append(ProductType.getDescByValue(oemProductInfo.getProductType()))
                    .append("短信")
                    .toString();
        }

        insertAgentBalanceBill.setRemark(remark);

        int insert = jsmsAgentBalanceBillService.insert(insertAgentBalanceBill);
        LOGGER.debug("生成余额账单记录 t_sms_agent_balance_bill.insert = {} , insertAgentBalanceBill --> {}", insert, JsonUtil.toJson(insertAgentBalanceBill));
        return insertAgentBalanceBill;
    }

    /**
     * 更新代理商账户表(更新 账户余额、返点余额、累计消费、累计返点支出)
     */
    private JsmsAgentAccount updateAgentAccount(Integer agentId, BigDecimal balance, BigDecimal rebateIncome,
                                                BigDecimal accumulatedConsume, BigDecimal accumulatedRebatePay, BigDecimal currentCredit, BigDecimal noBackPayment) {
        JsmsAgentAccount updateJsmsAgentAccount = new JsmsAgentAccount();
        updateJsmsAgentAccount.setAgentId(agentId);

        updateJsmsAgentAccount.setRebateIncome(rebateIncome);

        updateJsmsAgentAccount.setAccumulatedRebatePay(accumulatedRebatePay);// 累计返点

        updateJsmsAgentAccount.setBalance(balance);
        updateJsmsAgentAccount.setCurrentCredit(currentCredit);
        updateJsmsAgentAccount.setNoBackPayment(noBackPayment);
        updateJsmsAgentAccount.setAccumulatedConsume(accumulatedConsume);// 累计消费

        int update = jsmsAgentAccountService.updateAfterConsume(updateJsmsAgentAccount);
        LOGGER.debug("更新 账户余额、返点余额、累计消费、累计返点支出 t_sms_agent_account.update = {} , updateJsmsAgentAccount --> {}",update, JsonUtil.toJson(updateJsmsAgentAccount));
        return updateJsmsAgentAccount;
    }

    /**
     * 生成返点账单
     */
    private JsmsAgentRebateBill insertAgentRebateBill(Date now, Integer agentId, Long orderId,
                                                      BigDecimal amount, BigDecimal balance) {
        JsmsAgentRebateBill jsmsAgentRebateBill = new JsmsAgentRebateBill();

        jsmsAgentRebateBill.setAgentId(agentId);
        jsmsAgentRebateBill.setPaymentType(1);// '业务类型,0:返点收入,1:抵扣'
        jsmsAgentRebateBill.setFinancialType(1);// '财务类型,0:入账,1:出账'
        jsmsAgentRebateBill.setOrderId(orderId);
        jsmsAgentRebateBill.setAmount(amount);// 使用的返点金额
        jsmsAgentRebateBill.setBalance(balance);// 剩余的返点余额
        jsmsAgentRebateBill.setCreateTime(now);
        jsmsAgentRebateBill.setRemark("购买短信");

        int insert = jsmsAgentRebateBillService.insert(jsmsAgentRebateBill);
        LOGGER.debug("生成返点账单 t_sms_agent_rebate_bill.insert = {} , jsmsAgentRebateBill --> {}",insert, JsonUtil.toJson(jsmsAgentRebateBill));
        return jsmsAgentRebateBill;
    }

    /**
     * 更新OEM代理商帐户统计表
     */
    private JsmsOemAgentAccountStatistics updateOemAgentAccountStatistics(Integer agentId, BigDecimal purchaseNum, Integer productType) {

        JsmsOemAgentAccountStatistics insertOrUpdateObj = new JsmsOemAgentAccountStatistics();
        insertOrUpdateObj.setAgentId(agentId);
        /*
         * 0：行业，
         * 1：营销，
         * 2：国际，
         * 3：验证码，
         * 4：通知，
         */
        if(ProductType.行业.getValue().equals(productType)){
            insertOrUpdateObj.setHyTotalPurchaseNumber(purchaseNum.intValue());
        }else if(ProductType.营销.getValue().equals(productType)){
            insertOrUpdateObj.setYxTotalPurchaseNumber(purchaseNum.intValue());
        }else if(ProductType.国际.getValue().equals(productType)){
            insertOrUpdateObj.setGjTotalPurchaseAmount(purchaseNum);
        }else if(ProductType.验证码.getValue().equals(productType)){
            insertOrUpdateObj.setYzmTotalPurchaseNumber(purchaseNum.intValue());
        }else if(ProductType.通知.getValue().equals(productType)){
            insertOrUpdateObj.setTzTotalPurchaseNumber(purchaseNum.intValue());
        }

        JsmsOemAgentAccountStatistics oemAgentAccountStatistics = jsmsOemAgentAccountStatisticsService.getByAgentId(agentId);

        if (oemAgentAccountStatistics == null) {
            int insert = jsmsOemAgentAccountStatisticsService.insert(insertOrUpdateObj);
            LOGGER.debug("增加OEM代理商帐户统计表 t_sms_oem_agent_account_statistics.insert = {} , insertObj --> {}",insert, JsonUtil.toJson(insertOrUpdateObj));
            return insertOrUpdateObj;
        } else {
            int update = jsmsOemAgentAccountStatisticsService.updateForAddPurchaseNumber(insertOrUpdateObj);
            LOGGER.debug("更新OEM代理商帐户统计表 t_sms_oem_agent_account_statistics.update = {} , updateObj --> {}",update, JsonUtil.toJson(insertOrUpdateObj));
            return insertOrUpdateObj;
        }
    }

    private void setFail(ResultVO resultVO, String msg){
        resultVO.setMsg(msg);
        resultVO.setSuccess(false);
        throw new JsmsOEMAgentOrderProductException(msg);
    }

    private void setSuccess(ResultVO resultVO, String msg){
        resultVO.setMsg(msg);
        resultVO.setSuccess(true);
    }



}
