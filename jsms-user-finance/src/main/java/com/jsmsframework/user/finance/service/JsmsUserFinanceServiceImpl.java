package com.jsmsframework.user.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.entity.JsmsDict;
import com.jsmsframework.common.entity.JsmsMailprop;
import com.jsmsframework.common.enums.AgentType;
import com.jsmsframework.common.enums.Code;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.common.enums.invoice.*;
import com.jsmsframework.common.service.JsmsDictService;
import com.jsmsframework.common.service.JsmsEmailService;
import com.jsmsframework.common.service.JsmsMailpropService;
import com.jsmsframework.common.util.AgentIdUtil;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.common.util.CheckEmail;
import com.jsmsframework.common.util.RegexUtils;
import com.jsmsframework.finance.dto.JsmsAgentInvoiceListDTO;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceConfig;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceList;
import com.jsmsframework.finance.mapper.JsmsAgentInvoiceConfigMapper;
import com.jsmsframework.finance.mapper.JsmsAgentInvoiceListMapper;
import com.jsmsframework.finance.service.JsmsAgentAccountService;
import com.jsmsframework.finance.service.JsmsAgentInvoiceConfigService;
import com.jsmsframework.finance.service.JsmsAgentInvoiceListService;
import com.jsmsframework.product.entity.JsmsOemProductInfo;
import com.jsmsframework.product.service.JsmsOemProductInfoService;
import com.jsmsframework.user.entity.*;
import com.jsmsframework.user.finance.exception.JsmsApplicationInvoiceException;
import com.jsmsframework.user.mapper.JsmsAgentInfoMapper;
import com.jsmsframework.user.mapper.JsmsUserMapper;
import com.jsmsframework.user.service.*;
import com.ucpaas.sms.common.util.Collections3;
import com.ucpaas.sms.common.util.MD5;
import com.ucpaas.sms.common.util.SecurityUtils;
import com.ucpaas.sms.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by xiongfenglin on 2017/11/23.
 *
 * @author: xiongfenglin
 */
@Service
public class JsmsUserFinanceServiceImpl implements JsmsUserFinanceService {
	public static final String BELONG_SALE = "belongSale";
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JsmsUserService jsmsUserService;
	@Autowired
	private JsmsRoleService jsmsRoleService;
	@Autowired
	private JsmsUserRoleService jsmsUserRoleService;
	@Autowired
	private JsmsUserMapper jsmsUserMapper;
	@Autowired
	private JsmsEmailService emailService;
	@Autowired
	private JsmsAgentInfoService jsmsAgentInfoService;
	@Autowired
	private JsmsAgentAccountService jsmsAgentAccountService;
	@Autowired
	private JsmsOemProductInfoService jsmsOemProductInfoService;
	@Autowired
	private JsmsOemDataConfigService jsmsOemDataConfigService;
	@Autowired
	private JsmsMailpropService jsmsMailpropService;
	@Autowired
	private JsmsAgentInvoiceListService jsmsAgentInvoiceListService;
	@Autowired
	private JsmsAgentInvoiceListMapper jsmsAgentInvoiceListMapper;
	@Autowired
	private JsmsAgentInvoiceConfigService jsmsAgentInvoiceConfigService;

	@Autowired
	private JsmsAgentInvoiceConfigMapper jsmsAgentInvoiceConfigMapper;

	@Autowired
	private JsmsAgentInfoMapper jsmsAgentInfoMapper;
	@Autowired
	private JsmsDictService jsmsDictService;

	@Override
	public R oemAgentRegister(JsmsUserFinance jsmsUserFinance, boolean isCheckInviteCode) {
		// List<JsmsAgentApply> jsmsAgentApplyList= new ArrayList<>();
		// JsmsAgentApply jsmsAgentApply = new JsmsAgentApply();
		R checkResult;
		// 校验参数
		R returnCheckParm = checkParm(jsmsUserFinance, isCheckInviteCode);
		if (returnCheckParm != null) {
			return returnCheckParm;
		}
		// 查询代理商申请的手机号码和邮件是否在申请表中已存在
		/*
		 * JsmsAgentApply checkEmailAndMobile =
		 * jsmsAgentApplyService.checkEmailAndMobileInApply(jsmsUserFinance.
		 * getEmail(),jsmsUserFinance.getMobile()); if(checkEmailAndMobile
		 * !=null){ checkResult =
		 * checkEmailAndMobileEnable(checkEmailAndMobile.getEmail(),
		 * checkEmailAndMobile.getMobile()); if(checkResult != null){ return
		 * checkResult; } }
		 */
		// 查询OEM代理商申请的手机号码和邮件是否在用户表中已存在
		JsmsUser user = jsmsUserService.checkOemEmailAndMobileInUser(jsmsUserFinance.getEmail(),
				jsmsUserFinance.getMobile());
		if (user != null) {
			checkResult = checkEmailAndMobileEnable(user.getEmail(), user.getMobile());
			if (checkResult != null) {
				return checkResult;
			}
		}
		// status 0:待受理,1:受理不通过,2:已受理
		/*
		 * jsmsUserFinance.setStatus(2);
		 * jsmsAgentApply.setStatus(jsmsUserFinance.getStatus());
		 * jsmsAgentApply.setAddress(jsmsUserFinance.getAddress());
		 * if(StringUtils.isNotBlank(jsmsUserFinance.getInviteCode())){
		 * jsmsAgentApply.setBelongSale(Long.parseLong(jsmsUserFinance.
		 * getInviteCode())); }else{
		 * jsmsAgentApply.setBelongSale(jsmsUserMapper.getBelongSale("平台开放BU"));
		 * } jsmsAgentApply.setCity(jsmsUserFinance.getCity());
		 * jsmsAgentApply.setCompany(jsmsUserFinance.getCompany());
		 * jsmsAgentApply.setEmail(jsmsUserFinance.getEmail());
		 * jsmsAgentApply.setMobile(jsmsUserFinance.getMobile());
		 * jsmsAgentApply.setRealname(jsmsUserFinance.getCompany());
		 * jsmsAgentApply.setReason(""); //
		 * jsmsAgentApplyList.add(jsmsAgentApply); int insertNum =
		 * jsmsAgentApplyService.insert(jsmsAgentApply); if(insertNum < 1){
		 * return R.error("申请代理商失败,请联系客服"); }
		 */
		Map<String, String> params = new HashMap<>();
		params.put("productNum", "0");
		params.put("productType", "0");
		params.put("productId", null);
		params.put("email", jsmsUserFinance.getEmail());
		params.put("mobile", jsmsUserFinance.getMobile());
		params.put("realName", jsmsUserFinance.getCompany());
		Long belongSale = jsmsUserMapper.getBelongSale("平台开放BU");
		if (StringUtils.isNotBlank(jsmsUserFinance.getInviteCode())) {
			List<String> list = getSaleList();
			if (list.size() > 0) {
				if (!list.contains(jsmsUserFinance.getInviteCode())) {
					if (belongSale != null && StringUtils.isNotBlank(String.valueOf(belongSale))) {
						params.put("belongSale", String.valueOf(belongSale));
					} else {
						return R.error("无相应的默认邀请码");
					}
				} else {
					params.put("belongSale", String.valueOf(jsmsUserFinance.getInviteCode()));
				}
			} else {
				if (belongSale != null && StringUtils.isNotBlank(String.valueOf(belongSale))) {
					params.put("belongSale", String.valueOf(belongSale));
				} else {
					return R.error("无相应的默认邀请码");
				}
			}
		} else {
			if (belongSale != null && StringUtils.isNotBlank(String.valueOf(belongSale))) {
				params.put("belongSale", String.valueOf(belongSale));
			} else {
				return R.error("无相应的默认邀请码");
			}
		}
		/*
		 * Integer id =
		 * jsmsAgentApplyService.getIdByEmailAndMobile(jsmsUserFinance.getEmail(
		 * ),jsmsUserFinance.getMobile()); if(id !=null){
		 * params.put("applyId",String.valueOf(id)); }else{ return
		 * R.error("申请代理商失败,请联系客服"); }
		 */
		params.put("domain", jsmsUserFinance.getDomainName());
		params.put("copyright", jsmsUserFinance.getCopyright());
		params.put("nav_backcolor", jsmsUserFinance.getNavBackcolor());
		params.put("nav_text_color", jsmsUserFinance.getNavTextColor());
		params.put("oem_agent_domain_name", jsmsUserFinance.getOemAgentDomainName());
		List<JsmsOemProductInfo> jsmsOemProductInfo = getProductInfoListForOemData();
		if (jsmsOemProductInfo.size() > 0) {
			jsmsUserFinance.setTestSmsNumber(100);
			jsmsUserFinance.setHySmsDiscount(BigDecimal.ONE);
			jsmsUserFinance.setTestProductId(jsmsOemProductInfo.get(0).getProductId());// 默认拿第一条
		}
		return confirmAccept(params, jsmsUserFinance);
	}

