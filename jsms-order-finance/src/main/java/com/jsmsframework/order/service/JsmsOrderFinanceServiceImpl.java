package com.jsmsframework.order.service;

import com.alibaba.fastjson.JSON;
import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.*;
import com.jsmsframework.common.enums.LogConstant.LogType;
import com.jsmsframework.common.service.JsmsLogService;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import com.jsmsframework.finance.entity.JsmsSaleCreditAccount;
import com.jsmsframework.finance.entity.JsmsSaleCreditBill;
import com.jsmsframework.finance.mapper.JsmsAgentAccountMapper;
import com.jsmsframework.finance.mapper.JsmsAgentBalanceBillMapper;
import com.jsmsframework.finance.mapper.JsmsSaleCreditAccountMapper;
import com.jsmsframework.finance.mapper.JsmsSaleCreditBillMapper;
import com.jsmsframework.order.entity.JsmsClientOrder;
import com.jsmsframework.order.entity.JsmsOemAgentOrder;
import com.jsmsframework.order.entity.JsmsOemAgentPool;
import com.jsmsframework.order.entity.po.JsmsClientOrderPo;
import com.jsmsframework.order.entity.po.JsmsOemAgentPoolPo;
import com.jsmsframework.order.exception.JsmsOrderFinanceException;
import com.jsmsframework.order.mapper.JsmsClientOrderMapper;
import com.jsmsframework.order.mapper.JsmsOemAgentOrderMapper;
import com.jsmsframework.order.mapper.JsmsOemAgentPoolMapper;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import com.jsmsframework.user.mapper.JsmsAgentInfoMapper;
import com.ucpaas.sms.common.util.DateUtils;
import com.ucpaas.sms.common.util.FmtUtils;
import com.ucpaas.sms.common.util.MathUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class JsmsOrderFinanceServiceImpl implements JsmsOrderFinanceService {

	private static final Logger logger = LoggerFactory.getLogger(JsmsOrderFinanceServiceImpl.class);

	@Autowired
	private JsmsAgentInfoMapper jsmsAgentInfoMapper;


	@Autowired
	private JsmsClientOrderMapper clientOrderMapper;

	@Autowired
	private JsmsAgentAccountMapper agentAccountMapper;

	@Autowired
	private JsmsAgentBalanceBillMapper agentBalanceBillMapper;

	@Autowired
	private JsmsOemAgentPoolMapper oemAgentPoolMapper;

	@Autowired
	private JsmsLogService logService;

	@Autowired
	private JsmsOemAgentOrderMapper oemAgentOrderMapper;

	@Autowired
	private JsmsSaleCreditAccountMapper jsmsSaleCreditAccountMapper;

	@Autowired
	private  JsmsSaleCreditBillMapper jsmsSaleCreditBillMapper;

	private boolean checkOrderReturnQuantity(List<JsmsClientOrderPo> clientOrderPos) {
		boolean result = true;
		for (JsmsClientOrderPo order : clientOrderPos) {
			if (order.getReturnQuantity() == null || order.getReturnQuantity() <= 0 || order.getRemainQuantity() == null
					|| order.getReturnQuantity().compareTo(order.getRemainQuantity().intValue()) > 0) {
				result = false;
				break;
			}
		}
		return result;
	}

	private boolean checkAgentPoolReturnQuantity(List<JsmsOemAgentPoolPo> oemAgentPoolPos) {
		boolean result = true;
		for (JsmsOemAgentPoolPo pool : oemAgentPoolPos) {
			if (pool.getReturnQuantity() == null || pool.getReturnQuantity() <= 0 || pool.getRemainNumber() == null
					|| pool.getReturnQuantity().compareTo(pool.getRemainNumber()) > 0) {
				result = false;
				break;
			}
		}
		return result;
	}

	private String getRemark(Integer productType, Integer operatorCode, Integer areaCode, BigDecimal unitPrice,
			Integer count) {
		StringBuilder bf = new StringBuilder();
		bf.append(ProductType.getDescByValue(productType));
		bf.append("/");
		bf.append(OperatorType.getDescByValue(operatorCode));
		bf.append("/");
		bf.append(AreaCodeEnum.getDescByValue(areaCode));
		bf.append("/");
		bf.append(FmtUtils.roundDownAsString(unitPrice, 4));
		bf.append("元/");
		bf.append(count);
		bf.append("条");
		return bf.toString();
	}

	private String getRemark(JsmsOemAgentPoolPo agentPoolPo, Integer count) {
		return getRemark(agentPoolPo.getProductType(), agentPoolPo.getOperatorCode(), agentPoolPo.getAreaCode(),
				agentPoolPo.getUnitPrice(), count);
	}

	private String getRemark(JsmsClientOrder clientOrder, Integer count) {
		return getRemark(clientOrder.getProductType(), clientOrder.getOperatorCode(), clientOrder.getAreaCode(),
				clientOrder.getUnitPrice(), count);
	}

	private static class Temp {
		Long agentPoolId;
		Integer quantity;
	}

	@Transactional("message")
	@Override
	public R agentOrderReturnQuantity(List<JsmsClientOrderPo> clientOrderPos, Long adminId, String url, String ip) {
		if (!checkOrderReturnQuantity(clientOrderPos)) {
			return R.error("回退数量必须大于0，并且小于等于剩余数量");
		}

		logger.debug("==开始回退订单 {}", DateUtils.formatDateTime(Calendar.getInstance().getTime()));
		logger.debug("==回退订单 操作人 {} ip {} 数据 {}", adminId, ip, JSON.toJSONString(clientOrderPos));

		JsmsClientOrder clientOrder;
		JsmsAgentAccount agentAccount;
		JsmsAgentBalanceBill agentBalanceBill;
		int count;

		// 循环处理
		for (JsmsClientOrderPo order : clientOrderPos) {
			// 单价为空，抛出异常
			if (order.getUnitPrice() == null) {
				logger.debug("==回退订单 订单 {} 的单价为空", order.getOrderId());
				throw new JsmsOrderFinanceException("订单`" + order.getOrderId() + "`的单价为空");
			}

			// 回退数量
			Integer quantity = order.getReturnQuantity();

			// 回退金额 = 单价 * 数量
			Double b = MathUtils.mul(new Double(quantity), order.getUnitPrice().doubleValue());
			BigDecimal money = FmtUtils.roundDown(new BigDecimal(b.toString()), 4);

			// 查询代理商余额信息
			agentAccount = agentAccountMapper.getByAgentId(order.getAgentId());

			// 代理商增加后的余额
			b = MathUtils.add(agentAccount.getBalance().doubleValue(), money.doubleValue());
			BigDecimal balance = FmtUtils.roundDown(new BigDecimal(b.toString()), 4);

			// 第一步：回退订单数量，订单剩余数量减去当前回退数量
			clientOrder = new JsmsClientOrder();
			BeanUtil.copyProperties(order, clientOrder);
			count = clientOrderMapper.returnQuantity(clientOrder, quantity);
			if (count <= 0) {
				logger.debug("==回退订单 订单 {} 回退数量失败", order.getOrderId());
				throw new JsmsOrderFinanceException("剩余条数有变动，请重新进行回退操作！");
			}

			// 第二步：代理商余额增加本次回退的金额
//			count = agentAccountMapper.addBalance(agentAccount, balance);
//			if (count <= 0) {
//				logger.debug("==回退订单 订单 {} 代理商余额增加失败", order.getOrderId());
//				throw new JsmsOrderFinanceException("订单回退条数失败，原因：代理商余额充值失败！");
//			}
//
//			// 第三步：增加余额账单 1. 财务类型为入账、付费类型为 7：回退条数（新增）
//			agentBalanceBill = new JsmsAgentBalanceBill();
//			agentBalanceBill.setAgentId(clientOrder.getAgentId());
//			agentBalanceBill.setPaymentType(PaymentType.回退条数.getValue());
//			agentBalanceBill.setFinancialType("0"); // 财务类型，0：入账，1：出账
//			agentBalanceBill.setAmount(money);
//			agentBalanceBill.setBalance(balance);
//			agentBalanceBill.setCreateTime(Calendar.getInstance().getTime());
//			agentBalanceBill.setOrderId(clientOrder.getOrderId());
//			agentBalanceBill.setAdminId(adminId);
//			agentBalanceBill.setClientId(clientOrder.getClientId());
//			agentBalanceBill.setRemark(getRemark(clientOrder, quantity));
//
//			count = this.agentBalanceBillMapper.insert(agentBalanceBill);
//			if (count <= 0) {
//				logger.debug("==回退订单 订单 {} 增加余额账单失败", order.getOrderId());
//				throw new JsmsOrderFinanceException("订单回退条数失败，原因：生成余额账单失败！");
//			}

			Map<String,Object> billresult=this.agentUpdateByBalanceOP(clientOrder,new JsmsOemAgentPoolPo(),null,quantity,agentAccount,money,adminId,false);
			if(Objects.equals("fail",billresult.get("fail"))){
				logger.debug("==回退订单 订单 {} 增加余额账单失败", order.getOrderId());
				throw new JsmsOrderFinanceException("订单回退条数失败，原因："+billresult.get("msg"));
			}

			// 写日志
			logService.addToOperation(LogType.update, LogEnum.财务管理.getValue(), url, ip, adminId.toString(),
					"财务管理-代理商财务-回退条数："+ JSON.toJSONString(clientOrder), " 回退条数：" + quantity, " 回退金额：" + money);

		}

		logger.debug("==结束回退订单 {}", DateUtils.formatDateTime(Calendar.getInstance().getTime()));
		return R.ok("回退条数成功");
	}

	@Transactional("message")
	@Override
	public R agentPoolReturnQuantity(List<JsmsOemAgentPoolPo> oemAgentPoolPos, Long adminId, String url, String ip,
			List<Long> orderIdList) {
		if (!checkAgentPoolReturnQuantity(oemAgentPoolPos)) {
			return R.error("回退数量必须大于0，并且小于等于剩余数量");
		}

		logger.debug("==开始回退代理商短信池 {}", DateUtils.formatDateTime(Calendar.getInstance().getTime()));
		logger.debug("==回退代理商短信池 操作人 {} ip {} 数据 {}", adminId, ip, JSON.toJSONString(oemAgentPoolPos));

		int count;
		JsmsOemAgentPool oemAgentPool;
		JsmsAgentAccount agentAccount;
		JsmsAgentBalanceBill agentBalanceBill;
		JsmsOemAgentOrder oemAgentOrder;

		// 此批次为一个OrderNO
		Long orderNo = orderIdList.get(0);

		// 循环处理
		for (JsmsOemAgentPoolPo poolPo : oemAgentPoolPos) {
			if (poolPo.getMultiRecord() == null || poolPo.getMultiRecord().size() == 0) {
				logger.debug("==回退代理商短信池 代理商 {}  属性MultiRecord不能为空", poolPo.getAgentId());
				throw new JsmsOrderFinanceException("代理商`" + poolPo.getAgentId() + "`短信池参数错误！");
			}

			// 总数量
			Integer total = poolPo.getReturnQuantity();

			// 将MAP转换为List
			List<Temp> list = new ArrayList<>();
			Map<Long, String> multiRecord = poolPo.getMultiRecord();
			for (Long key : multiRecord.keySet()) {
				Temp temp = new Temp();
				temp.agentPoolId = key;
				temp.quantity = Integer.parseInt(multiRecord.get(key));
				list.add(temp);
			}

			// 排序，从小到达排序
			Collections.sort(list, new Comparator<Temp>() {
				// 升序排序
				@Override
				public int compare(Temp o1, Temp o2) {
					return o1.quantity.compareTo(o2.quantity);
				}
			});

			int dealCount = total;
			for (Temp temp : list) {
				// 本次处理减去本次
				int currCount = dealCount - temp.quantity > 0 ? temp.quantity : dealCount;

				// 单价为空，抛出异常
				if (poolPo.getUnitPrice() == null) {
					logger.debug("==回退代理商短信池 代理商 {} 短信池 {} 的单价为空", poolPo.getAgentId(), temp.agentPoolId);
					throw new JsmsOrderFinanceException(
							"代理商`" + poolPo.getAgentId() + "`短信池`" + temp.agentPoolId + "`的单价为空");
				}

				// 回退金额 = 单价 * 数量
				Double b = MathUtils.mul(currCount, poolPo.getUnitPrice().doubleValue());
				BigDecimal money = FmtUtils.roundDown(new BigDecimal(b.toString()), 4);

				// 查询代理商余额信息
				agentAccount = agentAccountMapper.getByAgentId(poolPo.getAgentId());

				// 代理商增加后的余额
				b = MathUtils.add(agentAccount.getBalance().doubleValue(), money.doubleValue());
				BigDecimal balance = FmtUtils.roundDown(new BigDecimal(b.toString()), 4);

				// 第一步：回退代理商池数量 t_sms_oem_agent_pool
				oemAgentPool = new JsmsOemAgentPool();
				BeanUtil.copyProperties(poolPo, oemAgentPool);

				// 设置更新条件
				oemAgentPool.setAgentPoolId(temp.agentPoolId);
				oemAgentPool.setRemainNumber(temp.quantity);
				count = oemAgentPoolMapper.returnQuantity(oemAgentPool, currCount);
				if (count <= 0) {
					logger.debug("==回退代理商短信池 代理商 {} 短信池 {} 回退池数量失败", poolPo.getAgentId(), temp.agentPoolId);
					throw new JsmsOrderFinanceException("剩余条数有变动，请重新进行回退操作！");
				}

				/*// 第二步：代理商余额增加money
				count = agentAccountMapper.addBalance(agentAccount, balance);
				if (count <= 0) {
					logger.debug("==回退代理商短信池 代理商 {} 短信池 {} 代理商余额增加失败", poolPo.getAgentId(), temp.agentPoolId);
					throw new JsmsOrderFinanceException("回退条数失败，原因：代理商余额充值失败！");
				}*/



				// 第三步：增加余额账单 1. 财务类型为入账、付费类型为 7：回退条数（新增）
//				agentBalanceBill = new JsmsAgentBalanceBill();
//				agentBalanceBill.setAgentId(poolPo.getAgentId());
//				agentBalanceBill.setPaymentType(PaymentType.回退条数.getValue());
//				agentBalanceBill.setFinancialType("0"); // 财务类型，0：入账，1：出账
//				agentBalanceBill.setAmount(money);
//				agentBalanceBill.setBalance(balance);
//				agentBalanceBill.setCreateTime(Calendar.getInstance().getTime());
//				agentBalanceBill.setOrderId(orderNo);
//				agentBalanceBill.setAdminId(adminId);
//				agentBalanceBill.setClientId(null);
//				agentBalanceBill.setRemark(getRemark(poolPo, currCount));
//				count = this.agentBalanceBillMapper.insert(agentBalanceBill);
//				if (count < 1) {
//					logger.debug("==回退代理商短信池 代理商 {} 短信池 {} 增加余额账单失败", poolPo.getAgentId(), temp.agentPoolId);
//					throw new JsmsOrderFinanceException("回退条数失败，原因：生成余额账单失败！");
//				}
				logger.debug("开始回退代理商短信池 代理商 {} 短信池 {} 增加余额操作", JsonUtil.toJson(agentAccount), temp.agentPoolId);
				Map<String,Object> billresult=this.agentUpdateByBalanceOP(new JsmsClientOrder(),poolPo,orderNo,currCount,agentAccount,money,adminId,true);
				if(Objects.equals(SysConstant.FAIL,billresult.get("result"))){
					logger.debug("==回退代理商短信池 代理商 {} 短信池 {} 增加余额操作失败", poolPo.getAgentId(), temp.agentPoolId);
					throw new JsmsOrderFinanceException("回退条数失败，原因："+billresult.get("msg"));
				}

				// 第四步：增加代理商订单
				oemAgentOrder = new JsmsOemAgentOrder();

				// 取第一个
				oemAgentOrder.setOrderId(orderIdList.get(0));
				oemAgentOrder.setOrderNo(orderNo);
				oemAgentOrder.setOrderType(3); // 0：OEM代理商购买，1：OEM代理商分发，2：OEM代理商回退，3：OEM代理商条数扣减
				oemAgentOrder.setProductId(null);
				oemAgentOrder.setProductCode(null);
				oemAgentOrder.setProductType(poolPo.getProductType());
				oemAgentOrder.setOperatorCode(poolPo.getOperatorCode());
				oemAgentOrder.setAreaCode(poolPo.getAreaCode());
				oemAgentOrder.setProductName(null);
				oemAgentOrder.setUnitPrice(poolPo.getUnitPrice());
				oemAgentOrder.setOrderNumber(currCount);
				oemAgentOrder.setOrderAmount(money);
				oemAgentOrder.setProductPrice(null);
				oemAgentOrder.setAgentId(poolPo.getAgentId());
				oemAgentOrder.setClientId(null);
				oemAgentOrder.setName("云之讯");
				oemAgentOrder.setDueTime(poolPo.getDueTime());
				oemAgentOrder.setAgentPoolId(temp.agentPoolId);// 添加订单的短信池ID
				oemAgentOrder.setCreateTime(Calendar.getInstance().getTime());
				oemAgentOrder.setRemark(getRemark(poolPo, currCount));
				count = this.oemAgentOrderMapper.insert(oemAgentOrder);
				if (count < 1) {
					logger.debug("==回退代理商短信池 代理商 {} 短信池 {} 增加代理商订单失败", poolPo.getAgentId(), temp.agentPoolId);
					throw new JsmsOrderFinanceException("回退条数失败，原因：创建订单失败！");
				}

				// 移除第一个
				orderIdList.remove(0);

				// 第五步：写日志
				logService.addToOperation(LogType.update, LogEnum.财务管理.getValue(), url, ip, adminId.toString(),
						"财务管理-代理商财务-回退条数："+ JSON.toJSONString(poolPo), " 回退条数" + currCount, "回退金额" + money);

				// 总处理数量减去本次处理数量
				dealCount = dealCount - currCount;

				// 若已处理完毕，跳出
				if (dealCount <= 0) {
					break;
				}
			}
		}

		logger.debug("==结束回退代理商短信池 {}", DateUtils.formatDateTime(Calendar.getInstance().getTime()));
		return R.ok("回退条数成功");
	}


	/**授信改造
	 *代理商相关变化
	 * @param
	 * @param
	 * @return
	 */

	public Map<String,Object> agentUpdateByBalanceOP(JsmsClientOrder clientOrder,JsmsOemAgentPoolPo poolPo,Long orderNo, Integer quantity, JsmsAgentAccount agentAcc,BigDecimal money,Long adminId,Boolean isOEM){
		Map<String,Object> data=new HashedMap();
		JsmsAgentAccount agentAccount=new JsmsAgentAccount();
		agentAccount.setAgentId(agentAcc.getAgentId());
		JsmsAgentBalanceBill bill=new JsmsAgentBalanceBill();
		bill.setAgentId(agentAcc.getAgentId());
		bill.setAdminId(adminId);
		BigDecimal backPay=BigDecimal.ZERO;


			//1.判断是是否欠款
			if(agentAcc.getBalance().compareTo(BigDecimal.ZERO)==-1){
				//欠款 判断回退金额是否大于欠款金额
				if(agentAcc.getNoBackPayment().compareTo(money)==1){
					agentAccount.setNoBackPayment(BigDecimal.ZERO.subtract(money));
					agentAccount.setHistoryPayment(money);
					backPay=money;
				}else {
					agentAccount.setNoBackPayment(BigDecimal.ZERO.subtract(agentAcc.getNoBackPayment()));
					agentAccount.setHistoryPayment(agentAcc.getNoBackPayment());
					backPay=agentAcc.getNoBackPayment();
				}

				agentAccount.setAccumulatedConsume(BigDecimal.ZERO.subtract(money));

				bill.setNoBackPayment(agentAccount.getNoBackPayment().add(agentAcc.getNoBackPayment()));
				bill.setHistoryPayment(agentAccount.getHistoryPayment().add(agentAcc.getHistoryPayment()));
			}else {
				agentAccount.setAccumulatedConsume(BigDecimal.ZERO.subtract(money));
				bill.setHistoryPayment(agentAcc.getHistoryPayment());
				bill.setNoBackPayment(agentAcc.getNoBackPayment());
			}

			agentAccount.setBalance(money);

			bill.setPaymentType(PaymentType.回退条数.getValue());
			bill.setFinancialType(FinancialType.入账.getValue());

			bill.setAmount(money);
			bill.setBalance(agentAccount.getBalance().add(agentAcc.getBalance()));

			bill.setCreditBalance(agentAcc.getCreditBalance());
			bill.setCurrentCredit(agentAcc.getCurrentCredit());
		if(isOEM){
			bill.setRemark(getRemark(poolPo, quantity));
			bill.setOrderId(orderNo);
			bill.setClientId(null);
		}else {
			bill.setRemark(getRemark(clientOrder, quantity));
			bill.setOrderId(orderNo);
			bill.setClientId(clientOrder.getClientId());
		}


		bill.setCreateTime(new Date());


		logger.debug("开始操作代理商生成流水及账号信息更新,bill={},更新金额account={}",JsonUtil.toJson(bill),JsonUtil.toJson(agentAccount));
		int binsert=agentBalanceBillMapper.insert(bill);
		if(binsert>0){
			int accUpdate=agentAccountMapper.updateAccoutForRealTime(agentAccount);
			if(accUpdate<0){
				logger.error("更新代理商{}对应账户信息失败！,更新信息{}",bill.getAgentId(), JsonUtil.toJson(agentAccount));
				data.put("result", SysConstant.FAIL);
				data.put("msg", "更新代理商账号金额失败！");
				throw  new JsmsOrderFinanceException("回退条数失败 更新代理商账号金额失败,原因:更新账户失败");

			}
			logger.debug("更新代理商{}对应账户信息成功！,更新信息{}",bill.getAgentId(), JsonUtil.toJson(agentAccount));
		}else {
			data.put("result", SysConstant.FAIL);
			data.put("msg", "生成代理商账单失败！");
			logger.error("生成代理商{}账单失败,更新信息{}！",bill.getAgentId(), JsonUtil.toJson(bill));
			throw  new JsmsOrderFinanceException("回退条数失败 新增代理商账单失败,原因:生成代理商账单失败");
		}
		logger.debug("完成操作代理商生成流水及账号信息更新,bill={},更新金额account={}",JsonUtil.toJson(bill),JsonUtil.toJson(agentAccount));

		if(agentAcc.getBalance().compareTo(BigDecimal.ZERO)==-1){
			logger.debug("欠款回款操作开始，回款金额={},代理商信息={}",backPay,JsonUtil.toJson(agentAcc));
			data=this.saleUpdateByBalanceOP(backPay,bill.getRemark(),bill.getAdminId(),agentAcc);
		}
		if(Objects.equals(data.get("result"),SysConstant.FAIL)){
			throw  new JsmsOrderFinanceException("回退条数失败 更新销售信息失败,原因:"+data.get("msg"));
		}
		data.put("result", SysConstant.SUCCESS);
		data.put("msg", "更新代理商余额成功！");
		return data;
	}

	/**
	 *
	 * @param
	 * @param
	 * @return
	 */

	public Map<String,Object> saleUpdateByBalanceOP(BigDecimal backPay,String remark,Long adminId,JsmsAgentAccount agentAcc){
		Map<String,Object> data=new HashedMap();
		JsmsSaleCreditAccount saleAccount=new JsmsSaleCreditAccount();
		JsmsAgentInfo agent=jsmsAgentInfoMapper.getByAgentId(agentAcc.getAgentId());
		if(agent==null){
			data.put("result", SysConstant.FAIL);
			data.put("msg", "代理商信息不存在！");
			logger.error("代理商{}信息不存在",agentAcc.getAgentId());
			throw  new JsmsOrderFinanceException("回退条数失败 ,原因:代理商信息不存在");
		}
		if(agent.getBelongSale()==null){
			data.put("result", SysConstant.FAIL);
			data.put("msg", "代理商不存在归属销售！");
			logger.error("代理商{}不存在归属销售！",agentAcc.getAgentId());
			throw  new JsmsOrderFinanceException("回退条数失败 ,原因:代理商信息归属销售不存在");
		}
		JsmsSaleCreditAccount saleAcc=jsmsSaleCreditAccountMapper.getBySaleId(agent.getBelongSale());
		if(saleAcc==null){
			data.put("result", SysConstant.FAIL);
			data.put("msg", "该代理商归属销售不存在！");
			logger.error("代理商{}归属销售{}不存在",agentAcc.getAgentId(),agent.getBelongSale());
			throw  new JsmsOrderFinanceException("回退条数失败 ,原因:代理商信息归属销售不存在");
		}

		saleAccount.setSaleId(saleAcc.getSaleId());
		saleAccount.setAgentHistoryPayment(backPay);
		saleAccount.setNoBackPayment(BigDecimal.ZERO.subtract(backPay));


		JsmsSaleCreditBill bill=new JsmsSaleCreditBill();
		bill.setSaleId(saleAcc.getSaleId());
		bill.setBusinessType(BusinessType.客户回款.getValue());
		bill.setFinancialType(FinancialType.入账.getValue());
		bill.setAdminId(Long.valueOf(adminId));
		bill.setAmount(backPay);
		bill.setObjectId(agent.getAgentId().longValue());
		bill.setObjectType(ObjectType.代理商.getValue());

		bill.setRemark(remark);
		bill.setCurrentCredit(saleAcc.getCurrentCredit());
		bill.setNoBackPayment(saleAccount.getNoBackPayment().add(saleAcc.getNoBackPayment()));
		bill.setAgentHistoryPayment(saleAccount.getAgentHistoryPayment().add(saleAcc.getAgentHistoryPayment()));
		bill.setFinancialHistoryCredit(saleAcc.getFinancialHistoryCredit());
		bill.setSaleHistoryCredit(saleAcc.getSaleHistoryCredit());
		bill.setCreateTime(new Date());


		int binsert=jsmsSaleCreditBillMapper.insert(bill);
		if(binsert>0){
			logger.debug("生成代理商{}回款销售账单成功,更新信息{}！",bill.getObjectId(), JsonUtil.toJson(bill));
			int accUpdate=jsmsSaleCreditAccountMapper.updateAccountForRealTime(saleAccount);
			if(accUpdate<0){
				data.put("result", SysConstant.FAIL);
				data.put("msg", "更新销售授信账号失败,请联系管理员！");
				logger.error("更新代理商{}对应销售回款信息失败！,更新信息{}",bill.getObjectId(), JsonUtil.toJson(saleAccount));
				throw  new JsmsOrderFinanceException("回退条数失败 ,原因:更新代理商"+bill.getObjectId()+"对应销售回款信息失败");
			}
		}else {
			data.put("result", SysConstant.FAIL);
			data.put("msg", "生成销售授信账单失败,请联系管理员！");
			logger.error("生成代理商{}回款销售账单失败,更新信息{}！",bill.getObjectId(), JsonUtil.toJson(bill));
			throw  new JsmsOrderFinanceException("回退条数失败 ,原因:生成代理商"+bill.getObjectId()+"回款销售账单失败");

		}
		data.put("result", SysConstant.SUCCESS);
		data.put("msg", "更新销售回款成功！");
		return data;
	}

}
