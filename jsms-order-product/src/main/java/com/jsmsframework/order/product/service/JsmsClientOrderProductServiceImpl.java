package com.jsmsframework.order.product.service;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.enums.*;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import com.jsmsframework.finance.service.JsmsAgentAccountService;
import com.jsmsframework.finance.service.JsmsAgentBalanceBillService;
import com.jsmsframework.order.entity.JsmsClientOrder;
import com.jsmsframework.order.exception.JsmsClientOrderException;
import com.jsmsframework.order.product.exception.JsmsClientOrderProductException;
import com.jsmsframework.order.service.JsmsClientOrderService;
import com.jsmsframework.product.entity.JsmsClientProduct;
import com.jsmsframework.product.entity.JsmsProductInfo;
import com.jsmsframework.product.service.JsmsAgentProductService;
import com.jsmsframework.product.service.JsmsClientProductService;
import com.jsmsframework.product.service.JsmsProductInfoService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class JsmsClientOrderProductServiceImpl implements JsmsClientOrderProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsClientOrderProductServiceImpl.class);

    @Autowired
    private JsmsProductInfoService jsmsProductInfoService;
    @Autowired
    private JsmsAgentProductService jsmsAgentProductService;
    @Autowired
    private JsmsClientProductService jsmsClientProductService;
    @Autowired
    private JsmsClientOrderService jsmsClientOrderService;
    @Autowired
    private JsmsAgentAccountService jsmsAgentAccountService;
    @Autowired
    private JsmsAgentBalanceBillService jsmsAgentBalanceBillService;

    @Override
    @Transactional
    public Result createOrder(List<String> productCodes, List<Integer> purchaseNums, List<Long> orderIds, ClientOrderType orderType, Integer agentId, String clientid) {
        if (agentId == null || clientid == null) {
            throw new JsmsClientOrderException("参数不正确");
        }
        if (productCodes == null || productCodes.isEmpty()) {
            throw new JsmsClientOrderException("请选择你需要购买的商品");
        }
        if (purchaseNums == null || purchaseNums.isEmpty()) {
            throw new JsmsClientOrderException("请选择你的购买的数量");
        }
        if (productCodes.size() != purchaseNums.size()) {
            throw new JsmsClientOrderException("商品和数量对应不上，请检查后再提交");
        }
        for (int i = 0; i < purchaseNums.size(); i++) {
            int purchaseNum = purchaseNums.get(i);
            if (purchaseNum <= 0) {
                throw new JsmsClientOrderException("购买数量必须大于0，请检查后再提交");
            }
        }

        //1.获取商品信息
        List<JsmsProductInfo> productInfos = jsmsProductInfoService.getByProductCodes(productCodes);
        List<String> weishangjiaList = new ArrayList<>();
        List<String> yixiajiaList = new ArrayList<>();
        List<String> canceldailiList = new ArrayList<>();
        List<Integer> productIds = new ArrayList<>();

        List<JsmsClientOrder> clientOrders = new ArrayList<>();
        Date now = new Date();
        for (int i = 0; i < productInfos.size(); i++) {
            JsmsProductInfo productInfo = productInfos.get(i);
            boolean isValidProduct = true;
            //2.是否已下架
            if (productInfo.getStatus().intValue() == ProductStatus.已下架.getValue().intValue()) {
                yixiajiaList.add(productInfo.getProductName());
                isValidProduct = false;
            }
            //3.是否待上架
            if (productInfo.getStatus().intValue() == ProductStatus.待上架.getValue().intValue()) {
                weishangjiaList.add(productInfo.getProductName());
                isValidProduct = false;
            }
            //4.是否未代理
            if (jsmsAgentProductService.isNotSaleForThisAgent(agentId, productInfo.getProductId())) {
                canceldailiList.add(productInfo.getProductName());
                isValidProduct = false;
            }

            if (!isValidProduct) {
                break;
            }

            //5.获取折扣价
            JsmsClientProduct clientProduct = jsmsClientProductService.getByClientidAndProductId(agentId, clientid, productInfo.getProductId());
            if (clientProduct != null) {
                if (ProductType.国际.getValue().equals(productInfo.getProductType())) {
                    // Mod by lpjLiu 20170919 若是国际，产品价格为折扣率，若客户的折扣率为空，就设置为1，写死
                    if (clientProduct.getPriceDiscount() == null) {
                        clientProduct.setPriceDiscount(BigDecimal.ONE);
                    }
                    productInfo.setProductPrice(clientProduct.getPriceDiscount());
                } else
                    productInfo.setProductPrice(clientProduct.getGnDiscountPrice());
            }

            //6.创建订单
            JsmsClientOrder clientOrder = new JsmsClientOrder();
            clientOrder.setOrderId(orderIds.get(i));
            clientOrder.setClientId(clientid);
            clientOrder.setAgentId(agentId);
            clientOrder.setProductId(productInfo.getProductId());
            clientOrder.setProductType(productInfo.getProductType());
            clientOrder.setProductName(productInfo.getProductName());
            clientOrder.setOperatorCode(productInfo.getOperatorCode());
            clientOrder.setAreaCode(productInfo.getAreaCode());
            clientOrder.setOrderType(orderType.getValue());//品牌订单类型，0：客户购买，1：代理商代买, 2:运营代买
            clientOrder.setStatus(0);//默认是0 待审核
            clientOrder.setActivePeriod(productInfo.getActivePeriod());

            Integer productNum = purchaseNums.get(i);
            clientOrder.setProductNumber(productNum);

            clientOrder.setRemainQuantity(null);

            // Mod by lpjLiu 201709819 默认是购买件数 * 产品数量
            clientOrder.setQuantity(new BigDecimal(productNum.toString()).multiply(productInfo.getQuantity()));

            //如果产品是国际产品，订单的销售价和产品定价不用乘以数量
            if (productInfo.getProductType().intValue() == ProductType.国际.getValue().intValue()) {
                clientOrder.setSalePrice(productInfo.getProductPrice());
                clientOrder.setProductCost(productInfo.getProductCost());

                // Mod by lpjLiu 20170919 国际短信需要再乘以客户的折扣
                clientOrder.setQuantity(clientOrder.getQuantity().multiply(clientOrder.getSalePrice()));
            } else {
                clientOrder.setSalePrice(new BigDecimal(productNum.toString()).multiply(productInfo.getProductPrice()));
                clientOrder.setProductCost(new BigDecimal(productNum.toString()).multiply(productInfo.getProductCost()));
            }

            clientOrder.setCreateTime(now);
            clientOrders.add(clientOrder);

        }
        if (weishangjiaList.size() > 0 || yixiajiaList.size() > 0) {
            String message1 = weishangjiaList.size() == 0 ? "" : (weishangjiaList.toString() + "未上架，请重新下单！");
            String message2 = yixiajiaList.size() == 0 ? "" : (yixiajiaList.toString() + "已下架，请重新下单！");
            if (StringUtils.isNoneBlank(message1) && StringUtils.isNoneBlank(message2)) {
                throw new JsmsClientOrderException(message1 + "," + message2);
            } else {
                throw new JsmsClientOrderException(message1 + message2);
            }
        }
        if (canceldailiList.size() > 0) {
            throw new JsmsClientOrderException(canceldailiList.toString() + "已取消代理，请重新下单！");
        }


        //7.保存订单
        jsmsClientOrderService.insertBatch(clientOrders);

        return new Result(true, CodeEnum.SUCCESS, clientOrders, "操作成功");
    }

    @Override
    @Transactional
    public ResultVO confirmBuy(List<Long> subIds, Integer agentId, Long adminId) {
        LOGGER.debug("进入confirmBuy方法, subIds =--------------------> {}", subIds);


        List<JsmsClientOrder> clientOrderList = jsmsClientOrderService.getBySubIds(new HashSet<Long>(subIds));
        // todo 首充
        for (JsmsClientOrder clientOrder : clientOrderList) {
            ResultVO result = firstChargeCheck(clientOrder);
        }
        clientOrderList = jsmsClientOrderService.getBySubIds(new HashSet<Long>(subIds));
        return checkOrderProduct(clientOrderList, agentId, adminId);
    }

    @Override
    @Transactional
    public ResultVO confirmBuy(List<Long> subIds, Long adminId) {
        LOGGER.debug("进入confirmBuy方法, subIds =--------------------> {}", subIds);

        Integer agentId = null;
        List<JsmsClientOrder> clientOrderList = jsmsClientOrderService.getBySubIds(new HashSet<Long>(subIds));
        // todo 首充
        for (JsmsClientOrder clientOrder : clientOrderList) {
            agentId = clientOrder.getAgentId();
            ResultVO result = firstChargeCheck(clientOrder);
        }
        clientOrderList = jsmsClientOrderService.getBySubIds(new HashSet<Long>(subIds));
        if (clientOrderList.isEmpty()) {
            return ResultVO.failure("未找到订单");
        }
        return checkOrderProduct(clientOrderList, agentId, adminId);
    }

    @Override
    @Transactional
    public ResultVO confirmBuyOrder(List<Long> orderIds, Integer agentId, Long adminId) {
        LOGGER.debug("进入confirmBuyOrder方法, orderIds =--------------------> {}", orderIds);


        List<JsmsClientOrder> clientOrderList = jsmsClientOrderService.getByOrderIds(new HashSet<Long>(orderIds));
        // todo 首充
        for (JsmsClientOrder clientOrder : clientOrderList) {
            ResultVO result = firstChargeCheck(clientOrder);
        }
        clientOrderList = jsmsClientOrderService.getByOrderIds(new HashSet<Long>(orderIds));
        return checkOrderProduct(clientOrderList, agentId, adminId);
    }

    private ResultVO checkOrderProduct(List<JsmsClientOrder> clientOrderList, Integer agentId, Long adminId) {

        DateTime now = DateTime.now();
        /**
         * 订单总价
         */
        BigDecimal totalSalePrice = BigDecimal.ZERO;
        /**
         * 订单中关联的产品id
         */
        Set<Integer> productIds = new HashSet<>();

        /**
         * 1 .检查商品是被代理 & 订单状态是否为待审核
         */
        LOGGER.debug("检查商品是被代理 & 订单状态是否为待审核 --------------------> start");
        StringBuilder resultMsg = null;
        StringBuilder orderStatusMsg = null;
        Map<Long, JsmsClientOrder> unProxMap = new HashMap<>();
        for (JsmsClientOrder jsmsClientOrder : clientOrderList) {

            boolean isProxy = jsmsAgentProductService.isNotSaleForThisAgent(jsmsClientOrder.getAgentId(), jsmsClientOrder.getProductId());
            LOGGER.debug("检查商品是被代理, 代理商id = {}, 商品id = {}, 有记录则表示未代理 = {}", jsmsClientOrder.getAgentId(), jsmsClientOrder.getProductId(), isProxy);
            if (isProxy) {
                if (resultMsg == null)
                    resultMsg = new StringBuilder("商品：")
                            .append(jsmsClientOrder.getProductName());
                else
                    resultMsg.append("、")
                            .append(jsmsClientOrder.getProductName());
                unProxMap.put(Long.parseLong(jsmsClientOrder.getSubId()), jsmsClientOrder);

            }
            LOGGER.debug("检查订单状态是否为待审核, orderId = {}, status = {} (0：待审核，1：订单生效，2：订单完成，3：订单失败,4：订单取消)", jsmsClientOrder.getOrderId(), jsmsClientOrder.getStatus());
            if (!jsmsClientOrder.getStatus().equals(0)) {
                if (orderStatusMsg == null)
                    orderStatusMsg = new StringBuilder("订单：")
                            .append(jsmsClientOrder.getOrderId());
                else
                    orderStatusMsg.append("、")
                            .append(jsmsClientOrder.getOrderId());
                unProxMap.put(Long.parseLong(jsmsClientOrder.getSubId()), jsmsClientOrder);
            }
            productIds.add(jsmsClientOrder.getProductId());
            //如果是国际产品，数量即为总金额
            if (ProductType.国际.getValue().equals(jsmsClientOrder.getProductType())) {
                totalSalePrice = totalSalePrice.add(jsmsClientOrder.getQuantity());
            } else {
                totalSalePrice = totalSalePrice.add(jsmsClientOrder.getSalePrice());
            }

        }
        LOGGER.debug("检查商品是被代理 & 订单状态是否为待审核  ====================> end");

        if (resultMsg != null) {
            LOGGER.debug("商品被下架, 对应产品的订单状态将修改为 --------------------> 失败");
            this.updateFailOrder((List<JsmsClientOrder>) unProxMap.values());

            resultMsg.append("已经被取消代理，订单失败");
            return ResultVO.failure(resultMsg.toString());
        }
        if (orderStatusMsg != null) {
            LOGGER.debug("订单状态不是待审核 --------------------");

            orderStatusMsg.append("提交时，状态异常！请稍后重试...");
            return ResultVO.failure(orderStatusMsg.toString());
        }
        LOGGER.debug("订单中的productIds  --------------------> {}", productIds);
        if (productIds.isEmpty())
            return ResultVO.failure("商品已经被删除，订单失败");
        List<JsmsProductInfo> productList = jsmsProductInfoService.getByProductIds(productIds);
        LOGGER.debug("订单中的商品信息  --------------------> {}", JsonUtil.toJson(productList));

        if (productList.isEmpty())
            return ResultVO.failure("商品已经被删除，订单失败");

        /**
         * 2 .判断商品是否下架
         */
        Map<Integer, Integer> offSaleProduct = new HashMap<>();
        Map<Integer, JsmsProductInfo> productInfoMap = new HashMap<>();
        for (JsmsProductInfo jsmsProduct : productList) {
            productInfoMap.put(jsmsProduct.getProductId(), jsmsProduct);
            if (!ProductStatus.已上架.getValue().equals(jsmsProduct.getStatus())) {
                LOGGER.debug("商品被下架  --------------------> {}", JsonUtil.toJson(jsmsProduct));

                if (resultMsg == null)
                    resultMsg = new StringBuilder("商品：")
                            .append(jsmsProduct.getProductName());
                else
                    resultMsg.append("、")
                            .append(jsmsProduct.getProductName());
                offSaleProduct.put(jsmsProduct.getProductId(), jsmsProduct.getProductId());
            }
        }
        if (resultMsg != null) {
            this.updateFailOrder(clientOrderList, offSaleProduct);
            resultMsg.append("已经下架，订单失败");
            return ResultVO.failure(resultMsg.toString());
        }
        /**
         * 3 .判断余额是否充足
         */
        LOGGER.debug("判断余额是否充足  --------------------> start");
        JsmsAgentAccount agentAccount = jsmsAgentAccountService.getByAgentId(agentId);
        /**
         * 可用额度：（1）当余额大于0的时候，可用额度=余额+授信余额；（2）当余额小于0的时候，可用额度=授信余额
         */
        BigDecimal availableAmount;
        BigDecimal balance = agentAccount.getBalance();
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            availableAmount = agentAccount.getCurrentCredit();
        }else{
            availableAmount = agentAccount.getBalance().add(agentAccount.getCurrentCredit());
        }
        LOGGER.debug("订单总价  == {}", totalSalePrice);
        LOGGER.debug("可用余额  == {}", availableAmount);
        if (totalSalePrice.compareTo(availableAmount) > 0) {
            return ResultVO.failure("您的可用余额不足，请充值!");
        }
        LOGGER.debug("判断余额是否充足  ====================> end");
        for (JsmsClientOrder jsmsClientOrder : clientOrderList) {

            BigDecimal tempBalance = BigDecimal.ZERO;
            if (ProductType.国际.getValue().equals(jsmsClientOrder.getProductType())) { // 非国际
                tempBalance = jsmsClientOrder.getQuantity();
            }else{
                tempBalance = jsmsClientOrder.getSalePrice();
            }

            /**
             * 4 .扣减余额
             */
            LOGGER.debug("扣减余额  --------------------> start");
            LOGGER.debug("几个产品包分几次扣减余额  == {}", tempBalance);
            ResultVO resultVO = jsmsAgentAccountService.reduceBalance(agentId, tempBalance);
            if (resultVO.isFail()) {
                return resultVO;
            }
            LOGGER.debug("扣减余额  ====================> end");
            DateTime forever = new DateTime(9999, 1, 1, 0, 0, 0, 0);
            JsmsAgentAccount jsmsAgentAccount = (JsmsAgentAccount) resultVO.getData();
            /**
             * 5 .更新订单状态
             */
            LOGGER.debug("更新订单状态  --------------------> start");
            this.updateOrder(jsmsClientOrder, now, forever);
            LOGGER.debug("更新订单状态  ====================> end");
            /**
             * 6 .增加代理商帐户余额收支明细
             */
            LOGGER.debug("增加代理商帐户余额收支明细  --------------------> start");
            // 获取最新代理商账户表
//            agentAccount = jsmsAgentAccountService.getByAgentId(jsmsAgentAccount.getAgentId());
            this.insertAgentBalanceBill(jsmsClientOrder, now, adminId, jsmsAgentAccount);
            LOGGER.debug("增加代理商帐户余额收支明细  ====================> end");
        }
        return ResultVO.successDefault("订单购买成功!");

    }

    /**
     * 修改订单状态为失败
     *
     * @param failClientOrderList
     */
    private void updateFailOrder(List<JsmsClientOrder> failClientOrderList) {

        JsmsClientOrder clientOrder = new JsmsClientOrder();
        for (JsmsClientOrder jsmsClientOrder : failClientOrderList) {
            clientOrder.setSubId(jsmsClientOrder.getSubId());
            clientOrder.setStatus(3); // 订单失败
            jsmsClientOrderService.updateSelective(clientOrder);
        }
    }

    /**
     * 修改订单状态为失败
     *
     * @param clientOrderList
     * @param unProxyProduct
     */
    private void updateFailOrder(List<JsmsClientOrder> clientOrderList, Map<Integer, Integer> unProxyProduct) {

        JsmsClientOrder clientOrder = new JsmsClientOrder();
        for (JsmsClientOrder jsmsClientOrder : clientOrderList) {
            if (unProxyProduct.get(jsmsClientOrder.getProductId()) == null) {
                continue;
            }
            clientOrder.setSubId(jsmsClientOrder.getSubId());
            clientOrder.setStatus(3); // 订单失败
            jsmsClientOrderService.updateSelective(clientOrder);
        }
    }

    private void updateOrder(JsmsClientOrder jsmsClientOrder, DateTime now, DateTime forever) {
        JsmsClientOrder clientOrder = new JsmsClientOrder();
        clientOrder.setStatus(1); // 订单生效
        clientOrder.setSubId(jsmsClientOrder.getSubId());
        clientOrder.setEffectiveTime(now.toDate());
        clientOrder.setUpdateTime(now.toDate());
        if (jsmsClientOrder.getActivePeriod().equals(0)) {
            clientOrder.setEndTime(forever.toDate());
        } else {
            clientOrder.setEndTime(now.plusDays(jsmsClientOrder.getActivePeriod()).toDate());
        }
        if (!ProductType.国际.getValue().equals(jsmsClientOrder.getProductType())) { // 非国际
            clientOrder.setUnitPrice(jsmsClientOrder.getSalePrice().divide(jsmsClientOrder.getQuantity(), 5, BigDecimal.ROUND_HALF_DOWN));
        }
        if (jsmsClientOrder.getRemainQuantity() == null || BigDecimal.ZERO.compareTo(jsmsClientOrder.getRemainQuantity()) == 0) {
            clientOrder.setRemainQuantity(jsmsClientOrder.getQuantity());
        }
        LOGGER.debug("即将更新一笔订单, 订单信息 --------------------> {}", JsonUtil.toJson(clientOrder));
        int update = jsmsClientOrderService.updateSelective(clientOrder);
        if (update < 1) {
            throw new JsmsClientOrderProductException("更新订单状态失败!");
        }
    }

    private void insertAgentBalanceBill(JsmsClientOrder clientOrder, DateTime now, Long adminId, JsmsAgentAccount agentAccount) {
        JsmsAgentBalanceBill agentBalanceBill = new JsmsAgentBalanceBill();
        agentBalanceBill.setPaymentType(PaymentType.购买产品包.getValue());
        agentBalanceBill.setAgentId(clientOrder.getAgentId());
        agentBalanceBill.setFinancialType(FinancialType.出账.getValue()); // 财务类型，0：入账，1：出账
        agentBalanceBill.setOrderId(clientOrder.getOrderId());
        agentBalanceBill.setAdminId(adminId);
        /**
         * 余额 = 代理商账户余额
         */
        agentBalanceBill.setBalance(agentAccount.getBalance());
        agentBalanceBill.setCreateTime(now.toDate());
        agentBalanceBill.setClientId(clientOrder.getClientId());
        agentBalanceBill.setRemark(null);

        agentBalanceBill.setCreditBalance(agentAccount.getCreditBalance());
        agentBalanceBill.setHistoryPayment(agentAccount.getHistoryPayment());
        agentBalanceBill.setCurrentCredit(agentAccount.getCurrentCredit());
        agentBalanceBill.setNoBackPayment(agentAccount.getNoBackPayment());


        if (ProductType.国际.getValue().equals(clientOrder.getProductType())) { // 非国际
            agentBalanceBill.setAmount(clientOrder.getQuantity());
        } else {
            agentBalanceBill.setAmount(clientOrder.getSalePrice());
        }
        int insert = jsmsAgentBalanceBillService.insert(agentBalanceBill);
        if (insert < 1) {
            throw new JsmsClientOrderProductException("增加余额收支明细失败!");
        }
    }

    /**
     * 首充 , 临时写死代码
     */
    private ResultVO firstChargeCheck(JsmsClientOrder clientOrder) {
        JsmsProductInfo product = jsmsProductInfoService.getByProductId(clientOrder.getProductId());
        LOGGER.debug("首充检查  --------------------> start");
        if (StringUtils.isBlank(product.getProductCode())) {
            throw new JsmsClientOrderProductException("该订单里的产品包不存在！请检查后再操作!");
        }
        String remark = product.getRemark() == null ? "" : product.getRemark().trim();
        if (product.getProductCode().startsWith("scyh") && "首充优惠赠送短信包".equals(remark)) {
            LOGGER.debug("订单号{}进入首充活动处理,[只允许购买一件] , 订单信息：{} ", clientOrder.getOrderId(), JsonUtil.toJson(clientOrder));
            if (clientOrder.getProductNumber() != 1) {
                JsmsClientOrder jsmsClientOrder = new JsmsClientOrder();
                jsmsClientOrder.setStatus(3);
                jsmsClientOrder.setProductCost(clientOrder.getProductCost());
                jsmsClientOrderService.updateIdempotent(clientOrder, jsmsClientOrder);
                return ResultVO.failure("该订单的产品包【" + clientOrder.getProductName() + "】只允许购买一件！订单自动关闭，具体原因可咨询运营人员");
            }
            /**
             * 产品销售价不为0，认为是异常活动订单
             */
            if (BigDecimal.ZERO.compareTo(clientOrder.getSalePrice()) != 0) {
                LOGGER.debug("产品销售价 = {} 不为0，认为是异常活动订单", clientOrder.getSalePrice());
                JsmsClientOrder jsmsClientOrder = new JsmsClientOrder();
                jsmsClientOrder.setStatus(3);
                jsmsClientOrder.setProductCost(clientOrder.getProductCost());
                jsmsClientOrderService.updateIdempotent(clientOrder, jsmsClientOrder);
                return ResultVO.failure("该订单的销售价不为0，为异常活动订单，订单自动关闭，具体原因可咨询运营人员");
            }
            LOGGER.debug("产品信息：{}", JsonUtil.toJson(product));
            List<JsmsProductInfo> firstChargeProducts = jsmsProductInfoService.getByProductCode("scyh");
            Map<Integer, JsmsProductInfo> firstChargeProductMap = new HashMap<>();
            for (JsmsProductInfo firstChargeProduct : firstChargeProducts) {
                if (firstChargeProduct.getProductCode().startsWith("scyh")
                        && firstChargeProduct.getRemark() != null
                        && firstChargeProduct.getRemark().contains("首充优惠赠送短信包")) {
                    firstChargeProductMap.put(firstChargeProduct.getProductId(), firstChargeProduct);
                }
            }

            List<JsmsClientOrder> clientOrders = jsmsClientOrderService.getByClientId(clientOrder.getClientId());
            for (JsmsClientOrder jsmsClientOrder : clientOrders) {
                JsmsProductInfo productInfo = firstChargeProductMap.get(jsmsClientOrder.getProductId());
                if (productInfo != null) {
                    JsmsClientOrder temp = new JsmsClientOrder();
                    temp.setStatus(3);
                    temp.setProductCost(clientOrder.getProductCost());
                    jsmsClientOrderService.updateIdempotent(clientOrder, temp);
                    return ResultVO.failure("scyh（首充优惠）系列的产品限购一次，已被订单【" + clientOrder.getOrderId() + "】购买！订单自动关闭。具体原因可咨询运营人员");
                }
            }
            LOGGER.debug("修改首充总成本  --------------------> start");
            JsmsClientOrder temp = new JsmsClientOrder();
            temp.setStatus(0);
            temp.setProductCost(BigDecimal.ZERO);
            jsmsClientOrderService.updateIdempotent(clientOrder, temp);
            LOGGER.debug("修改首充总成本  ====================> end");
            return ResultVO.successDefault("scyh（首充优惠）系列产品购买成功");
        }

        LOGGER.debug("首充检查  ====================> end");
        return ResultVO.successDefault();
    }


}
