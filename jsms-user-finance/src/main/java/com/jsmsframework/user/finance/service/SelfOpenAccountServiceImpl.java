package com.jsmsframework.user.finance.service;

import com.alibaba.fastjson.JSON;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsEmailAlarmSetting;
import com.jsmsframework.common.entity.JsmsParam;
import com.jsmsframework.common.entity.JsmsSendEmailList;
import com.jsmsframework.common.enums.*;
import com.jsmsframework.common.service.JsmsEmailAlarmSettingService;
import com.jsmsframework.common.service.JsmsEmailService;
import com.jsmsframework.common.service.JsmsParamService;
import com.jsmsframework.common.service.JsmsSendEmailListService;
import com.jsmsframework.common.util.CheckEmail;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.RegexUtils;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.entity.JsmsAgentBalanceBill;
import com.jsmsframework.finance.entity.JsmsClientBalanceAlarm;
import com.jsmsframework.finance.exception.JsmsClientBalanceAlarmException;
import com.jsmsframework.finance.service.JsmsAgentAccountService;
import com.jsmsframework.finance.service.JsmsAgentBalanceBillService;
import com.jsmsframework.finance.service.JsmsClientBalanceAlarmService;
import com.jsmsframework.finance.service.JsmsTaskAlarmSettingService;
import com.jsmsframework.order.entity.JsmsOemAgentOrder;
import com.jsmsframework.order.entity.JsmsOemAgentPool;
import com.jsmsframework.order.entity.JsmsOemClientOrder;
import com.jsmsframework.order.entity.JsmsOemClientPool;
import com.jsmsframework.order.enums.OEMClientOrderType;
import com.jsmsframework.order.service.JsmsOemAgentOrderService;
import com.jsmsframework.order.service.JsmsOemAgentPoolService;
import com.jsmsframework.order.service.JsmsOemClientOrderService;
import com.jsmsframework.order.service.JsmsOemClientPoolService;
import com.jsmsframework.product.entity.JsmsOemProductInfo;
import com.jsmsframework.product.service.JsmsOemProductInfoService;
import com.jsmsframework.user.entity.*;
import com.jsmsframework.user.exception.JsmsAccountException;
import com.jsmsframework.user.exception.JsmsClientInfoExtException;
import com.jsmsframework.user.exception.JsmsOauthPicException;
import com.jsmsframework.user.exception.JsmsUserPropertyLogException;
import com.jsmsframework.user.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户中心-自助服务管理实现类
 *
 * @outhor tanjiangqiang
 * @create 2017-11-23 17:35
 */
@Service
public class SelfOpenAccountServiceImpl implements SelfOpenAccountService {

    private static final Logger logger = LoggerFactory.getLogger(SelfOpenAccountServiceImpl.class);

    private static final String DEFAULT_IDENTIFY = "DEFAULT_IDENTIFY";
    private static final Integer IDENTIFY = 7;
    //默认邮箱发件人地址
    private static final String form = "admin@ucpaas.com";

    @Autowired
    private JsmsClientidSequenceService jsmsClientidSequenceService;

    @Autowired
    private JsmsParamService jsmsParamService;

    @Autowired
    private JsmsAccountService jsmsAccountService;

    @Autowired
    private JsmsClientInfoExtService jsmsClientInfoExtService;

    @Autowired
    private JsmsOauthPicService jsmsOauthPicService;

    @Autowired
    private JsmsOauthAuditLogService jsmsOauthAuditLogService;

    @Autowired
    private JsmsUserPropertyLogService jsmsUserPropertyLogService;

    @Autowired
    private JsmsClientBalanceAlarmService jsmsClientBalanceAlarmService;

    @Autowired
    private JsmsEmailAlarmSettingService jsmsEmailAlarmSettingService;

    @Autowired
    private JsmsUserService jsmsUserService;

    @Autowired
    private JsmsTaskAlarmSettingService jsmsTaskAlarmSettingService;

    @Autowired
    private JsmsAgentInfoService jsmsAgentInfoService;

    @Autowired
    private JsmsOemDataConfigService jsmsOemDataConfigService;

    @Autowired
    private JsmsOemProductInfoService jsmsOemProductInfoService;

    @Autowired
    private JsmsAgentAccountService jsmsAgentAccountService;

    @Autowired
    private JsmsAgentBalanceBillService jsmsAgentBalanceBillService;

    @Autowired
    private JsmsOemAgentPoolService jsmsOemAgentPoolService;

    @Autowired
    private JsmsOemAgentOrderService jsmsOemAgentOrderService;

    @Autowired
    private JsmsOemClientPoolService jsmsOemClientPoolService;

    @Autowired
    private JsmsOemClientOrderService jsmsOemClientOrderService;

    @Autowired
    private JsmsSendEmailListService jsmsSendEmailListService;

    @Autowired
    private JsmsEmailService jsmsEmailService;