	private R confirmAccept(Map<String, String> params, JsmsUserFinance jsmsUserFinance) {
		boolean flag = false;
		/*
		 * String password = UUID.randomUUID().toString().replace("-",
		 * "").substring(4,12); params.put("password", password);
		 */
		params.put("password", jsmsUserFinance.getPassword());
		StringBuilder agentIdSb = new StringBuilder("");
		// 生成用户、生成代理商、生成代理商账户、管理代理商角色
		params.put("agent_type", AgentType.OEM代理商.getValue().toString());// 代理商类型,1:销售代理商,2:品牌代理商,3:资源合作商,4:代理商和资源合作,5:OEM代理商
		flag = sumbitRegisterContent(params, jsmsUserFinance);
		if (flag) {
			// 插入t_sms_oem_data_config表
			if (jsmsUserFinance.getTestSmsNumber() != null) {
				if (jsmsUserFinance.getTestSmsNumber().intValue() == 100) {
					int insertOemData = insertSmsOEMDataForRegister(params, jsmsUserFinance);
					if (insertOemData <= 0) {
						throw new IllegalStateException("注册代理商资料配置失败");
					}
				} else {
					throw new IllegalStateException("获取赠送短信错误");
				}
			} else {// 无可赠送短信默认通过
				logger.debug("暂无可赠送的短信包！");
			}
		} else {
			return R.error("申请代理商失败,请联系客服");
		}
		String vUrl = params.get("oem_agent_domain_name");// oem代理商站点地址

		JsmsMailprop mail = jsmsMailpropService.querySmsMailprop(100023);// 100023为代理商注册并直接生成账号和密码的邮件
		// 发送开户邮件到邮箱
		if (mail != null) {
			String body = mail.getText();
			body = body.replace("vUrl", vUrl);
			body = body.replace("vemail", params.get("email"));
			body = body.replace("password", jsmsUserFinance.getPassword());
			boolean sendEmail = emailService.sendHtmlEmail(mail.getFrm(), params.get("email"), mail.getSubject(), body);
			if (sendEmail) {
				return R.ok("注册成功！请注意查收邮件");
			} else {
				return R.ok("注册成功！发送邮件失败！请联系客服");
			}
		} else {
			return R.error("邮件服务器异常！");
		}

	}

	private boolean sumbitRegisterContent(Map<String, String> params, JsmsUserFinance jsmsUserFinance) {
		int insertFlag = 0;
		// 插入t_sms_user表
		int insertUser = this.insertSmsUserInfoForRegister(params);
		if (insertUser <= 0) {
			throw new IllegalStateException("注册新增用户失败");
		} else {
			insertFlag += 1;
			jsmsUserFinance.setUserId(jsmsUserService.getId(jsmsUserFinance.getEmail(), jsmsUserFinance.getMobile()));
		}
		int agent_id = AgentIdUtil.getAgentId();
		params.put("agent_id", agent_id + "");// 保存代理商id，生成oem资料需要用到

		// 插入t_sms_agent_info表
		int insertAgent = this.insertSmsAgentInfoForRegister(agent_id, params, jsmsUserFinance);
		if (insertAgent <= 0) {
			throw new IllegalStateException("注册新增代理商信息失败");
		} else {
			insertFlag += 1;
		}
		// 插入t_sms_agent_account表
		int insertAgentAccount = this.insertSmsAgentAccountForRegister(agent_id);
		if (insertAgentAccount <= 0) {
			throw new IllegalStateException("注册新增代理商帐户失败");
		} else {
			insertFlag += 1;
		}

		// 给用户赋代理商的角色t_sms_user_role
		int createUserRole = this.createUserRole(jsmsUserFinance.getUserId(), params.get("agent_type"));
		if (createUserRole <= 0) {
			throw new IllegalStateException("给用户赋代理商的角色失败");
		} else {
			insertFlag += 1;
		}
		if (insertFlag == 4) {
			return true;
		} else {
			return false;
		}
	}

	private int insertSmsUserInfoForRegister(Map<String, String> params) {
		String email = params.get("email");
		String mobile = params.get("mobile");
		int emailAgentNum = jsmsUserService.querySmsUserCountByEmail(email);
		if (emailAgentNum > 0) {
			throw new RuntimeException("操作失败，邮箱已经存在");
		}
		int mobileAgentNum = jsmsUserService.querySmsUserCountByMobile(mobile);
		if (mobileAgentNum > 0) {
			throw new RuntimeException("操作失败，手机号码已经存在");
		}
		JsmsUser smsUserParams = new JsmsUser();
		String sid = SecurityUtils.generateSid();
		String agent_type = params.get("agent_type");
		String web_id = null;
		if ("2".equals(agent_type)) {
			web_id = WebId.代理商平台.getValue().toString();
		} else {
			web_id = WebId.OEM代理商平台.getValue().toString();
		}
		String password = SecurityUtils.encryptMD5(params.get("password"));
		smsUserParams.setSid(sid);
		smsUserParams.setEmail(email);
		smsUserParams.setPassword(MD5.md5(password));
		smsUserParams.setUserType("1");
		smsUserParams.setStatus("1");
		smsUserParams.setMobile(mobile);
		smsUserParams.setRealname(params.get("realName"));
		smsUserParams.setCreateDate(new Date());
		smsUserParams.setUpdateDate(new Date());
		smsUserParams.setLoginTimes(0);
		smsUserParams.setWebId(Integer.parseInt(web_id)); // web应用ID：1短信调度系统
															// 2代理商平台 3运营平台
		int insert = jsmsUserService.insert(smsUserParams);
		// agentApply.put("userId", smsUserParams.get("id"));
		if (insert <= 0) {
			logger.debug("代理商受理 / 添加用户信息失败(t_sms_user), 参数  --> {} \r\n\t insert结果 --> {}", smsUserParams, insert);
		}
		return insert;
	}

