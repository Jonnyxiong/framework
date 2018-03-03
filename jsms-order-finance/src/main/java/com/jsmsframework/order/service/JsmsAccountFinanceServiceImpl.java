package com.jsmsframework.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsmsframework.common.enums.ProductType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import com.jsmsframework.finance.mapper.JsmsAgentAccountMapper;
import com.jsmsframework.finance.mapper.JsmsAgentBalanceBillMapper;
import com.jsmsframework.order.entity.JsmsOemAgentOrder;
import com.jsmsframework.order.entity.JsmsOemAgentPool;
import com.jsmsframework.order.entity.JsmsOemClientOrder;
import com.jsmsframework.order.entity.JsmsOemClientPool;
import com.jsmsframework.order.exception.JsmsOrderFinanceException;
import com.jsmsframework.order.mapper.JsmsOemAgentOrderMapper;
import com.jsmsframework.order.mapper.JsmsOemAgentPoolMapper;
import com.jsmsframework.order.mapper.JsmsOemClientOrderMapper;
import com.jsmsframework.order.mapper.JsmsOemClientPoolMapper;
import com.jsmsframework.product.entity.JsmsOemProductInfo;
import com.jsmsframework.product.mapper.JsmsOemProductInfoMapper;
import com.jsmsframework.user.entity.JsmsOemDataConfig;
import com.jsmsframework.user.mapper.JsmsAgentInfoMapper;
import com.jsmsframework.user.mapper.JsmsOemDataConfigMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JsmsAccountFinanceServiceImpl implements JsmsAccountFinanceService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JsmsAccountFinanceServiceImpl.class);
	@Autowired
	private JsmsAgentInfoMapper jsmsAgentInfoMapper;
	@Autowired
	private JsmsOemDataConfigMapper jsmsOemDataConfigMapper;
	@Autowired
	private JsmsOemProductInfoMapper jsmsOemProductInfoMapper;
	@Autowired
	private JsmsAgentAccountMapper jsmsAgentAccountMapper;
	@Autowired
	private JsmsAgentBalanceBillMapper jsmsAgentBalanceBillMapper;
	@Autowired
	private JsmsOemAgentPoolMapper jsmsOemAgentPoolMapper;
	@Autowired
	private JsmsOemAgentOrderMapper jsmsOemAgentOrderMapper;
	@Autowired
	private JsmsOemClientPoolMapper jsmsOemClientPoolMapper;
	@Autowired
	private JsmsOemClientOrderMapper jsmsOemClientOrderMapper;

	/**
	 * 测试短信赠送
	 * 
	 * @param clientid
	 * @param paytype
	 * @param agentId
	 * @param name
	 */
	@Override
	public void giveShortMessage(String clientid, Integer paytype, Integer agentId, String name,
			List<Long> oemClientOrders,Integer test_product_id,Integer test_sms_number,Integer oemDataId) {
		logger.debug("测试短信赠送,方法为--->{},参数为---->{}", "GiveShortMessage", clientid);
		if (null != paytype && paytype != 0) {
			logger.debug("付费类型，0：预付费，1：后付费--->{}，不是预付费，不能赠送短信", paytype);
			return;
		}

		if (agentId == null && agentId < 0) {
			logger.debug("代理商id错误!");
			return;
		}

		int agent_type = jsmsAgentInfoMapper.getAgentTypeByAgentId(agentId);
		// 品牌代理/销售代理的客户,不赠送测试短信(只有OEM代理商赠送短信)
		if (agent_type != 5) {
			logger.debug("代理商类型为--->{}，不是oem代理商，不能赠送短信", agent_type);
			return;
		}
		// 不赠送短信产品
		//JsmsOemDataConfig oemDataConfig = jsmsOemDataConfigMapper.getOemDataConfig(agentId);
		//if (oemDataConfig == null) {
		//	logger.debug("代理商：{}------->没有对应的oem资料", agentId);
		//	return;
		//}
		//Integer test_product_id = oemDataConfig.getTestProductId();
		//Integer test_sms_number = oemDataConfig.getTestSmsNumber();
		//String oemDataId = oemDataConfig.getId().toString();
		if (test_product_id == null || test_sms_number == null
				|| "0".equals(test_sms_number.toString())) {
			logger.debug("oemid:{}资料----->为空、或者条数--->为空、或者-->条数", oemDataId);
			return;
		}
		// 测试产品包已经下架或者已经过期
		JsmsOemProductInfo oemProductInfo = jsmsOemProductInfoMapper
				.getOemProductInfoByProductId(test_product_id);
		int productStatus = (int) oemProductInfo.getStatus(); // 状态，0：待上架，1：已上架，2：已下架
		BigDecimal unit_price = oemProductInfo.getUnitPrice();
		Integer product_type = oemProductInfo.getProductType();// 产品类型,0:行业,1:营销,2:国际
		Integer operator_code = oemProductInfo.getOperatorCode();
		Integer area_code = oemProductInfo.getAreaCode();
		Date due_time = oemProductInfo.getDueTime();
		Integer product_id = oemProductInfo.getProductId();
		String product_code = oemProductInfo.getProductCode();
		String product_name = oemProductInfo.getProductName();

		if (productStatus != 1) {
			logger.debug("产品id:{}--------->为待上架或者已下架", test_product_id);
			return;
		}

		// 满足赠送短信的条件
		logger.debug("满足赠送短信的条件=============================================================");
		Date now = new Date();

		JsmsAgentAccount agentAccount = jsmsAgentAccountMapper.getAgentAccountByAgentId(agentId);
		String balance = agentAccount.getBalance().toString();
		BigDecimal oldBgBalance = new BigDecimal(balance);

		BigDecimal bgTestNum = new BigDecimal(test_sms_number);
		BigDecimal bgUnitPrice = new BigDecimal(unit_price.toString());
		BigDecimal bgAmount = bgTestNum.multiply(bgUnitPrice);

		BigDecimal newBgBalance = oldBgBalance.add(bgAmount);

		// 生成余额账单(代理商入账)
		JsmsAgentBalanceBill agentBalanceBill = new JsmsAgentBalanceBill();
		agentBalanceBill.setAgentId(agentId);
		agentBalanceBill.setPaymentType(5);// 业务类型，0：充值，1：扣减，2：佣金转余额，3：购买产品包，4：退款，5：赠送
		agentBalanceBill.setFinancialType("0");// 财务类型，0：入账，1：出账
		agentBalanceBill.setAmount(bgAmount);
		agentBalanceBill.setBalance(newBgBalance);
		agentBalanceBill.setCreateTime(now);
		agentBalanceBill.setOrderId(null); // 充值操作订单id为null
		agentBalanceBill.setAdminId(0L);
		agentBalanceBill.setClientId(clientid);
		agentBalanceBill.setRemark("赠送短信充值");

		int i = jsmsAgentBalanceBillMapper.insert(agentBalanceBill);
		if (i <= 0) {
			throw new JsmsOrderFinanceException("赠送短信，生成余额入账账单失败");
		}
		// 判断OEM代理商短信池(t_sms_oem_agent_pool)是否存在记录(获取agent_pool_id)
		Long agent_pool_id = null;
		JsmsOemAgentPool jsmsOemAgentPool = new JsmsOemAgentPool();
		jsmsOemAgentPool.setAgentId(agentId);
		jsmsOemAgentPool.setProductType(product_type);// 产品类型，0：行业，1：营销，2：国际
		jsmsOemAgentPool.setOperatorCode(operator_code);// 对应运营商,0:全网,1:移动,2:联通,3:电信,4:国际
		jsmsOemAgentPool.setAreaCode(area_code);// 适用地区,0:全国,1:国际,2:省网
		jsmsOemAgentPool.setDueTime(due_time);// 到期时间
		jsmsOemAgentPool.setUnitPrice(unit_price);
		jsmsOemAgentPool.setStatus(0);// 状态，0：正常，1：停用
		JsmsOemAgentPool agentPoolInfo = jsmsOemAgentPoolMapper.getByAgentPoolInfo(jsmsOemAgentPool);
		if (agentPoolInfo != null && agentPoolInfo.getAgentPoolId() != null) {
			agent_pool_id = agentPoolInfo.getAgentPoolId();
		} else {
			jsmsOemAgentPool.setAgentId(agentId);
			jsmsOemAgentPool.setProductType(product_type);
			jsmsOemAgentPool.setOperatorCode(operator_code);
			jsmsOemAgentPool.setAreaCode(area_code);
			jsmsOemAgentPool.setDueTime(due_time);
			jsmsOemAgentPool.setStatus(0);// 状态,0:正常,1:停用
			jsmsOemAgentPool.setRemainNumber(0);
			jsmsOemAgentPool.setRemainAmount(BigDecimal.ZERO);
			jsmsOemAgentPool.setUnitPrice(unit_price);
			jsmsOemAgentPool.setUpdateTime(now);
			jsmsOemAgentPool.setRemark(null);
			int j = jsmsOemAgentPoolMapper.insert(jsmsOemAgentPool);
			if (j <= 0) {
				throw new JsmsOrderFinanceException("生成代理商短信池记录失败！");
			}
			agent_pool_id = jsmsOemAgentPool.getAgentPoolId();
		}

		// 生成代理商订单(购买记录)
		JsmsOemAgentOrder jsmsOemAgentOrder = new JsmsOemAgentOrder();
		Long orderId = oemClientOrders.get(0);
		jsmsOemAgentOrder.setOrderId(orderId);
		jsmsOemAgentOrder.setOrderNo(orderId);
		jsmsOemAgentOrder.setOrderType(0);// 订单类型，0：OEM代理商购买，1：OEM代理商分发，2：OEM代理商回退
		jsmsOemAgentOrder.setProductId(product_id);
		jsmsOemAgentOrder.setProductCode(product_code);
		jsmsOemAgentOrder.setProductType(product_type);
		jsmsOemAgentOrder.setOperatorCode(operator_code);
		jsmsOemAgentOrder.setAreaCode(area_code);
		jsmsOemAgentOrder.setProductName(product_name);
		jsmsOemAgentOrder.setUnitPrice(unit_price);
		jsmsOemAgentOrder.setOrderNumber(test_sms_number);// 赠送的短信条数
		jsmsOemAgentOrder.setOrderAmount(bgAmount);
		jsmsOemAgentOrder.setProductPrice(BigDecimal.valueOf(0));
		jsmsOemAgentOrder.setAgentId(agentId);
		jsmsOemAgentOrder.setClientId(clientid);
		jsmsOemAgentOrder.setName("云之讯");
		jsmsOemAgentOrder.setAgentPoolId(agent_pool_id);
		jsmsOemAgentOrder.setDueTime(due_time);
		jsmsOemAgentOrder.setCreateTime(now);
		jsmsOemAgentOrder.setRemark(null);

		int k = jsmsOemAgentOrderMapper.insert(jsmsOemAgentOrder);
		if (k <= 0) {
			throw new JsmsOrderFinanceException("生成代理商订单（购买记录）失败！");
		}
		// 生成余额账单(代理商出账)
		agentBalanceBill.setAgentId(agentId);
		agentBalanceBill.setPaymentType(3);// 业务类型，0：充值，1：扣减，2：佣金转余额，3：购买产品包，4：退款，5：赠送
		agentBalanceBill.setFinancialType("1");// 财务类型，0：入账，1：出账
		agentBalanceBill.setAmount(bgAmount);
		agentBalanceBill.setBalance(oldBgBalance);
		agentBalanceBill.setCreateTime(now);
		agentBalanceBill.setOrderId(orderId);
		agentBalanceBill.setAdminId(0L);
		agentBalanceBill.setClientId(clientid);
		agentBalanceBill.setRemark("赠送短信充值");

		int m = jsmsAgentBalanceBillMapper.insert(agentBalanceBill);
		if (m <= 0) {
			throw new JsmsOrderFinanceException("赠送短信，生成余额出账账单失败");
		}

		// ======================================给客户充值===========================================

		// 生成代理商订单(分发记录)
		Long orderId2 = oemClientOrders.get(1);
		jsmsOemAgentOrder.setOrderId(orderId2);
		jsmsOemAgentOrder.setOrderNo(orderId2);
		jsmsOemAgentOrder.setOrderType(1);// 订单类型，0：OEM代理商购买，1：OEM代理商分发，2：OEM代理商回退
		jsmsOemAgentOrder.setProductId(product_id);
		jsmsOemAgentOrder.setProductCode(product_code);
		jsmsOemAgentOrder.setProductType(product_type);
		jsmsOemAgentOrder.setOperatorCode(operator_code);
		jsmsOemAgentOrder.setAreaCode(area_code);
		jsmsOemAgentOrder.setProductName(product_name);
		jsmsOemAgentOrder.setUnitPrice(unit_price);
		jsmsOemAgentOrder.setOrderNumber(test_sms_number);// 赠送短信条数
		jsmsOemAgentOrder.setOrderAmount(bgAmount);
		jsmsOemAgentOrder.setProductPrice(BigDecimal.valueOf(0));
		jsmsOemAgentOrder.setAgentId(agentId);
		jsmsOemAgentOrder.setClientId(clientid);
		jsmsOemAgentOrder.setName(name);
		jsmsOemAgentOrder.setAgentPoolId(agent_pool_id);
		jsmsOemAgentOrder.setDueTime(due_time);
		jsmsOemAgentOrder.setCreateTime(now);
		jsmsOemAgentOrder.setRemark(null);

		int n = jsmsOemAgentOrderMapper.insert(jsmsOemAgentOrder);
		if (n <= 0) {
			throw new JsmsOrderFinanceException("生成代理商订单（分发记录）失败！");
		}

		// 判断oem客户短信池是否存在记录(获取client_pool_id)

		// 判断OEM代理商短信池(t_sms_oem_agent_pool)是否存在记录(获取agent_pool_id)
		Long client_pool_id = null;
		JsmsOemClientPool jsmsOemClientPool = new JsmsOemClientPool();
		jsmsOemClientPool.setClientId(clientid);
		jsmsOemClientPool.setProductType(product_type);// 产品类型，0：行业，1：营销，2：国际
		jsmsOemClientPool.setOperatorCode(operator_code);// 对应运营商，0：全网，1：移动，2：联通，3：电信，4：国际
		jsmsOemClientPool.setAreaCode(area_code);// 适用区域，0：全国，1：国际
		jsmsOemClientPool.setDueTime(due_time);// 到期时间
		jsmsOemClientPool.setUnitPrice(unit_price);
		jsmsOemClientPool.setStatus(0);// 状态，0：正常，1：停用

		JsmsOemClientPool clientPool = jsmsOemClientPoolMapper.getByClientPoolInfo(jsmsOemClientPool);
		if (clientPool != null && clientPool.getClientPoolId() != null) {
			client_pool_id = clientPool.getClientPoolId();

			Map<String, Object> updateClientPoolMap = new HashMap<>();
			updateClientPoolMap.put("client_pool_id", client_pool_id);
			updateClientPoolMap.put("test_num", test_sms_number);

			int o = jsmsOemClientPoolMapper.updateClientPoolByCondition(test_sms_number, client_pool_id);
			if (o <= 0) {
				throw new JsmsOrderFinanceException("更新客户短信池的测试条数失败！");
			}

		} else {
			jsmsOemClientPool.setClientId(clientid);
			jsmsOemClientPool.setProductType(product_type);
			jsmsOemClientPool.setOperatorCode(operator_code);
			jsmsOemClientPool.setAreaCode(area_code);
			jsmsOemClientPool.setDueTime(due_time);
			jsmsOemClientPool.setStatus(0);// 状态,0正常,1停用
			jsmsOemClientPool.setTotalNumber(test_sms_number);
			jsmsOemClientPool.setUnitPrice(unit_price);
			jsmsOemClientPool.setTotalAmount(BigDecimal.ZERO);
			jsmsOemClientPool.setRemainNumber(test_sms_number);
			jsmsOemClientPool.setRemainAmount(BigDecimal.ZERO);
			jsmsOemClientPool.setRemainTestNumber(test_sms_number);
			jsmsOemClientPool.setUpdateTime(now);
			jsmsOemClientPool.setRemark(null);

			int p = jsmsOemClientPoolMapper.insert(jsmsOemClientPool);
			if (p <= 0) {
				throw new JsmsOrderFinanceException("生成客户短信池失败！");
			}

			client_pool_id = jsmsOemClientPool.getClientPoolId();
		}

		// 给客户订单增加分发记录(生成oem客户订单)
		Long oemClientOrderId = oemClientOrders.get(2);
		JsmsOemClientOrder jsmsOemClientOrder = new JsmsOemClientOrder();
		jsmsOemClientOrder.setOrderId(oemClientOrderId);
		jsmsOemClientOrder.setOrderNo(oemClientOrderId);
		jsmsOemClientOrder.setProductType(product_type);// 产品类型，0：行业，1：营销，2：国际
		jsmsOemClientOrder.setOperatorCode(operator_code);// 对应运营商，0：全网，1：移动，2：联通，3：电信，4：国际 所有订单类型时都有值
		jsmsOemClientOrder.setAreaCode(area_code);// 适用区域，0：全国，1：国际 所有订单类型时都有值
		jsmsOemClientOrder.setOrderType(1);// 订单类型，1：OEM代理商分发，2：OEM代理商回退
		jsmsOemClientOrder.setOrderNumber(test_sms_number);// 赠送的短信条数
		jsmsOemClientOrder.setUnitPrice(unit_price);
		jsmsOemClientOrder.setOrderPrice(bgAmount);
		jsmsOemClientOrder.setClientId(clientid);
		jsmsOemClientOrder.setAgentId(agentId);
		jsmsOemClientOrder.setClientPoolId(client_pool_id);
		jsmsOemClientOrder.setDueTime(due_time);
		jsmsOemClientOrder.setCreateTime(now);
		jsmsOemClientOrder.setRemark(null);

		int q = jsmsOemClientOrderMapper.insert(jsmsOemClientOrder);
		if (q <= 0) {
			throw new JsmsOrderFinanceException("生成oem客户订单失败！");
		}
		logger.debug("赠送短信最终成功==========================================================");
	}
}