    @Override
    public JsmsAccount addUser(JsmsAccount jsmsAccountPo, JsmsOauthPic oauthPicPo, Long adminId, String remindMailBoby, String remindMailSubject, Long buyAgentOrderId, Long distributeAgentOrderId, Long oemClientOrderId) {
        logger.debug("用户中心自助开户-------------检查参数(邮件和手机号是否已经被注册)开始");

        String email = jsmsAccountPo.getEmail();
        String mobile = jsmsAccountPo.getMobile();
        if (StringUtils.isNotEmpty(email)) {
            if (!CheckEmail.checkEmail(email)) {
                logger.error("邮箱格式错误" + JsonUtil.toJson(email));
                throw new IllegalArgumentException("请输入正确邮箱");
            }
            JsmsPage<JsmsAccount> accountPage = new JsmsPage();
            accountPage.getParams().put("email", email);
            JsmsPage<JsmsAccount> jsmsPage = jsmsAccountService.queryList(accountPage);
            if (!jsmsPage.getData().isEmpty()) {
                logger.debug("邮箱已经被注册，方法结束======================================");
                throw new JsmsAccountException("邮箱已经被注册");
            }
        }
        if (StringUtils.isNotEmpty(mobile)) {
            if (!RegexUtils.isMobile(mobile)) {
                logger.error("手机号码格式错误" + JsonUtil.toJson(mobile));
                throw new IllegalArgumentException("请输入正确手机号码");
            }
            JsmsPage<JsmsAccount> accountPage = new JsmsPage();
            accountPage.getParams().put("mobile", mobile);
            JsmsPage<JsmsAccount> jsmsPage = jsmsAccountService.queryList(accountPage);
            if (!jsmsPage.getData().isEmpty()) {
                logger.debug("手机已经被注册，方法结束======================================");
                throw new JsmsAccountException("手机已经被注册");
            }
        }
        logger.debug("检查参数(邮件和手机号是否已经被注册)结束======================================");

        // 获取代理商信息
        JsmsAgentInfo jsmsAgentInfo = jsmsAgentInfoService.getByAgentId(jsmsAccountPo.getAgentId());

        // 添加主键,32位随机字符串
        jsmsAccountPo.setId(UUID.randomUUID().toString().replace("-", ""));
        /**
         * 添加用户id(从公用序列中取,如果没有则生成后再取)
         */
        String Clientid = jsmsClientidSequenceService.getOrAddId();
        if (StringUtils.isEmpty(Clientid)) {
            logger.error("生成Clientid失败，方法结束======================================");
            throw new JsmsAccountException("服务器异常,正在检修中...");
        }
        jsmsAccountPo.setClientid(Clientid);
        // 初始密码(随机八位数字和字母组合)
        jsmsAccountPo.setPassword(UUID.randomUUID().toString().replace("-", "").substring(4, 12));
        //用户真实姓名
        jsmsAccountPo.setRealname(jsmsAccountPo.getName());
        // 付费类型，0：预付费，1：后付费；默认是0
        jsmsAccountPo.setPaytype(PayType.预付费.getValue());
        // 短信协议类型  默认为http json协议
        jsmsAccountPo.setSmsfrom(SmsFrom.HTTPS.getValue());
        //用户等级，1：普通客户（6－8位用户扩展），2：中小企业大型企业（4－5位用户扩展）(默认值)，3：大型企业（2－3位用户扩展）
        if (null == jsmsAccountPo.getClientLevel()) {
            jsmsAccountPo.setClientLevel(2);
        }
        //是否需要审核，0：不需要，1：营销需要，2：全部需要，3：关键字审核(默认值)
        if (null == jsmsAccountPo.getNeedaudit()) {
            jsmsAccountPo.setNeedaudit(3);
        }
        // 是否支持签名对应签名端口，0：不支持，1：支持',
        if (null == jsmsAccountPo.getSignextend()) {
            jsmsAccountPo.setSignextend(0);
        }
        // 是否超频计费，0：不需要，1：需要',
        if (null == jsmsAccountPo.getIsoverratecharge()) {
            jsmsAccountPo.setIsoverratecharge(0);
        }
        //用户类型 1：个人用户(默认值)，2：企业用户
        if (null == jsmsAccountPo.getClientType()) {
            jsmsAccountPo.setClientType(1);
        }
        // 验证IP（可以有多个，用逗号分隔：192.168.0.*，*，192.168.0.0/16   (如果未设置，则默认值*)
        if (null == jsmsAccountPo.getIp()) {
            jsmsAccountPo.setIp("*");
        }
        // 所属销售(如果未设置，则默认值为代理商所属销售)
        if (null == jsmsAccountPo.getBelongSale()) {
            jsmsAccountPo.setBelongSale(jsmsAgentInfo.getBelongSale());
        }
        // 标识，取值范围【0,9】 在配置文件中配置
        if (null == jsmsAccountPo.getIdentify()) {
            List<JsmsParam> paramResult = jsmsParamService.getByParamKey(DEFAULT_IDENTIFY);
            if (!paramResult.isEmpty()) {
                jsmsAccountPo.setIdentify(Integer.valueOf(paramResult.get(0).getParamValue()));
            } else {
                jsmsAccountPo.setIdentify(IDENTIFY);
            }
        }
        //extendport(用户端口)
//        if (StringUtils.isEmpty(jsmsAccountPo.getExtendtype().toString()) || StringUtils.isEmpty(jsmsAccountPo.getExtendport())) {
//            logger.error("用户端口或扩展类型未设置，方法结束======================================");
//            new RuntimeException("服务器异常,正在检修中...");
//        }
        //创建时间
        jsmsAccountPo.setCreatetime(new Date());

        /**
         * 判断使用对象 1为自己使用
         *1、使用对象为“自己使用”的时候，点击确定保存之后自动复制一份代理商的资质到子账户中；
         * 如果该代理商没有经过资质认证的话，点击确定时候，弹出提示“请先对代理商进行认证”。
         *2、使用对象为“下级客户”的时候，点击确定之后，弹出开户信息和资质认证。
         */
        if (AgentOwned.代理商自己使用.getValue().equals(jsmsAccountPo.getAgentOwned())) {
            jsmsAccountPo.setOauthStatus(jsmsAgentInfo.getOauthStatus());
            //自己使用默认填写认证时间为当前时间
            jsmsAccountPo.setOauthDate(new Date());
            //自己使用默认填写代理商地址
            jsmsAccountPo.setAddress(jsmsAgentInfo.getAddress());
            //自动复制一份代理商的资质到子账户中；
            JsmsPage<JsmsOauthPic> oauthPicPage = new JsmsPage<>();
            oauthPicPage.getParams().put("agentId", jsmsAccountPo.getAgentId());
            oauthPicPage.getParams().put("oauthType", "1");
            oauthPicPage.getParams().put("status", "1");
            //获取代理商资质认证信息
            JsmsPage OauthPicResult = jsmsOauthPicService.queryList(oauthPicPage);
            if (OauthPicResult.getData().isEmpty()) {
                logger.error("未查询到代理商资质认证信息======================================");
                throw new JsmsOauthPicException("未查询到代理商资质认证信息");
            }
            //获取该代理商的第一条认证信息
            JsmsOauthPic agentOauthPic = (JsmsOauthPic) OauthPicResult.getData().get(0);
            agentOauthPic.setId(null);
            agentOauthPic.setClientId(jsmsAccountPo.getClientid());
            agentOauthPic.setOauthType(2);
            agentOauthPic.setCreateDate(new Date());
            agentOauthPic.setUpdateDate(new Date());
            int n = jsmsOauthPicService.insert(agentOauthPic);
            if (n != 1) {
                logger.error("复制代理商的资质到子账户失败================"+JsonUtil.toJson(agentOauthPic));
                throw new JsmsOauthPicException("新增代理商的资质到子账户失败"+JsonUtil.toJson(agentOauthPic));
            }

            logger.debug("复制一份代理商的资质到子账户中成功======================================" + JsonUtil.toJson(agentOauthPic));
            //使用对象为自己使用的时候，完成开户马上赠送条数；
            giveShortMessage(jsmsAccountPo.getClientid(), jsmsAccountPo.getAgentId(), adminId, jsmsAccountPo.getName(), buyAgentOrderId, distributeAgentOrderId, oemClientOrderId);
            logger.debug("设置赠送条数成功======================================");
        } else {
            jsmsAccountPo.setOauthStatus(OauthStatusEnum.待认证.getValue());
            //当使用对象为下级客户的时候，出现资质认证信息！
            if (null == oauthPicPo) {
                logger.error("资质认证信息为空======================================");
                throw new JsmsOauthPicException("请填写资质认证信息");
            }
            if (StringUtils.isEmpty(oauthPicPo.getIdNbr())) {
                logger.error("证件号码为空======================================");
                throw new JsmsOauthPicException("请填写证件号码");
            }
            if (StringUtils.isEmpty(oauthPicPo.getImgUrl())) {
                logger.error("证件图片地址为空======================================");
                throw new JsmsOauthPicException("请上传证件图片");
            }
            // 认证类型： 1、代理商资质认证 2、客户资质认证
            oauthPicPo.setOauthType(2);
            oauthPicPo.setClientId(jsmsAccountPo.getClientid());
            oauthPicPo.setAgentId(jsmsAccountPo.getAgentId());
            oauthPicPo.setCreateDate(new Date());
            oauthPicPo.setUpdateDate(new Date());
            int n = jsmsOauthPicService.insert(oauthPicPo);
            if (n != 1) {
                throw new JsmsOauthPicException("自助开户，增加子客户资质认证信息================"+JsonUtil.toJson(oauthPicPo));
            }
            logger.debug("增加子客户资质认证信息===================================" + JsonUtil.toJson(oauthPicPo));
            //当成功开户后，会生成一条资质审核记录
            JsmsOauthAuditLog jsmsOauthAuditLog = new JsmsOauthAuditLog();
            jsmsOauthAuditLog.setAgentId(jsmsAccountPo.getAgentId());
            jsmsOauthAuditLog.setClientId(jsmsAccountPo.getClientid());
            jsmsOauthAuditLog.setAdminId(adminId);
            // 审核类型，1：代理商认证，2：客户认证
            jsmsOauthAuditLog.setAuditType(2);
            jsmsOauthAuditLog.setStatus(0);
            jsmsOauthAuditLog.setCreateDate(new Date());
            jsmsOauthAuditLog.setRemark("下级客户资质审核");
            n = jsmsOauthAuditLogService.insert(jsmsOauthAuditLog);
            if (n != 1) {
                throw new JsmsUserPropertyLogException("自助开户，新增资质审核记录失败================"+JsonUtil.toJson(jsmsOauthAuditLog));
            }
            logger.debug("增加子客户资质审核记录===================================" + JsonUtil.toJson(jsmsOauthAuditLog));
            //使用对象为下级客户的时候，需要资质认证通过之后再赠送条数。
        }

        //4、自助开户的计费规则默认按提交量计费 增加t_sms_user_property_log中的clientid记录
        JsmsUserPropertyLog jsmsUserPropertyLog = new JsmsUserPropertyLog();
        jsmsUserPropertyLog.setClientid(jsmsAccountPo.getClientid());
        jsmsUserPropertyLog.setProperty("charge_rule");
        jsmsUserPropertyLog.setValue(ChargeRuleType.提交量.getValue().toString());
        jsmsUserPropertyLog.setEffectDate(new Date());
        jsmsUserPropertyLog.setOperator(adminId);
        jsmsUserPropertyLog.setCreateTime(new Date());
        int n = jsmsUserPropertyLogService.insert(jsmsUserPropertyLog);
        if (n != 1) {
            throw new JsmsUserPropertyLogException("新增自助开户的计费规则失败================"+JsonUtil.toJson(jsmsUserPropertyLog));
        }
        logger.debug("新增子客户开户的计费规则默认按提交量计费======================================" + JsonUtil.toJson(jsmsUserPropertyLog));

        logger.debug("插入账户数据及更新clientid序列状态开始======================================" + JsonUtil.toJson(jsmsAccountPo));
        int saveNum = jsmsAccountService.insert(jsmsAccountPo);
        if (saveNum != 1) {
            logger.error("插入用户信息t_sms_account失败======================================" + JsonUtil.toJson(jsmsAccountPo));
            throw new RuntimeException("服务器异常,正在检修中...");
        }
        boolean updateClientIdStatus = jsmsClientidSequenceService.updateClientIdStatus(jsmsAccountPo.getClientid());
        if (!updateClientIdStatus) {
            logger.error("更新序列状态失败======================================" + JsonUtil.toJson(jsmsAccountPo));
            throw new RuntimeException("服务器异常,正在检修中...");
        }
        /*if (saveNum != 1 && updateClientIdStatus) {
            logger.error("插入用户信息和更新序列状态失败======================================" + JsonUtil.toJson(jsmsAccountPo));
            throw new RuntimeException("服务器异常,正在检修中...");
        }*/
        logger.debug("插入账户数据及更新clientid序列状态结束======================================");

        logger.debug("插入用户信息扩展表开始======================================");
        JsmsClientInfoExt clientInfoExt = new JsmsClientInfoExt();
        clientInfoExt.setClientid(Clientid);
        // 更新者,对应t_sms_user表中id字段
        clientInfoExt.setUpdator(adminId);
        clientInfoExt.setUpdateDate(new Date());
        clientInfoExt.setWebPassword(jsmsAccountPo.getPassword());
        int addExtCount = jsmsClientInfoExtService.insert(clientInfoExt);
        if (addExtCount != 1) {
            logger.error("插入扩展失败======================================" + JsonUtil.toJson(clientInfoExt));
            throw new JsmsClientInfoExtException("服务器异常,正在检修中...");
        }


        logger.debug("插入用户信息扩展表结束======================================");
        /**
         * 账户的提醒设置默认为验证码500条、通知500条、营销500条、国际10元，提醒手机号码为用户中心手机号码和该子账户提醒号码。
         */
        JsmsUser user = jsmsUserService.getById(jsmsAgentInfo.getAdminId().toString());
        JsmsClientBalanceAlarm jsmsClientBalanceAlarm = new JsmsClientBalanceAlarm();
        jsmsClientBalanceAlarm.setClientid(jsmsAccountPo.getClientid());
        if (StringUtils.isNotEmpty(mobile)) {
            jsmsClientBalanceAlarm.setAlarmPhone(user.getMobile() + "," + mobile);
        } else {
            jsmsClientBalanceAlarm.setAlarmPhone(user.getMobile());
        }
        if (StringUtils.isNotEmpty(email)) {
            jsmsClientBalanceAlarm.setAlarmEmail(user.getEmail() + "," + email);
        } else {
            jsmsClientBalanceAlarm.setAlarmEmail(user.getEmail());
        }
        clientBalanceAlarm(jsmsClientBalanceAlarm);
        logger.debug("提醒设置默认为验证码500条、通知500条、营销500条、国际10元成功======================================" + JsonUtil.toJson(jsmsClientBalanceAlarm));

//            logger.debug("给客户发送邮件开始======================================");
//            // 发送开户邮件到邮箱
//            customerMailBoby = customerMailBoby.replace("vpassword", jsmsAccountPo.getPassword());
//            customerMailBoby = customerMailBoby.replace("agent_name", jsmsAgentInfo.getAgentName());
//            customerMailBoby = customerMailBoby.replace("agent_mobile", jsmsAgentInfo.getMobile());
//
//            boolean sendEmail = EmailUtils.sendHtmlEmail(jsmsAccountPo.getEmail(), customerMailSubject, customerMailBoby);
//            logger.debug("给客户发送邮件结束======================================" + sendEmail);

        //发送邮箱到归属销售、产品运营、通道运营、审批客服
        sendEmail(jsmsAccountPo,remindMailBoby,remindMailSubject);

        return jsmsAccountPo;
    }