	// 插入t_sms_oem_data_config表
	private int insertSmsOEMDataForRegister(Map<String, String> params, JsmsUserFinance jsmsUserFinance) {
		JsmsOemDataConfig jsmsOemDataConfig = new JsmsOemDataConfig();
		jsmsOemDataConfig.setTestProductId(jsmsUserFinance.getTestProductId());
		jsmsOemDataConfig.setTestSmsNumber(jsmsUserFinance.getTestSmsNumber());
		jsmsOemDataConfig.setHySmsDiscount(jsmsUserFinance.getHySmsDiscount());
		jsmsOemDataConfig.setYxSmsDiscount(BigDecimal.ONE);
		jsmsOemDataConfig.setGjSmsDiscount(BigDecimal.ONE);
		jsmsOemDataConfig.setAgentId(Integer.parseInt(params.get("agent_id")));
		jsmsOemDataConfig.setDomainName(jsmsUserFinance.getDomainName());
		jsmsOemDataConfig.setCopyrightText(jsmsUserFinance.getCopyright());
		jsmsOemDataConfig.setNavigationBackcolor(jsmsUserFinance.getNavBackcolor());
		jsmsOemDataConfig.setNavigationTextColor(jsmsUserFinance.getNavTextColor());
		int insertOemData = jsmsOemDataConfigService.insert(jsmsOemDataConfig);
		if (insertOemData <= 0) {
			logger.debug("代理商受理 / 添加OEM资料配置失败(t_sms_oem_data_config), 参数  --> {} \r\n\t insert结果 --> {}",
					jsmsOemDataConfig, insertOemData);
		}
		return insertOemData;
	}

	// 插入t_sms_agent_info表
	private int insertSmsAgentInfoForRegister(int agent_id, Map<String, String> params,
			JsmsUserFinance jsmsUserFinance) {
		int insert = 0;
		JsmsAgentInfo smsAgentInfoParmas = new JsmsAgentInfo();
		smsAgentInfoParmas.setAgentId(agent_id);
		smsAgentInfoParmas.setAdminId(jsmsUserFinance.getUserId());
		smsAgentInfoParmas.setAgentName(jsmsUserFinance.getCompany());
		smsAgentInfoParmas.setShorterName(jsmsUserFinance.getCompany());
		smsAgentInfoParmas.setAgentType(Integer.parseInt(params.get("agent_type")));
		smsAgentInfoParmas.setStatus("1");
		smsAgentInfoParmas.setOauthStatus(2);
		smsAgentInfoParmas.setOauthDate(null);
		smsAgentInfoParmas.setAddress(jsmsUserFinance.getAddress());
		smsAgentInfoParmas.setCompany(jsmsUserFinance.getCompany());
		smsAgentInfoParmas.setCompanyNbr(null);
		smsAgentInfoParmas.setMobile(params.get("mobile"));
		smsAgentInfoParmas.setCreateTime(new Date());
		smsAgentInfoParmas.setUpdateTime(null);
		smsAgentInfoParmas.setRemark(null);
		smsAgentInfoParmas.setBelongSale(Long.parseLong(params.get("belongSale")));
		insert = jsmsAgentInfoService.insert(smsAgentInfoParmas);
		if (insert <= 0) {
			logger.debug("代理商受理 / 添加代理商信息失败(t_sms_agent_info), 参数  --> {} \r\n\t insert结果 --> {}", smsAgentInfoParmas,
					insert);
		}
		return insert;
	}

	private int insertSmsAgentAccountForRegister(int agent_id) {
		JsmsAgentAccount smsAgentAccountParms = new JsmsAgentAccount();
		smsAgentAccountParms.setAgentId(agent_id);
		smsAgentAccountParms.setBalance(BigDecimal.ZERO);
		smsAgentAccountParms.setCreditBalance(BigDecimal.ZERO);
		smsAgentAccountParms.setAccumulatedIncome(BigDecimal.ZERO);
		smsAgentAccountParms.setCommissionIncome(BigDecimal.ZERO);
		smsAgentAccountParms.setAccumulatedRecharge(BigDecimal.ZERO);
		int insert = jsmsAgentAccountService.insert(smsAgentAccountParms);
		if (insert <= 0) {
			logger.debug("代理商受理 / 给代理商添加账户信息失败(t_sms_agent_account), 参数  --> {} \r\n\t insert结果 --> {}",
					smsAgentAccountParms, insert);
		}
		return insert;
	}

	private int createUserRole(Long userId, String agent_type) {
		// 代理商的角色id为3
		int roleId = 3;
		if ("2".equals(agent_type)) {
			// 品牌代理商
			roleId = 3;
		} else {
			// oem代理商
			roleId = jsmsRoleService.queryOemRoleId();
		}

		JsmsUserRole userRoleParams = new JsmsUserRole();
		userRoleParams.setRoleId(roleId);
		userRoleParams.setUserId(userId);
		int insert = jsmsUserRoleService.insert(userRoleParams);
		if (insert <= 0) {
			logger.debug("代理商受理 / 给代理商生成账号分配菜单(t_sms_user_role)失败, 参数  --> {} \r\n\t insert结果 --> {}", userRoleParams,
					insert);
		}
		return insert;
	}

	@Override
	public List<String> getSaleList() {
		// 查询角色名都为‘销售人员’的角色id
		String roleId = jsmsRoleService.getSaleRoleId();
		if (roleId == null) {
			throw new RuntimeException("查找销售人员失败！");
		}
		List<String> userIdList = new ArrayList<>();
		List<JsmsUserRole> userIdMapList = jsmsUserRoleService.getUserIdFromUserRoleByRoleId(roleId);
		for (JsmsUserRole jsmsUserRole : userIdMapList) {
			String userId = String.valueOf(jsmsUserRole.getUserId());
			userIdList.add(userId);
		}
		List<String> saleList = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		params.put("userIdList", userIdList);
		List<JsmsUser> saleMapList = jsmsUserService.getSaleInfoByUserId(params);
		for (JsmsUser jsmsUser : saleMapList) {
			saleList.add(String.valueOf(jsmsUser.getId()));
		}

		return saleList;
	}