    /**
     * @param clientId
     * @param agentId
     * @param adminId
     * @param realName
     * @Description: 测试短信赠送
     * @Author: tanjiangqiang
     * @Date: 2017/11/24 - 14:18
     */
    private void giveShortMessage(String clientId, Integer agentId, Long adminId, String realName, Long buyAgentOrderId, Long distributeAgentOrderId, Long oemClientOrderId) {

        Date now = new Date();

        logger.debug("用户中心-自助开户赠送短信，客户id为-----------{}" + clientId);
        JsmsAgentInfo jsmsAgentInfo = jsmsAgentInfoService.getByAgentId(agentId);
        // 品牌代理/销售代理的客户,不赠送测试短信(只有OEM代理商赠送短信)
        int agentType = jsmsAgentInfo.getAgentType();
        if (agentType != 5) {
            logger.error("代理商类型为--->{}，不是oem代理商，不能赠送短信", agentType);
            return;
//            throw new RuntimeException("赠送短信失败，不是oem代理商，不能赠送短信");
        }
//        // 不赠送短信产品
        JsmsOemDataConfig jsmsOemDataConfig = jsmsOemDataConfigService.getByAgentId(agentId);
        if (null == jsmsOemDataConfig || null == jsmsOemDataConfig.getId()) {
            logger.error("代理商：{}------->没有对应的oem资料", JsonUtil.toJson(jsmsOemDataConfig));
            return;
//            throw new RuntimeException("赠送短信失败，没有对应的oem代理商资料");
        }
        Integer testSmsNumber = jsmsOemDataConfig.getTestSmsNumber();
        if (null == jsmsOemDataConfig.getTestProductId() || null == testSmsNumber || "0".equals(testSmsNumber.toString())) {
            logger.error("测试短信产品id号为空、或者赠送的测试短信条数为空", JsonUtil.toJson(jsmsOemDataConfig));
            return;
//            throw new RuntimeException("赠送短信失败，测试短信条数为空");
        }
        JsmsOemProductInfo jsmsOemProductInfo = jsmsOemProductInfoService.getByProductId(jsmsOemDataConfig.getTestProductId());

        BigDecimal unitPrice = jsmsOemProductInfo.getUnitPrice();
        Integer productType = jsmsOemProductInfo.getProductType();
        Date duetime = jsmsOemProductInfo.getDueTime();
        Integer productId = jsmsOemProductInfo.getProductId();
        String productCode = jsmsOemProductInfo.getProductCode();
        String productName = jsmsOemProductInfo.getProductName();


        int status = jsmsOemProductInfo.getStatus();
        if (status != 1) {
            logger.error("产品id:{}--------->为待上架或者已下架", JsonUtil.toJson(jsmsOemDataConfig));
//            throw new RuntimeException("赠送短信失败，产品已下架");
        }
        // 满足赠送短信的条件
        logger.debug("满足赠送短信的条件=============================================================");
        JsmsAgentAccount jsmsAgentAccount = jsmsAgentAccountService.getByAgentId(agentId);
        BigDecimal balance = jsmsAgentAccount.getBalance();

        BigDecimal bgTestNum = new BigDecimal(testSmsNumber);
        BigDecimal bgAmount = bgTestNum.multiply(unitPrice);

        BigDecimal newBgBalance = balance.add(bgAmount);
        // 生成余额账单(代理商入账)
        JsmsAgentBalanceBill jsmsAgentBalanceBill = new JsmsAgentBalanceBill();
        jsmsAgentBalanceBill.setAgentId(agentId);
        jsmsAgentBalanceBill.setPaymentType(5);
        jsmsAgentBalanceBill.setFinancialType("0");
        jsmsAgentBalanceBill.setAmount(bgAmount);
        jsmsAgentBalanceBill.setBalance(newBgBalance);
        jsmsAgentBalanceBill.setCreateTime(now);
        jsmsAgentBalanceBill.setAdminId(adminId);
        jsmsAgentBalanceBill.setClientId(clientId);
        jsmsAgentBalanceBill.setRemark("赠送短信充值");
        int i = jsmsAgentBalanceBillService.insert(jsmsAgentBalanceBill);
        if (i <= 0) {
            logger.error("赠送短信失败，生成余额入账账单失败" + JsonUtil.toJson(jsmsAgentBalanceBill));
            throw new RuntimeException("赠送短信失败，生成余额入账账单失败");
        }
//
        // 判断OEM代理商短信池(t_sms_oem_agent_pool)是否存在记录(获取agent_pool_id)
        Long agent_pool_id = null;
        JsmsOemAgentPool jsmsOemAgentPool = new JsmsOemAgentPool();
        jsmsOemAgentPool.setAgentId(agentId);
        jsmsOemAgentPool.setProductType(productType);
        jsmsOemAgentPool.setDueTime(duetime);
        jsmsOemAgentPool.setUnitPrice(unitPrice);
        jsmsOemAgentPool.setStatus(0);
        jsmsOemAgentPool.setOperatorCode(0);
        jsmsOemAgentPool.setAreaCode(0);
        JsmsOemAgentPool jsmsOemAgentPoolResult = jsmsOemAgentPoolService.getByAgentPoolInfo(jsmsOemAgentPool);
        if (null != jsmsOemAgentPoolResult && null != jsmsOemAgentPoolResult.getAgentPoolId()) {
            agent_pool_id = jsmsOemAgentPoolResult.getAgentPoolId();
        } else {
            JsmsOemAgentPool jsmsOemAgentPoolVo = new JsmsOemAgentPool();
            jsmsOemAgentPoolVo.setAgentId(agentId);
            jsmsOemAgentPoolVo.setProductType(productType);
            jsmsOemAgentPoolVo.setDueTime(duetime);
            jsmsOemAgentPoolVo.setStatus(0);
            jsmsOemAgentPoolVo.setRemainNumber(0);
            jsmsOemAgentPoolVo.setUnitPrice(unitPrice);
            jsmsOemAgentPoolVo.setUpdateTime(now);
            int j = jsmsOemAgentPoolService.insert(jsmsOemAgentPoolVo);
            if (j <= 0) {
                logger.error("赠送短信失败，生成代理商短信池记录失败！---------{}" + JsonUtil.toJson(jsmsOemAgentPoolVo));
                throw new RuntimeException("赠送短信失败，生成代理商短信池记录失败！");
            }
            agent_pool_id = jsmsOemAgentPoolVo.getAgentPoolId();
        }
        JsmsOemAgentOrder jsmsOemAgentOrder = new JsmsOemAgentOrder();
        jsmsOemAgentOrder.setOrderId(buyAgentOrderId);
        jsmsOemAgentOrder.setOrderNo(buyAgentOrderId);
        // 订单类型，0：OEM代理商购买，1：OEM代理商分发，2：OEM代理商回退
        jsmsOemAgentOrder.setOrderType(OEMClientOrderType.OEM代理商购买.getValue());
        jsmsOemAgentOrder.setProductId(productId);
        jsmsOemAgentOrder.setProductCode(productCode);
        jsmsOemAgentOrder.setProductType(productType);
        jsmsOemAgentOrder.setProductName(productName);
        jsmsOemAgentOrder.setUnitPrice(unitPrice);
        jsmsOemAgentOrder.setOrderNumber(testSmsNumber);
        jsmsOemAgentOrder.setOrderAmount(bgAmount);
        jsmsOemAgentOrder.setProductPrice(BigDecimal.ZERO);
        jsmsOemAgentOrder.setAgentId(agentId);
        jsmsOemAgentOrder.setClientId(clientId);
        jsmsOemAgentOrder.setName("云子讯");
        jsmsOemAgentOrder.setAgentPoolId(agent_pool_id);
        jsmsOemAgentOrder.setDueTime(duetime);
        jsmsOemAgentOrder.setCreateTime(now);
        int k = jsmsOemAgentOrderService.insert(jsmsOemAgentOrder);
        if (k <= 0) {
            logger.error("赠送短信失败，生成代理商订单（购买记录）失败！" + JsonUtil.toJson(jsmsOemAgentOrder));
            throw new RuntimeException("赠送短信失败，生成代理商订单（购买记录）失败！");
        }
        // 生成余额账单(代理商出账)
        JsmsAgentBalanceBill jsmsAgentBalanceBillVo = new JsmsAgentBalanceBill();
        jsmsAgentBalanceBillVo.setAgentId(agentId);
        jsmsAgentBalanceBillVo.setOrderId(buyAgentOrderId);
        jsmsAgentBalanceBillVo.setPaymentType(3);
        jsmsAgentBalanceBillVo.setFinancialType("1");
        jsmsAgentBalanceBillVo.setAmount(bgAmount);
        jsmsAgentBalanceBillVo.setBalance(newBgBalance);
        jsmsAgentBalanceBillVo.setCreateTime(now);
        jsmsAgentBalanceBillVo.setAdminId(adminId);
        jsmsAgentBalanceBillVo.setClientId(clientId);
        jsmsAgentBalanceBillVo.setRemark("赠送短信充值");
        int o = jsmsAgentBalanceBillService.insert(jsmsAgentBalanceBillVo);
        if (o <= 0) {
            logger.error("赠送短信失败，生成余额出账账单失败" + JsonUtil.toJson(jsmsAgentBalanceBillVo));
            throw new RuntimeException("赠送短信失败，生成余额出账账单失败");
        }
        // ======================================给客户充值===========================================

        // 生成代理商订单(分发记录)
        JsmsOemAgentOrder jsmsOemAgentOrderVo = new JsmsOemAgentOrder();
        jsmsOemAgentOrderVo.setOrderId(distributeAgentOrderId);
        jsmsOemAgentOrderVo.setOrderNo(distributeAgentOrderId);
        // 订单类型，0：OEM代理商购买，1：OEM代理商分发，2：OEM代理商回退
        jsmsOemAgentOrderVo.setOrderType(OEMClientOrderType.OEM代理商分发.getValue());
        jsmsOemAgentOrderVo.setProductId(productId);
        jsmsOemAgentOrderVo.setProductCode(productCode);
        jsmsOemAgentOrderVo.setProductType(productType);
        jsmsOemAgentOrderVo.setProductName(productName);
        jsmsOemAgentOrderVo.setUnitPrice(unitPrice);
        jsmsOemAgentOrderVo.setOrderNumber(testSmsNumber);
        jsmsOemAgentOrderVo.setOrderAmount(bgAmount);
        jsmsOemAgentOrderVo.setProductPrice(BigDecimal.ZERO);
        jsmsOemAgentOrderVo.setAgentId(agentId);
        jsmsOemAgentOrderVo.setClientId(clientId);
        jsmsOemAgentOrderVo.setName(realName);
        jsmsOemAgentOrderVo.setAgentPoolId(agent_pool_id);
        jsmsOemAgentOrderVo.setDueTime(duetime);
        jsmsOemAgentOrderVo.setCreateTime(now);
        int n = jsmsOemAgentOrderService.insert(jsmsOemAgentOrderVo);
        if (n <= 0) {
            logger.error("赠送短信失败，生成代理商订单（分发记录）失败！" + JsonUtil.toJson(jsmsOemAgentOrderVo));
            throw new RuntimeException("赠送短信失败，生成代理商订单（分发记录）失败！");
        }
        // 判断oem客户短信池是否存在记录(获取client_pool_id)

        // 判断OEM代理商子客户短信池(client_pool_id)是否存在记录(client_pool_id)
        Long client_pool_id = null;
        JsmsOemClientPool jsmsOemClientPool = new JsmsOemClientPool();
        jsmsOemClientPool.setClientId(clientId);
        jsmsOemClientPool.setProductType(productType);
        jsmsOemClientPool.setDueTime(duetime);
        jsmsOemClientPool.setUnitPrice(unitPrice);
        // 状态，0：正常，1：停用
        jsmsOemClientPool.setStatus(0);
        //运营商
        jsmsOemClientPool.setOperatorCode(0);
        // 区域
        jsmsOemClientPool.setAreaCode(0);
        JsmsOemClientPool jsmsOemClientPoolResult = jsmsOemClientPoolService.getByClientPoolInfo(jsmsOemClientPool);
        if (null != jsmsOemClientPoolResult && null != jsmsOemClientPoolResult.getClientPoolId()) {
            client_pool_id = jsmsOemClientPoolResult.getClientPoolId();
            JsmsOemClientPool JsmsOemClientPool = new JsmsOemClientPool();
            JsmsOemClientPool.setClientPoolId(client_pool_id);
            JsmsOemClientPool.setRemainTestNumber(testSmsNumber);
            int j = jsmsOemClientPoolService.update(JsmsOemClientPool);
            if (j <= 0) {
                logger.error("赠送短信失败，更新客户短信池的测试条数失败！" + JsonUtil.toJson(JsmsOemClientPool));
                throw new RuntimeException("赠送短信失败，更新客户短信池的测试条数失败！");
            }
        } else {
            JsmsOemClientPool JsmsOemClientPool = new JsmsOemClientPool();
            JsmsOemClientPool.setClientId(clientId);
            JsmsOemClientPool.setProductType(productType);
            JsmsOemClientPool.setDueTime(duetime);
            JsmsOemClientPool.setStatus(0);
            JsmsOemClientPool.setTotalNumber(testSmsNumber);
            JsmsOemClientPool.setRemainNumber(testSmsNumber);
            JsmsOemClientPool.setUnitPrice(unitPrice);
            JsmsOemClientPool.setTotalAmount(BigDecimal.ZERO);
            JsmsOemClientPool.setRemainTestNumber(testSmsNumber);
            JsmsOemClientPool.setUpdateTime(now);
            int p = jsmsOemClientPoolService.insert(JsmsOemClientPool);
            if (p <= 0) {
                logger.error("赠送短信失败，生成客户短信池失败！");
                throw new RuntimeException("赠送短信失败，生成客户短信池失败！");
            }
            client_pool_id = JsmsOemClientPool.getClientPoolId();
        }
        JsmsOemClientOrder jsmsOemClientOrder = new JsmsOemClientOrder();
        jsmsOemClientOrder.setOrderId(oemClientOrderId);
        jsmsOemClientOrder.setOrderNo(oemClientOrderId);
        jsmsOemClientOrder.setProductType(productType);
        jsmsOemClientOrder.setOrderType(1);
        jsmsOemClientOrder.setOrderNumber(testSmsNumber);
        jsmsOemClientOrder.setUnitPrice(unitPrice);
        jsmsOemClientOrder.setOrderPrice(bgAmount);
        jsmsOemClientOrder.setClientId(clientId);
        jsmsOemClientOrder.setAgentId(agentId);
        jsmsOemClientOrder.setClientPoolId(client_pool_id);
        jsmsOemClientOrder.setDueTime(duetime);
        jsmsOemClientOrder.setCreateTime(now);
        int q = jsmsOemClientOrderService.insert(jsmsOemClientOrder);
        if (q <= 0) {
            logger.error("赠送短信失败，生成oem客户订单失败！" + JsonUtil.toJson(jsmsOemClientOrder));
            throw new RuntimeException("赠送短信失败，生成oem客户订单失败！");
        }
    }

    /**
     * @param jsmsAccountPo     子客户信息
     * @param remindMailBoby    提醒邮箱内容
     * @param remindMailSubject 提醒邮箱主题
     * @Description: 自助开户-发送邮箱到归属销售、产品运营、通道运营、审批客服
     * @Author: tanjiangqiang
     * @Date: 2017/12/12 - 13:58
     */
    public boolean sendEmail(JsmsAccount jsmsAccountPo, String remindMailBoby, String remindMailSubject) {
        logger.debug("完成开户后自动发送邮件到归属销售、产品运营、通道运营、审批客服开始======================================");

        JsmsAgentInfo jsmsAgentInfo = jsmsAgentInfoService.getByAgentId(jsmsAccountPo.getAgentId());

        //查询所属销售、产品运营、通道运营、审批客服等邮箱
        JsmsPage emailAlarmSettingPage = new JsmsPage();
        emailAlarmSettingPage.getParams().put("webId", WebId.OEM代理商平台.getValue());
        emailAlarmSettingPage.getParams().put("status", StatusEnum.STATUS_ENABLE.getValue());
        JsmsPage emailAlarmSettingResult = jsmsEmailAlarmSettingService.queryList(emailAlarmSettingPage);
        List<JsmsEmailAlarmSetting> emailAlarmSettingResultList = emailAlarmSettingResult.getData();
        JsmsUser jsmsUser = jsmsUserService.getById(jsmsAgentInfo.getBelongSale().toString());
        StringBuilder to = new StringBuilder(jsmsUser.getEmail());
//            StringBuilder to = new StringBuilder("tanjiangqiang@ucpaas.com");
        if (!emailAlarmSettingResultList.isEmpty()) {
            for (JsmsEmailAlarmSetting emailAlarmSetting : emailAlarmSettingResultList) {
                to.append("," + emailAlarmSetting.getAlarmEmail());
            }
        }

        remindMailBoby = remindMailBoby.replace("agentId", jsmsAgentInfo.getAgentId().toString());
        remindMailBoby = remindMailBoby.replace("agentName", jsmsAgentInfo.getAgentName());
        remindMailBoby = remindMailBoby.replace("clientId", jsmsAccountPo.getClientid());
        remindMailBoby = remindMailBoby.replace("name", jsmsAccountPo.getName());
        remindMailBoby = remindMailBoby.replace("belongSale", jsmsUser.getRealname());
        remindMailBoby = remindMailBoby.replace("email", jsmsAccountPo.getEmail());
        remindMailBoby = remindMailBoby.replace("mobile", jsmsAccountPo.getMobile());
        remindMailBoby = remindMailBoby.replace("remarks", jsmsAccountPo.getRemarks());

        if (PayType.预付费.getValue().toString().equals(jsmsAccountPo.getPaytype().toString())) {
            remindMailBoby = remindMailBoby.replace("paytype", PayType.预付费.getDesc());
        } else {
            remindMailBoby = remindMailBoby.replace("paytype", PayType.后付费.getDesc());
        }

        if (SmsFrom.HTTPS.getValue().toString().equals(jsmsAccountPo.getSmsfrom().toString())) {
            remindMailBoby = remindMailBoby.replace("smsfrom", SmsFrom.HTTPS.getDesc());
        } else if (SmsFrom.CMPP.getValue().toString().equals(jsmsAccountPo.getSmsfrom().toString())) {
            remindMailBoby = remindMailBoby.replace("smsfrom", SmsFrom.CMPP.getDesc());
        } else if (SmsFrom.SGIP.getValue().toString().equals(jsmsAccountPo.getSmsfrom().toString())) {
            remindMailBoby = remindMailBoby.replace("smsfrom", SmsFrom.SGIP.getDesc());
        } else if (SmsFrom.SMGP.getValue().toString().equals(jsmsAccountPo.getSmsfrom().toString())) {
            remindMailBoby = remindMailBoby.replace("smsfrom", SmsFrom.SMGP.getDesc());
        } else if (SmsFrom.SMPP.getValue().toString().equals(jsmsAccountPo.getSmsfrom().toString())) {
            remindMailBoby = remindMailBoby.replace("smsfrom", SmsFrom.SMPP.getDesc());
        }

        if (NeedauditEnum.NEEDREPORT_TRANSPARENT_RANSMISSION.getValue().toString().equals(jsmsAccountPo.getNeedreport().toString())) {
            remindMailBoby = remindMailBoby.replace("needreport", NeedauditEnum.NEEDREPORT_TRANSPARENT_RANSMISSION.getDesc());
        } else if (NeedauditEnum.NEEDREPORT_SIMPLE.getValue().toString().equals(jsmsAccountPo.getNeedreport().toString())) {
            remindMailBoby = remindMailBoby.replace("needreport", NeedauditEnum.NEEDREPORT_SIMPLE.getDesc());
        } else if (NeedauditEnum.NEEDREPORT_USER.getValue().toString().equals(jsmsAccountPo.getNeedreport().toString())) {
            remindMailBoby = remindMailBoby.replace("needreport", NeedauditEnum.NEEDREPORT_USER.getDesc());
        } else {
            remindMailBoby = remindMailBoby.replace("needreport", NeedauditEnum.NEEDREPORT_NO.getDesc());
        }

        if (StringUtils.isEmpty(jsmsAccountPo.getDeliveryurl())) {
            remindMailBoby = remindMailBoby.replace("deliveryurl", "");
        } else {
            remindMailBoby = remindMailBoby.replace("deliveryurl", jsmsAccountPo.getDeliveryurl());
        }

        if (NeedmoEnum.NEEDMO_USER.getValue().toString().equals(jsmsAccountPo.getNeedmo().toString())) {
            remindMailBoby = remindMailBoby.replace("needmo", NeedmoEnum.NEEDMO_USER.getDesc());
        } else if (NeedmoEnum.NEEDMO_WANT.getValue().toString().equals(jsmsAccountPo.getNeedmo().toString())) {
            remindMailBoby = remindMailBoby.replace("needmo", NeedmoEnum.NEEDMO_WANT.getDesc());
        } else {
            remindMailBoby = remindMailBoby.replace("needmo", NeedmoEnum.NEEDMO_NO.getDesc());
        }
        if (StringUtils.isEmpty(jsmsAccountPo.getMourl())) {
            remindMailBoby = remindMailBoby.replace("mourl", "");
        } else {
            remindMailBoby = remindMailBoby.replace("mourl", jsmsAccountPo.getMourl());
        }

        JsmsPage page = new JsmsPage();
        page.getParams().put("webId", WebId.OEM代理商平台.getValue());
        page.getParams().put("status", StatusEnum.STATUS_ENABLE.getValue());
        JsmsPage pageResult = jsmsSendEmailListService.queryList(page);
        List<JsmsSendEmailList> resultData = (List<JsmsSendEmailList>) pageResult.getData();
        boolean sendEmail = false;
        if (!resultData.isEmpty()) {
            //默认获取第一个发送邮箱
            JsmsSendEmailList jsmsSendEmailList = resultData.get(0);
            sendEmail = jsmsEmailService.sendHtmlEmail(jsmsSendEmailList, to.toString(), remindMailSubject, remindMailBoby);
        } else {
            sendEmail = jsmsEmailService.sendHtmlEmail(form, to.toString(), remindMailSubject, remindMailBoby);
        }
        logger.debug("完成开户后自动发送邮件到归属销售、产品运营、通道运营、审批客服结束======================================" + sendEmail);
        return sendEmail;
    }