	private R checkEmailAndMobileEnable(String email, String mobile) {
		if (StringUtils.isNoneBlank(email)) {
			return R.error("邮箱已经被注册");
		}
		if (StringUtils.isNoneBlank(mobile)) {
			return R.error("手机号已经被注册");
		}
		return null;
	}

	private R checkParm(JsmsUserFinance jsmsUserFinance, boolean isCheckInviteCode) {
		if (StringUtils.isBlank(jsmsUserFinance.getEmail())) {
			return R.error("请输入登入邮箱!");
		} else {
			if (!CheckEmail.checkEmail(jsmsUserFinance.getEmail())) {
				return R.error("登入邮箱格式错误!");
			}
		}
		if (StringUtils.isBlank(jsmsUserFinance.getMobile())) {
			return R.error("请输入手机号码!");
		} else {
			/*
			 * if(!CheckMobileNumber.checkMobileNumber(jsmsUserFinance.getMobile
			 * ())){ return R.error("手机号码格式错误!"); }
			 */
			if (jsmsUserFinance.getMobile().length() != 11) {
				return R.error("手机号码格式错误!");
			}
		}
		if (StringUtils.isBlank(jsmsUserFinance.getPassword())) {
			return R.error("请输入密码!");
		} else {
			if (jsmsUserFinance.getPassword().length() < 8 || jsmsUserFinance.getPassword().length() > 12) {
				return R.error("密码只能输入8-12位!");
			}
		}
		if (StringUtils.isBlank(jsmsUserFinance.getConfirmPassword())) {
			return R.error("请输入确认密码!");
		} else {
			if (jsmsUserFinance.getConfirmPassword().length() < 8
					|| jsmsUserFinance.getConfirmPassword().length() > 12) {
				return R.error("确认密码只能输入8-12位!");
			}
		}
		if (!jsmsUserFinance.getPassword().equals(jsmsUserFinance.getConfirmPassword())) {
			return R.error("密码输入不一致!");
		}
		if (StringUtils.isBlank(jsmsUserFinance.getCompany())) {
			return R.error("请输入企业/个人名称!");
		}
		if (isCheckInviteCode) {
			if (StringUtils.isNotBlank(jsmsUserFinance.getInviteCode())) {
				List<String> list = getSaleList();
				if (list.size() > 0) {
					if (!list.contains(jsmsUserFinance.getInviteCode())) {
						R r = new R();
						r.setCode(501);
						r.setMsg("邀请码错误,是否继续?");
						return r;
					}
				} else {
					R r = new R();
					r.setCode(501);
					r.setMsg("邀请码错误,是否继续?");
					return r;
				}
			}
		}
		return null;
	}

	// 获取产品包
	public List<JsmsOemProductInfo> getProductInfoListForOemData() {

		Map<String, Object> params = new HashMap<>();
		params.put("productType", 0); // 产品类型，0：行业，1：营销，2：国际，7：USSD，8：闪信，9：挂机短信，其中0和1为普通短信
		params.put("operatorCode", 0); // 对应运营商,0:全网,1:移动,2:联通,3:电信,4:国际
		params.put("areaCode", 0); // 适用地区,0:全国,1:国际,2:省网
		params.put("status", 1); // 状态，0：待上架，1：已上架，2：已下架
		params.put("is_show", 0); // 产品是否对所有OEM代理商可见，0：否，1：是
		params.put("unit_price", 0); // 产品是否对所有OEM代理商可见，0：否，1：是
		List<JsmsOemProductInfo> jsmsOemProductInfo = jsmsOemProductInfoService.getProductInfo(params);
		if (jsmsOemProductInfo.size() <= 0) {
			logger.debug("代理商受理 / 给代理商获取赠送行业产品包失败,  \r\n\t 结果 --> {}", jsmsOemProductInfo);
		}
		return jsmsOemProductInfo;
	}

	/**
	 * 获取可开票金额
	 * 
	 * @param agentId
	 *            代理商ID
	 * @return
	 */
	@Override
	public BigDecimal getCanInvoiceAmount(Integer agentId) {
		Assert.notNull(agentId, "客户ID不能为空");

		JsmsAgentInfo agentInfo = jsmsAgentInfoService.getByAgentId(agentId);
		if (null == agentInfo) {
			throw new JsmsApplicationInvoiceException("客户信息不存在");
		}

		if (!AgentType.OEM代理商.getValue().equals(agentInfo.getAgentType())) {
			throw new JsmsApplicationInvoiceException("客户类型不支持");
		}

		JsmsAgentAccount agentAccount = jsmsAgentAccountService.getByAgentId(agentId);
		if (null == agentAccount) {
			throw new JsmsApplicationInvoiceException("客户信息不存在");
		}

		// 可开票金额=累计充值金额-已开票金额-待开票金额-历史退款金额
		BigDecimal recharge = agentAccount.getAccumulatedRecharge();
		Assert.notNull(recharge, "客户累计充值金额不能为空");

		BigDecimal hasTake = agentAccount.getHasTakeInvoice();
		Assert.notNull(hasTake, "客户已开票金额不能为空");

		BigDecimal hisBack = agentAccount.getAccumulatedRefund();
		Assert.notNull(hisBack, "客户历史退款金额不能为空");

		// 查询待开票金额
		Map<String, Object> params = new HashMap<>();
		params.put("agentId", agentId);
		params.put("source", InvoiceSourceEnum.发票申请.getValue());
		params.put("statusList", Arrays.asList(InvoiceStatusEnum.待审核.getValue(), InvoiceStatusEnum.开票中.getValue()));
		BigDecimal waitAnvoice = jsmsAgentInvoiceListService.getWaitInvoiceListAmount(params);
		Assert.notNull(waitAnvoice, "客户待开票金额不能为空");

		BigDecimal canOpen = recharge.subtract(hasTake).subtract(waitAnvoice).subtract(hisBack);
		if (BigDecimal.ZERO.compareTo(canOpen) >= 0) {
			canOpen = BigDecimal.ZERO;
		}

		return canOpen.setScale(2, BigDecimal.ROUND_DOWN);
	}