    /**
      * @Description: 短信余额提醒默认设置
      * @Author: tanjiangqiang
      * @Date: 2017/12/13 - 16:13
      * @param clientBalanceAlarm
      *
      */
    private void clientBalanceAlarm(JsmsClientBalanceAlarm clientBalanceAlarm){
        // 构造验证码
        JsmsClientBalanceAlarm yzm = buildJsmsClientBalanceAlarm(clientBalanceAlarm, 500,
                null, ClientAlarmType.验证码.getValue());
        // 构造通知
        JsmsClientBalanceAlarm tz = buildJsmsClientBalanceAlarm(clientBalanceAlarm, 500,
                null, ClientAlarmType.通知.getValue());
        // 构造营销
        JsmsClientBalanceAlarm yx = buildJsmsClientBalanceAlarm(clientBalanceAlarm, 500,
                null, ClientAlarmType.营销.getValue());
        // 构造国际
        JsmsClientBalanceAlarm gj = buildJsmsClientBalanceAlarm(clientBalanceAlarm, null,
                new BigDecimal(10), ClientAlarmType.国际.getValue());
        List<JsmsClientBalanceAlarm> list = new ArrayList<>();
        list.add(yzm);
        list.add(tz);
        list.add(yx);
        list.add(gj);
        logger.debug("客户余额提醒设置添加 {}", JSON.toJSONString(list));
        int count = jsmsClientBalanceAlarmService.insertBatch(list);
        if (count != list.size()) {
            logger.error("用户中心-自助开户-余额提醒默认设置失败{}",JsonUtil.toJson(list));
            throw new JsmsClientBalanceAlarmException("服务器正在检修...");
        }
    }

    private JsmsClientBalanceAlarm buildJsmsClientBalanceAlarm(JsmsClientBalanceAlarm clientBalanceAlarm, Integer alarmNumber, BigDecimal alarmAmount, int type) {
        if (clientBalanceAlarm == null) {
            return null;
        }
        JsmsClientBalanceAlarm jsmsClientBalanceAlarm = new JsmsClientBalanceAlarm();
        jsmsClientBalanceAlarm.setClientid(clientBalanceAlarm.getClientid());
        jsmsClientBalanceAlarm.setAlarmPhone(clientBalanceAlarm.getAlarmPhone());
        jsmsClientBalanceAlarm.setAlarmEmail(clientBalanceAlarm.getAlarmEmail());
        jsmsClientBalanceAlarm.setAlarmType(type);
        jsmsClientBalanceAlarm.setAlarmNumber(alarmNumber);
        jsmsClientBalanceAlarm.setAlarmAmount(alarmAmount);
        jsmsClientBalanceAlarm.setReminderNumber(0);
        jsmsClientBalanceAlarm.setResetTime(Calendar.getInstance().getTime());
        jsmsClientBalanceAlarm.setCreateTime(jsmsClientBalanceAlarm.getResetTime());
        jsmsClientBalanceAlarm.setUpdateTime(jsmsClientBalanceAlarm.getResetTime());
        return jsmsClientBalanceAlarm;
    }

}