	@Override
	public BigDecimal getCanBackAmount(Integer agentId) {
		Assert.notNull(agentId, "客户ID不能为空");

		JsmsAgentInfo agentInfo = jsmsAgentInfoService.getByAgentId(agentId);
		if (null == agentInfo) {
			throw new JsmsApplicationInvoiceException("客户信息不存在");
		}

		if (!AgentType.OEM代理商.getValue().equals(agentInfo.getAgentType())) {
			throw new JsmsApplicationInvoiceException("客户类型不支持");
		}

		JsmsAgentAccount agentAccount = jsmsAgentAccountService.getByAgentId(agentId);
		if (null == agentAccount) {
			throw new JsmsApplicationInvoiceException("客户信息不存在");
		}

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

	private static final Integer INVOICE_TO_ADD = 1; // 发票添加
	private static final Integer INVOICE_TO_MOD = 2; // 发票修改
	private static final Integer INVOICE_TO_NON = 3; // 发票不做操作

	@Override
	public R applicationInvoice(JsmsAgentInvoiceList invoice) {
		Assert.notNull(invoice, "发票申请信息不能为空");
		Assert.notNull(invoice.getInvoiceId(), "发票申请ID不能为空");
		Assert.notNull(invoice.getOperator(), "操作人不能为空");
		Assert.notNull(invoice.getWebId(), "WEBID不能为空");

		// 设置申请人
		invoice.setApplicant(invoice.getOperator());

		if (invoice.getAgentId() == null) {
			return R.error(Code.SYS_ERR, "客户ID不能为空");
		}

		if (invoice.getInvoiceAmount() == null) {
			return R.error(Code.SYS_ERR, "发票金额不能为空");
		}

		if (invoice.getInvoiceAmount() == null || invoice.getInvoiceAmount().compareTo(BigDecimal.ZERO) <= 0)
		{
			return R.error(Code.SYS_ERR, "发票金额必须大于0");
		}

		if (invoice.getInvoiceId().length() > 32) {
			return R.error(Code.SYS_ERR, "发票申请ID长度必须介于 1 和 32 之间");
		}

		if (StringUtils.isNotBlank(invoice.getRemark()) && invoice.getRemark().length() > 100) {
			return R.error(Code.SYS_ERR, "备注长度必须介于 0 和 100 之间");
		}

		// 发票类型
		if (invoice.getInvoiceType() == null
				|| StringUtils.isBlank(InvoiceTypeEnum.getDescByValue(invoice.getInvoiceType()))) {
			return R.error(Code.SYS_ERR,
					"必须是" + InvoiceTypeEnum.普通发票.getDesc() + "或者" + InvoiceTypeEnum.增值税专票.getDesc());
		}

		// 检验代理商类型
		JsmsAgentInfo agentInfo = jsmsAgentInfoService.getByAgentId(invoice.getAgentId());
		if (agentInfo.getAgentType().intValue() != AgentType.OEM代理商.getValue().intValue())
		{
			return R.error(Code.SYS_ERR, "此客户不支持");
		}

		R r = R.ok("");
		Integer oper = invoice.getInvoiceType().intValue() == InvoiceTypeEnum.普通发票.getValue().intValue()
				? checkNormalInvoice(invoice, r) : checkVATInvoice(invoice, r);
		if (r.getCode().intValue() > Code.SUCCESS.getValue().intValue()) {
			return r;
		}

		logger.debug("发票申请失败，检查是否修改了发票配置的返回值为空{}", oper);
		Assert.notNull(oper, "系统内部错误");

		// 插入申请记录
		invoice.setStatus(InvoiceStatusEnum.待审核.getValue());
		invoice.setCreateTime(Calendar.getInstance().getTime());
		invoice.setUpdateTime(invoice.getCreateTime());
		invoice.setSource(InvoiceSourceEnum.发票申请.getValue());

		if (invoice.getInvoiceType().equals(InvoiceTypeEnum.增值税专票.getValue()))
		{
			invoice.setInvoiceBody(InvoiceBodyEnum.企业.getValue());
		}

		// 获取可开票金额
		BigDecimal canOpen = getCanInvoiceAmount(invoice.getAgentId());

		// 校验一次可开票金额
		if (invoice.getInvoiceAmount().compareTo(canOpen) > 0)
		{
			return R.error(Code.SYS_ERR, "可开票金额不足");
		}

		int count = jsmsAgentInvoiceListService.insert(invoice);
		if (count <= 0) {
			logger.debug("发票申请添加数据库失败，更新条数为{}", count);
			return R.error(Code.SYS_ERR, "发票申请失败");
		}

		// 发票配置
		if (oper.equals(INVOICE_TO_NON)) {
			return R.ok("发票申请成功");
		}

		if (oper.equals(INVOICE_TO_MOD)) {
			JsmsAgentInvoiceConfig config = (JsmsAgentInvoiceConfig) r.getData();
			logger.debug("发票申请失败，检查是否修改了发票配置时构造发票配置信息失败{}", config);
			Assert.notNull(oper, "系统内部错误");

			int configCount = jsmsAgentInvoiceConfigService.updateSelective(config);
			if (configCount <= 0) {
				logger.debug("发票申请失败，修改发票配置信息{} 更新条数为 {}", config, configCount);
				throw new JsmsApplicationInvoiceException("发票申请失败");
			}
		}

		if (oper.equals(INVOICE_TO_ADD)) {
			JsmsAgentInvoiceConfig config = (JsmsAgentInvoiceConfig) r.getData();
			logger.debug("发票申请失败，检查是否修改了发票配置时构造发票配置信息失败{}", config);
			Assert.notNull(oper, "系统内部错误");

			int configCount = jsmsAgentInvoiceConfigService.insert(config);
			if (configCount <= 0) {
				logger.debug("发票申请失败，添加发票配置信息{} 更新条数为 {}", config, configCount);
				throw new JsmsApplicationInvoiceException("发票申请失败");
			}
		}

		return R.ok("发票申请成功");
	}

	private Integer checkNormalInvoice(JsmsAgentInvoiceList invoice, R r) {
		if (invoice.getInvoiceBody() == null
				|| StringUtils.isBlank(InvoiceBodyEnum.getDescByValue(invoice.getInvoiceBody()))) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("发票主体必须是" + InvoiceBodyEnum.个人.getDesc() + "或者" + InvoiceBodyEnum.企业.getDesc());
			return null;
		}

		if (StringUtils.isBlank(invoice.getInvoiceHead()) || invoice.getInvoiceHead().length() > 100) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("发票抬头长度必须介于 1 和 100 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getEmail()) || invoice.getEmail().length() > 50) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("电子邮箱长度必须介于 1 和 50 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getEmail()) || invoice.getEmail().length() > 50) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("电子邮箱长度必须介于 1 和 50 之间");
			return null;
		}

		if (!RegexUtils.checkEmail(invoice.getEmail())) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("电子邮箱格式错误");
			return null;
		}

		if (invoice.getInvoiceBody().intValue() == InvoiceBodyEnum.企业.getValue().intValue()) {
			if (StringUtils.isBlank(invoice.getCreditCode()) || invoice.getCreditCode().length() > 50) {
				r.setCode(Code.SYS_ERR);
				r.setMsg("统一社会信用代码长度必须介于 1 和 50 之间");
				return null;
			}
		}

		// 查询
		Map<String, Object> params = new HashMap<>();
		params.put("agingId", invoice.getAgentId());
		params.put("invoiceType", invoice.getInvoiceType());
		params.put("status", InvoiceConfigStatus.正常.getValue().intValue());
		List<JsmsAgentInvoiceConfig> configs = jsmsAgentInvoiceConfigService.findList(params);
		if (Collections3.isEmpty(configs)) {
			// 构造对象
			JsmsAgentInvoiceConfig config = new JsmsAgentInvoiceConfig();
			config.setAgingId(invoice.getAgentId());
			config.setInvoiceBody(invoice.getInvoiceBody());
			config.setInvoiceType(invoice.getInvoiceType());
			config.setInvoiceHead(invoice.getInvoiceHead());
			config.setEmail(invoice.getEmail());
			if (invoice.getInvoiceBody().intValue() == InvoiceBodyEnum.企业.getValue().intValue()) {
				config.setCreditCode(invoice.getCreditCode());
			}

			config.setCreateTime(Calendar.getInstance().getTime());
			config.setUpdateTime(config.getCreateTime());
			config.setStatus(InvoiceConfigStatus.正常.getValue());
			config.setOperator(invoice.getOperator());

			r.setData(config);
			logger.debug("普票配置不存在需要添加{}", config);
			return INVOICE_TO_ADD;
		} else {
			if (configs.size() > 1) {
				r.setCode(Code.SYS_ERR);
				r.setMsg("普票信息存在多条");
				logger.debug("普票信息配置存在多条{}", configs);
				throw new JsmsApplicationInvoiceException("普票信息存在多条");
			}

			JsmsAgentInvoiceConfig config = configs.get(0);
			if (!invoice.getInvoiceBody().equals(config.getInvoiceBody())
					|| !invoice.getInvoiceHead().equals(config.getInvoiceHead())
					|| !invoice.getEmail().equals(config.getEmail())
					|| (invoice.getInvoiceBody().intValue() == InvoiceBodyEnum.企业.getValue().intValue()
							&& !invoice.getCreditCode().equals(config.getCreditCode()))) {
				config.setInvoiceBody(invoice.getInvoiceBody());
				config.setInvoiceHead(invoice.getInvoiceHead());
				config.setCreditCode(invoice.getCreditCode());
				config.setEmail(invoice.getEmail());
				config.setOperator(invoice.getOperator());
				config.setUpdateTime(Calendar.getInstance().getTime());
				logger.debug("普票配置需要更新{}", config);
				r.setData(config);
				return INVOICE_TO_MOD;
			}
		}

		return INVOICE_TO_NON;
	}

	private Integer checkVATInvoice(JsmsAgentInvoiceList invoice, R r) {
		if (StringUtils.isBlank(invoice.getInvoiceHead()) || invoice.getInvoiceHead().length() > 100) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("发票抬头长度必须介于 1 和 100 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getBank()) || invoice.getBank().length() > 50) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("开户银行长度必须介于 1 和 50 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getBankAccount()) || invoice.getBankAccount().length() > 30) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("开户账号长度必须介于 1 和 30 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getCreditCode()) || invoice.getCreditCode().length() > 50) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("统一社会信用代码长度必须介于 1 和 50 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getCompanyRegAddr()) || invoice.getCompanyRegAddr().length() > 50) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("公司注册地址长度必须介于 1 和 100 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getTelphone()) || invoice.getTelphone().length() > 20) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("公司固定电话必须介于 1 和 20 之间");
			return null;
		}

		if (!RegexUtils.isTelPhone(invoice.getTelphone())) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("公司固定电话格式错误");
			return null;
		}

		if (StringUtils.isBlank(invoice.getToName()) || invoice.getToName().length() > 50) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("收件人必须介于 1 和 50 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getToPhone()) || invoice.getToPhone().length() > 20) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("收件人手机必须介于 1 和 20 之间");
			return null;
		}

		if (!RegexUtils.isMobile(invoice.getToPhone())) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("收件人手机格式错误");
			return null;
		}

		if (StringUtils.isBlank(invoice.getToAddr()) || invoice.getToAddr().length() > 50) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("收件人地址介于 1 和 50 之间");
			return null;
		}

		if (StringUtils.isBlank(invoice.getToAddrDetail()) || invoice.getToAddrDetail().length() > 100) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("收件人详细地址介于 1 和 100 之间");
			return null;
		}

		if (StringUtils.isNotBlank(invoice.getToQq()) && invoice.getToQq().length() > 20) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("收件人QQ介于 0 和 20 之间");
			return null;
		}

		if (StringUtils.isNotBlank(invoice.getEmail()) && invoice.getEmail().length() > 50) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("电子邮箱长度必须介于 1 和 50 之间");
			return null;
		}

		if (StringUtils.isNotBlank(invoice.getEmail()) && !RegexUtils.checkEmail(invoice.getEmail())) {
			r.setCode(Code.SYS_ERR);
			r.setMsg("电子邮箱格式错误");
			return null;
		}

		// 查询
		Map<String, Object> params = new HashMap<>();
		params.put("agingId", invoice.getAgentId());
		params.put("invoiceType", invoice.getInvoiceType());
		params.put("status", InvoiceConfigStatus.正常.getValue().intValue());
		List<JsmsAgentInvoiceConfig> configs = jsmsAgentInvoiceConfigService.findList(params);
		if (Collections3.isEmpty(configs)) {
			// 构造对象
			JsmsAgentInvoiceConfig config = new JsmsAgentInvoiceConfig();
			config.setAgingId(invoice.getAgentId());
			config.setInvoiceBody(InvoiceBodyEnum.企业.getValue());
			config.setInvoiceType(invoice.getInvoiceType());
			config.setInvoiceHead(invoice.getInvoiceHead());
			config.setBank(invoice.getBank());
			config.setBankAccount(invoice.getBankAccount());
			config.setCreditCode(invoice.getCreditCode());
			config.setCompanyRegAddr(invoice.getCompanyRegAddr());
			config.setTelphone(invoice.getTelphone());
			config.setToName(invoice.getToName());
			config.setToPhone(invoice.getToPhone());
			config.setToAddr(invoice.getToAddr());
			config.setToAddrDetail(invoice.getToAddrDetail());
			config.setToQq(invoice.getToQq());
			config.setCreateTime(Calendar.getInstance().getTime());
			config.setUpdateTime(config.getCreateTime());
			config.setStatus(InvoiceConfigStatus.正常.getValue());
			config.setOperator(invoice.getOperator());
			r.setData(config);
			logger.debug("增票配置不存在需要添加{}", config);
			return INVOICE_TO_ADD;
		} else {
			if (configs.size() > 1) {
				r.setCode(Code.SYS_ERR);
				r.setMsg("增票信息存在多条");
				logger.debug("增票信息配置存在多条{}", configs);
				throw new JsmsApplicationInvoiceException("增票信息存在多条");
			}

			JsmsAgentInvoiceConfig config = configs.get(0);

			boolean isChangeQQ = false;
			if ((StringUtils.isBlank(invoice.getToQq()) && StringUtils.isNotBlank(config.getToQq()))
					|| (StringUtils.isNotBlank(invoice.getToQq()) && StringUtils.isBlank(config.getToQq()))
					|| (StringUtils.isNotBlank(invoice.getToQq()) && StringUtils.isNotBlank(config.getToQq()) &&
						!invoice.getToQq().equals(config.getToQq()))) {
				isChangeQQ = true;
			}

			boolean isChangeEmail = false;
			if ((StringUtils.isBlank(invoice.getEmail()) && StringUtils.isNotBlank(config.getEmail()))
					|| (StringUtils.isNotBlank(invoice.getEmail()) && StringUtils.isBlank(config.getEmail()))
					|| (StringUtils.isNotBlank(invoice.getEmail()) && StringUtils.isNotBlank(config.getEmail())
						&& !invoice.getEmail().equals(config.getEmail())) ) {
				isChangeEmail = true;
			}

			if (!invoice.getInvoiceHead().equals(config.getInvoiceHead()) || !config.getBank().equals(config.getBank())
					|| !invoice.getBankAccount().equals(config.getBankAccount())
					|| !invoice.getCreditCode().equals(config.getCreditCode())
					|| !invoice.getCompanyRegAddr().equals(config.getCompanyRegAddr())
					|| !invoice.getTelphone().equals(config.getTelphone())
					|| !invoice.getToName().equals(config.getToName())
					|| !invoice.getToPhone().equals(config.getToPhone())
					|| !invoice.getToAddr().equals(config.getToAddr())
					|| !invoice.getToAddrDetail().equals(config.getToAddrDetail())
					|| isChangeQQ
					|| isChangeEmail) {

				config.setInvoiceHead(invoice.getInvoiceHead());
				config.setBank(invoice.getBank());
				config.setBankAccount(invoice.getBankAccount());
				config.setCreditCode(invoice.getCreditCode());
				config.setCompanyRegAddr(invoice.getCompanyRegAddr());
				config.setTelphone(invoice.getTelphone());
				config.setToName(invoice.getToName());
				config.setToPhone(invoice.getToPhone());
				config.setToAddr(invoice.getToAddr());
				config.setToAddrDetail(invoice.getToAddrDetail());
				config.setToQq(invoice.getToQq());
				config.setEmail(invoice.getEmail());
				config.setCreditCode(invoice.getCreditCode());
				config.setOperator(invoice.getOperator());
				config.setUpdateTime(Calendar.getInstance().getTime());
				logger.debug("增票配置需要更新{}", config);
				r.setData(config);
				return INVOICE_TO_MOD;
			}
		}

		return INVOICE_TO_NON;
	}


	@Override
	public JsmsPage queryPageList(JsmsPage page, WebId webId) {
		int index =(page.getPage()-1)*page.getRows();
		List<JsmsAgentInvoiceListDTO> jsmsAgentInvoiceListDTOList = new ArrayList<>();
		List<JsmsAgentInvoiceList> list = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		if(webId.getValue().equals(WebId.OEM代理商平台.getValue())){
			page.getParams().put("system",WebId.OEM代理商平台.getValue());
		}else if(webId.getValue().equals(WebId.运营平台.getValue())){
			map = getagentIdsAndBelongSales(page.getParams().get("belongSale"),page.getParams().get("applicationOperation"));
			if(map != null){
				page.getParams().put("belongSales",map.get("belongSales"));
				page.getParams().put("agentIds",map.get("agentIds"));
			}
			page.getParams().put("system",WebId.运营平台.getValue());
		}
		list =jsmsAgentInvoiceListMapper.queryPageList(page);
		if(list.size()>0){
			for (int i =0;i<list.size();i++){
				index =index+1;
				JsmsAgentInvoiceListDTO jsmsAgentInvoiceListDTO = new JsmsAgentInvoiceListDTO();
				BeanUtil.copyProperties(list.get(i),jsmsAgentInvoiceListDTO);
				if(webId.getValue().equals(WebId.运营平台.getValue())){
					jsmsAgentInvoiceListDTO =getMessage(list.get(i).getAgentId(),list.get(i).getAuditor(),jsmsAgentInvoiceListDTO);
				}
				JsmsUser jsmsUserApplicant =jsmsUserService.getById2(list.get(i).getApplicant());
				if(jsmsUserApplicant != null){
					jsmsAgentInvoiceListDTO.setApplicantStr(jsmsUserApplicant.getRealname());
				}
				if(list.get(i).getStatus().equals(InvoiceStatusEnum.待审核.getValue())){
					jsmsAgentInvoiceListDTO.setFlag(1);
				}else{
					jsmsAgentInvoiceListDTO.setFlag(0);
				}
				jsmsAgentInvoiceListDTO.setInvoiceProject("信息服务费");
				jsmsAgentInvoiceListDTOList.add(jsmsAgentInvoiceListDTO);
				jsmsAgentInvoiceListDTO.setRowNum(index);
				//格式化金额为小数点后两位
				BigDecimal invoiceAmount = list.get(i).getInvoiceAmount();
				jsmsAgentInvoiceListDTO.setInvoiceAmountStr(invoiceAmount.setScale(2, BigDecimal.ROUND_DOWN).toString());
			}
		}
		page.setData(jsmsAgentInvoiceListDTOList);
		return page;
	}

	@Override
	public JsmsAgentInvoiceListDTO checkDetailedInformation(Integer id,Integer invoiceType,WebId webId) {
		JsmsAgentInvoiceListDTO jsmsAgentInvoiceListDTO = new JsmsAgentInvoiceListDTO();
		JsmsAgentInvoiceList jsmsAgentInvoiceList =null;
		jsmsAgentInvoiceList =  jsmsAgentInvoiceListMapper.getByIdAndInvoiceType(id,invoiceType);
		if(jsmsAgentInvoiceList!=null){
			BeanUtil.copyProperties(jsmsAgentInvoiceList,jsmsAgentInvoiceListDTO);
			jsmsAgentInvoiceListDTO.setInvoiceProject("信息服务费");
			JsmsUser jsmsUserApplicant =jsmsUserService.getById2(jsmsAgentInvoiceList.getApplicant());
			if(jsmsUserApplicant != null){
				jsmsAgentInvoiceListDTO.setApplicantStr(jsmsUserApplicant.getRealname());
			}
			if(webId.getValue().equals(WebId.运营平台.getValue())){
				jsmsAgentInvoiceListDTO =getMessage(jsmsAgentInvoiceList.getAgentId(),jsmsAgentInvoiceList.getAuditor(),jsmsAgentInvoiceListDTO);
			}
			Integer expressCompany = jsmsAgentInvoiceList.getExpressCompany();
			if(expressCompany!=null){
				Map<String,Object> params=new HashMap<>();
				params.put("paramType","express");
				params.put("paramKey",String.valueOf(expressCompany));
				//获取快递公司名称
				List<JsmsDict> list = jsmsDictService.findList(params);
				if(list!=null && list.size()>0){
					jsmsAgentInvoiceListDTO.setExpressCompanyStr(list.get(0).getParamValue());
				}
			}

			//格式化金额为小数点后两位
			BigDecimal invoiceAmount = jsmsAgentInvoiceList.getInvoiceAmount();
			jsmsAgentInvoiceListDTO.setInvoiceAmountStr(invoiceAmount.setScale(2, BigDecimal.ROUND_DOWN).toString());
		}
		return jsmsAgentInvoiceListDTO;
	}

	@Override
	public List<JsmsAgentInvoiceListDTO> getAgentInvoiceLists(Map<String, Object> params) {
		List<JsmsAgentInvoiceList> list = new ArrayList<>();
		List<JsmsAgentInvoiceListDTO> jsmsAgentInvoiceListDTOList = new ArrayList<>();
		int index =0;
		Map<String,Object> map = new HashMap<>();
		map = getagentIdsAndBelongSales(params.get("belongSale"),params.get("applicationOperation"));
		if(map != null){
			params.put("belongSales",map.get("belongSales"));
			params.put("agentIds",map.get("agentIds"));
		}
		list =jsmsAgentInvoiceListMapper.getAgentInvoiceLists(params);
		if(list.size()>0){
			for (int i =0;i<list.size();i++){
				index =index+1;
				JsmsAgentInvoiceListDTO jsmsAgentInvoiceListDTO = new JsmsAgentInvoiceListDTO();
				BeanUtil.copyProperties(list.get(i),jsmsAgentInvoiceListDTO);
				jsmsAgentInvoiceListDTO.setRowNum(index);
				jsmsAgentInvoiceListDTO =getMessage(list.get(i).getAgentId(),list.get(i).getAuditor(),jsmsAgentInvoiceListDTO);
				JsmsUser jsmsUserApplicant =jsmsUserService.getById2(list.get(i).getApplicant());
				if(jsmsUserApplicant != null){
					jsmsAgentInvoiceListDTO.setApplicantStr(jsmsUserApplicant.getRealname());
				}
				jsmsAgentInvoiceListDTO.setInvoiceProject("信息服务费");
				jsmsAgentInvoiceListDTOList.add(jsmsAgentInvoiceListDTO);
				BigDecimal invoiceAmount = list.get(i).getInvoiceAmount();
				jsmsAgentInvoiceListDTO.setInvoiceAmountStr(invoiceAmount.setScale(2, BigDecimal.ROUND_DOWN).toString());
			}
		}
		return jsmsAgentInvoiceListDTOList;
	}

	public Map<String,Object> getagentIdsAndBelongSales(Object belongSale,Object agentId){
		Set<Integer> agentIds = new HashSet<>();
		Set<Integer> belongSales = new HashSet<>();
		Map<String,Object> map = new HashMap<>();
		if(belongSale!=null&&StringUtils.isNotBlank(belongSale.toString())){
			List<JsmsAgentInfo> jsmsAgentInfoListBelongSale = jsmsAgentInfoService.getByBelongSale(Long.parseLong(belongSale.toString()));
			if(jsmsAgentInfoListBelongSale!=null && jsmsAgentInfoListBelongSale.size()>0){
				for(int i=0;i<jsmsAgentInfoListBelongSale.size();i++){
					belongSales.add(jsmsAgentInfoListBelongSale.get(i).getAgentId());
				}
			}else{
				belongSales.add(-1);
			}
		}
		if(agentId!=null&&StringUtils.isNotBlank(agentId.toString())){
			List<JsmsAgentInfo> jsmsAgentInfoList = jsmsAgentInfoService.getByAgentName(agentId.toString());
			if(jsmsAgentInfoList!=null && jsmsAgentInfoList.size()>0){
				for(int i=0;i<jsmsAgentInfoList.size();i++){
					agentIds.add(jsmsAgentInfoList.get(i).getAgentId());
				}
			}else{
				agentIds.add(-1);
			}
		}
		map.put("agentIds",agentIds);
		map.put("belongSales",belongSales);
		return map;
	}
	public JsmsAgentInvoiceListDTO getMessage(Integer agengId,Long auditor,JsmsAgentInvoiceListDTO jsmsAgentInvoiceListDTO){
		JsmsAgentInfo jsmsAgentInfo=jsmsAgentInfoService.getByAgentId(agengId);
		if(jsmsAgentInfo!=null){
			jsmsAgentInvoiceListDTO.setName(jsmsAgentInfo.getAgentName());
			JsmsUser jsmsUser = jsmsUserService.getById2(jsmsAgentInfo.getBelongSale());
			if(jsmsUser!=null){
				jsmsAgentInvoiceListDTO.setBelongSaleStr(jsmsUser.getRealname());
			}
		}
		JsmsUser jsmsUserAuditor = jsmsUserService.getById2(auditor);
		if(jsmsUserAuditor != null && !jsmsAgentInvoiceListDTO.getStatus().equals(InvoiceStatusEnum.待审核.getValue())){
			jsmsAgentInvoiceListDTO.setAuditorStr(jsmsUserAuditor.getRealname());
		}
		return jsmsAgentInvoiceListDTO;
	}

	@Override
	public JsmsAgentInvoiceConfig findListAdd(Integer agingId){
		if(agingId==null||agingId==0){
			return null;
		}
		JsmsAgentInfo model=new JsmsAgentInfo();
		model.setAgentId(agingId);
		model.setAgentType(AgentType.OEM代理商.getValue());
		List<JsmsAgentInfo> jsmsAgentInfo=jsmsAgentInfoMapper.findList(model);
		if(CollectionUtils.isEmpty(jsmsAgentInfo)){
			return null;
		}
		Map<String,Object> params=new HashMap<>();
		params.put("agingId",agingId);
		params.put("invoiceType",InvoiceTypeEnum.增值税专票.getValue());//普票
		params.put("status",InvoiceConfigStatus.正常.getValue());
        if(this.jsmsAgentInvoiceConfigMapper.findList(params).size()>=1){
            return this.jsmsAgentInvoiceConfigMapper.findList(params).get(0);
        }else {
            return null;
        }
	}
	@Override
	public JsmsAgentInvoiceConfig findListNomal(Integer agingId){
		if(agingId==null||agingId==0){
			return null;
		}
		JsmsAgentInfo model=new JsmsAgentInfo();
		model.setAgentId(agingId);
		model.setAgentType(AgentType.OEM代理商.getValue());
		List<JsmsAgentInfo> jsmsAgentInfo=jsmsAgentInfoMapper.findList(model);
		if(CollectionUtils.isEmpty(jsmsAgentInfo)){
			return null;
		}
		Map<String,Object> params=new HashMap<>();
		params.put("agingId",agingId);
		params.put("invoiceType",InvoiceTypeEnum.普通发票.getValue());//增值税发票
		params.put("status",InvoiceConfigStatus.正常.getValue());
		if(this.jsmsAgentInvoiceConfigMapper.findList(params).size()>=1){
            return this.jsmsAgentInvoiceConfigMapper.findList(params).get(0);
        }else {
		    return null;
        }


	}

}
