package com.jsmsframework.sms.send.service;

import com.alibaba.fastjson.JSON;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.enums.*;
import com.jsmsframework.common.enums.smsSend.SmsSendFileType;
import com.jsmsframework.common.enums.smsSend.SmsSendStatus;
import com.jsmsframework.common.enums.smsSend.SmsSendSubmitState;
import com.jsmsframework.common.enums.smsSend.TaskSubmitTypeEnum;
import com.jsmsframework.common.util.FileUtils;
import com.jsmsframework.common.util.HttpUtil;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.RegexUtils;
import com.jsmsframework.common.util.file.FileIO;
import com.jsmsframework.finance.entity.JsmsAgentAccount;
import com.jsmsframework.finance.entity.JsmsUserPriceLog;
import com.jsmsframework.finance.service.JsmsAgentAccountService;
import com.jsmsframework.finance.service.JsmsUserPriceLogService;
import com.jsmsframework.order.entity.JsmsOemClientPool;
import com.jsmsframework.order.service.JsmsOemClientPoolService;
import com.jsmsframework.product.entity.JsmsClientTariff;
import com.jsmsframework.product.service.JsmsClientTariffService;
import com.jsmsframework.sms.send.dto.JsmsAccessSmsDTO;
import com.jsmsframework.sms.send.entity.JsmsSubmitProgress;
import com.jsmsframework.sms.send.entity.JsmsTimerSendTask;
import com.jsmsframework.sms.send.po.*;
import com.jsmsframework.sms.send.util.JsmsHttpSend;
import com.jsmsframework.sms.send.util.JsmsParseMobilesFile;
import com.jsmsframework.sms.send.util.JsmsSendParam;
import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.service.JsmsAccountService;
import com.ucpaas.sms.common.util.security.Des3Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JsmsSendServiceImpl implements JsmsSendService {
    private Logger logger = LoggerFactory.getLogger(JsmsSendServiceImpl.class);
    private static final int SMS_SEND_MAX_NUM = 100; // 短信发送最大手机号码数
    private static final int OEM_CLIENT_POOL_STATUS_NORMAL = 0; // 状态，0：正常，1：停用
    private static final int SMS_LENGTH_62 = 62; // 彩印长度62
    private static final int SMS_LENGTH_500 = 500; // 短信内容长度500
    private static final int MAX_IMPORT_LIMIT = 1000000; // 最大导入数量限制


    @Autowired
    private JsmsAgentAccountService jsmsAgentAccountService;
    @Autowired
    private JsmsAccountService jsmsAccountService;
    @Autowired
    private JsmsClientTariffService jsmsClientTariffService;
    @Autowired
    private JsmsOemClientPoolService jsmsOemClientPoolService;
    @Autowired
    private JsmsTimerSendPhonesService jsmsTimerSendPhonesService;
    @Autowired
    private JsmsTimerSendTaskService jsmsTimerSendTaskService;
    @Autowired
    private JsmsUserPriceLogService jsmsUserPriceLogService;
    @Autowired
    private JsmsSendParam jsmsSendParam;
    @Autowired
    private JsmsSubmitProgressService jsmsSubmitProgressService;
    @Autowired
    private JsmsAsyncSend jsmsAsyncSend;

    /**
     * 短信发送
     *
     * @param jsmsAccessSms
     * @param url           短信发送接口, url包含一组大括号, 如:http://sms.ucpaas.com?
     * @return
     */
    @Override
    public ResultVO oemSmsSend(JsmsAccessSms jsmsAccessSms, String url) {
        return commonOemSmsSend(jsmsAccessSms, url);
    }


    private ResultVO commonOemSmsSend(JsmsAccessSms jsmsAccessSms, String url) {
        logger.debug("OEM短信发送的实体信息AccessSms ---------->{}", JsonUtil.toJson(jsmsAccessSms));
        ResultVO result;
        // 短信参数校验
        result = checkSmsParam(jsmsAccessSms);
        if (result != null && result.isFailure()) {
            return result;
        }
        // 用户账号基本信息校验
        JsmsAccount jsmsAccount = jsmsAccountService.getByClientId(jsmsAccessSms.getClientid());
        result = checkAccountBaseInfo(jsmsAccount);
        if (result != null && result.isFailure()) {
            return result;
        }
        // 检查账户余额信息

        String allMobile = jsmsAccessSms.getMobile();
        // 按手机最大发送数量将手机号码分组
        String[] mobileArr = StringUtils.split(allMobile, ",");
        result = checkAccountFianceInfo(jsmsAccount, jsmsAccessSms.getSmstype(), mobileArr, 1);
        if (result != null && result.isFailure()) {
            return result;
        }

        List<String> mobileList = checkAndSplitSmsMobile(mobileArr);
        logger.info("【短信发送】短信分组批量发送开始，分组数量 ={}", mobileList.size());
        int sendSuccessNum = 0;
        int mobileNum = mobileArr.length;
        for (int pos = 0; pos < mobileList.size(); pos++) {
            int groupNum = pos + 1;
            logger.info("----------- 第{}组开始发送----------->", groupNum);
            jsmsAccessSms.setMobile(mobileList.get(pos));
            ResultVO resultVO = invokeHttpToSend(url, jsmsAccessSms);

            if (resultVO != null && resultVO.isFailure()) {
                logger.info("【短信发送失败】第{}组发送失败", groupNum);
            } else {
                if (mobileNum <= SMS_SEND_MAX_NUM) {
                    sendSuccessNum += mobileNum;
                } else {
                    if ((pos + 1) == mobileList.size()) {
                        sendSuccessNum += (mobileNum % SMS_SEND_MAX_NUM);
                    } else {
                        sendSuccessNum += SMS_SEND_MAX_NUM;
                    }
                }
                logger.debug("----------- 第{}组发送成功", groupNum);
            }
        }

        if (sendSuccessNum == mobileNum) {
            result = ResultVO.successDefault("短信提交成功");
        } else if (sendSuccessNum == 0) {
            result = ResultVO.failure("短信提交失败");
        } else {
            StringBuilder msg = new StringBuilder("短信提交成功");
            msg.append(sendSuccessNum)
                    .append("条，提交失败")
                    .append(mobileNum - sendSuccessNum)
                    .append("条");
            result = ResultVO.failure("短信提交成功");
        }
        return result;
    }

    /**
     * 短信发送
     *
     * @param clientid 子账号id
     * @param password 密码,MD5加密
     * @param mobile   手机号
     * @param smstype  短信类型
     * @param content  发送内容
     * @param url      短信发送接口, url包含一组大括号, 如:http://sms.ucpaas.com?
     * @return
     */
    @Override
    public ResultVO oemSmsSend(String clientid, String password, String mobile, String smstype, String content, String url) {
        JsmsAccessSms jsmsAccessSms = new JsmsAccessSms();
        jsmsAccessSms.setClientid(clientid);
        jsmsAccessSms.setPassword(password);
        jsmsAccessSms.setMobile(mobile);
        jsmsAccessSms.setSmstype(smstype);
        jsmsAccessSms.setContent(content);
        return commonOemSmsSend(jsmsAccessSms, url);
    }

    /**
     * 模板短信发送, 不校验账号信息(适用于内部通知及后付费)
     *
     * @param clientid 子账号id
     * @param password 密码,MD5加密
     * @param mobile   手机号
     * @param smstype  短信类型, (可空,空即为通知)
     * @param template 发送模板, 模板中参数需要和List中参数数量一致
     * @param params   模板参数
     * @param url      短信发送接口, url包含一组大括号, 如:http://sms.ucpaas.com/sms/{client}/sending
     * @return
     */
    @Override
    public ResultVO sendTemplateSms(String clientid, String password, String mobile, SmsTypeEnum smstype, String template, List<String> params, String url) {
        if (smstype == null) {
            smstype = SmsTypeEnum.通知;
        }
        JsmsAccessSms jsmsAccessSms = new JsmsAccessSms();
        jsmsAccessSms.setClientid(clientid);
        jsmsAccessSms.setPassword(password);
        jsmsAccessSms.setMobile(mobile);
        jsmsAccessSms.setSmstype(smstype.getValue().toString());
        jsmsAccessSms.setContent(convertTemplate(template, params));
        return invokeHttpToSend(url, jsmsAccessSms);
    }

    @Override
    public ResultVO oemSmsTimSend(JsmsAccessTimerSms jsmsAccessTimerSms, String taskId, String url, Integer chargeNumTotal, boolean bool) {
        ResultVO result;
        int updateFlag = 0;
        // 短信参数校验
        JsmsAccessSms jsmsAccessSms = new JsmsAccessSms();
        jsmsAccessSms.setContent(jsmsAccessTimerSms.getContent());
        jsmsAccessSms.setSmstype(jsmsAccessTimerSms.getSmstype());
        result = checkSmsParam(jsmsAccessSms);
        if (result != null && result.isFailure()) {
            return result;
        }
        // 用户账号基本信息校验
        JsmsAccount jsmsAccount = jsmsAccountService.getByClientId(jsmsAccessTimerSms.getClientid());
        result = checkAccountBaseInfo(jsmsAccount);
        if (result != null && result.isFailure()) {
            return result;
        }
        //定时时间检验
        result = checkSendTime(jsmsAccessTimerSms);
        if (result != null && result.isFailure()) {
            return result;
        }
        if (bool) {
            //校验短信库存量
            result = checkFiance4TimerSend(jsmsAccount, jsmsAccessTimerSms, chargeNumTotal);
            if (result != null && result.isFailure()) {
                return result;
            }
        }
        //调后台接口插入定时短信发送任务信息表和定时短信发送任务关联号码表
        result = invokeHttpToTimSend(url, jsmsAccessTimerSms);
        if (result != null && result.isFailure()) {
            return result;
        } else {
            if (StringUtils.isNotBlank(taskId)) {//编辑时删除原有数据
                JsmsTimerSendTask jsmsTimerSendTask = new JsmsTimerSendTask();
                jsmsTimerSendTask.setTaskId(taskId);
                jsmsTimerSendTask.setStatus(SmsSendStatus.已删除.getValue());
                updateFlag = jsmsTimerSendTaskService.updateSelective(jsmsTimerSendTask);
                if (updateFlag > 0) {
                    if (bool) {
                        return ResultVO.successDefault(Code.SUCCESS, "本短信将于 " + jsmsAccessTimerSms.getSendtime() + " 发送，总共计费条数为 " + chargeNumTotal + "条，请在本短信发送前保持库存充足！");
                    } else {
                        return ResultVO.successDefault(Code.SUCCESS, "本短信将于 " + jsmsAccessTimerSms.getSendtime() + " 发送，总共计费条数为 " + chargeNumTotal + "条，当前库存已不足，请在本短信发送前保持库存充足！");
                    }
                } else {
                    return ResultVO.failure(Code.REDIRECT, "编辑短信失败,请联系客服！");
                }
            } else {
                if (bool) {
                    return ResultVO.successDefault(Code.SUCCESS, "本短信将于 " + jsmsAccessTimerSms.getSendtime() + " 发送，总共计费条数为 " + chargeNumTotal + "条，请在本短信发送前保持库存充足！");
                } else {
                    return ResultVO.successDefault(Code.SUCCESS, "本短信将于 " + jsmsAccessTimerSms.getSendtime() + " 发送，总共计费条数为 " + chargeNumTotal + "条，当前库存已不足，请在本短信发送前保持库存充足！");
                }
            }
        }
    }

    @Override
    public ResultVO parseMobileFile(String downloadUrl, String importFilePath, String tempDir4Download, String dirOfParsedFile) {
        return parseMobileFile(downloadUrl, importFilePath, tempDir4Download, dirOfParsedFile, MAX_IMPORT_LIMIT);
    }

    @Override
    public ResultVO parseMobileFile(String downloadUrl, String importFilePath, String tempDir4Download, String dirOfParsedFile, int maxImportLimit) {
        // 从文件服务器下载文件
        ResultVO resultVO;
        String downloadFilePath = null;
        try {
            String parsedFile = Des3Utils.decodeDes3(importFilePath);
            String fileName = FileIO.getFileName(parsedFile);
            logger.debug("解码文件路径 ---> {}, 文件名 --> {}", parsedFile, fileName);
            ResultVO parseFileExist = JsmsParseMobilesFile.INSTANCE.isParseFileExist(fileName, dirOfParsedFile);
            // 解析文件存在  --> 读取
            if (parseFileExist.isSuccess()) {
                String firstLine = FileIO.readFirstLine(parseFileExist.getMsg(), FileIO.Charset.UTF8);
                JsmsParseMobilesFile.ParseMobilesInfo parseMobilesInfo = JsmsParseMobilesFile.INSTANCE.parseFirstLine(firstLine);
                if (parseMobilesInfo != null) {
                    resultVO = ResultVO.successDefault(parseMobilesInfo);
                } else {
                    resultVO = ResultVO.failure("文件解析失败");
                }

            } else {  // 解析文件不存在  --> 解析
                downloadFilePath = JsmsHttpSend.INSTANCE.downloadFile(downloadUrl, importFilePath, tempDir4Download);
                logger.debug("从文件服务器下载的文件路径 --> {}", dirOfParsedFile);
                if (StringUtils.isBlank(downloadFilePath)) {
                    return ResultVO.failure("未找上传的文件");
                }
                resultVO = JsmsParseMobilesFile.INSTANCE.parseFileAndOutput(downloadFilePath, dirOfParsedFile, maxImportLimit);

            }
        } catch (Exception e) {
            logger.error("文件解析错误 --> {}", e);
            resultVO = ResultVO.failure("文件解析失败");
        }
        if (StringUtils.isNotBlank(downloadFilePath)) {
            logger.debug("删除从文件服务器下载的文件");
            FileUtils.delete(downloadFilePath);
        }
        return resultVO;
    }

    private ResultVO checkJsmsAccessSmsDTO(JsmsAccessSmsDTO jsmsAccessSmsDTO) {
        if (jsmsAccessSmsDTO == null) {
            return ResultVO.failure(Code.OPT_ERR_NOT_FOUND, "操作号码异常！");
        } else if (StringUtils.isBlank(jsmsAccessSmsDTO.getSign())) {
            return ResultVO.failure("签名不能为空");
        } else if (StringUtils.isBlank(jsmsAccessSmsDTO.getContent())) {
            return ResultVO.failure("短信内容不能为空");
        } else if (StringUtils.isBlank(jsmsAccessSmsDTO.getClientId())) {
            return ResultVO.failure("短信发送账号必选");
        }else if (jsmsAccessSmsDTO.getFileType() == null){
            return ResultVO.failure("请求不合法");
        }
        return null;
    }

    @Override
    public ResultVO oemSmsSend(JsmsAccessSmsDTO jsmsAccessSmsDTO, String dirOfParsedFile, WebId webId, String adminId) {
        ResultVO resultVO = checkJsmsAccessSmsDTO(jsmsAccessSmsDTO);
        if (resultVO != null && resultVO.isFail()) {
            return resultVO;
        }

        int submitProgressId = -1;
        // (1)初始进度
        ResultVO result = initSubmitProgress(jsmsAccessSmsDTO, webId, adminId);
        if (result.isFail()) {
            return result;
        }
        List<String> mobileLines;
        JsmsParseMobilesFile.ParseMobilesInfo parseMobilesInfo;
        submitProgressId = (int) result.getData(); // 获取初始化的进度id
        // (2) 号码解析
        if (SmsSendFileType.号码池.getValue().equals(jsmsAccessSmsDTO.getFileType())) {
            String mobileStr = jsmsAccessSmsDTO.getMobile();
            resultVO = JsmsParseMobilesFile.INSTANCE.parseMobiles(mobileStr, MAX_IMPORT_LIMIT);
            if (resultVO.isFail()) {
                updateStatusToFail(submitProgressId);
                return resultVO;
            }
            Map data = (Map) resultVO.getData();
            mobileLines = (List<String>) data.get("mobileLines");
            parseMobilesInfo = (JsmsParseMobilesFile.ParseMobilesInfo) data.get("parseMobilesInfo");
        } else {
            // 非号码池的为文件导入
            String importFilePath = jsmsAccessSmsDTO.getImportFilePath();
            String fileName = FileIO.getFileName(importFilePath);
            // 查询解析文件
            ResultVO parseFileExist = JsmsParseMobilesFile.INSTANCE.isParseFileExist(fileName, dirOfParsedFile);
            if (parseFileExist.isFail()) {
                updateStatusToFail(submitProgressId);
                return ResultVO.failure("文件解析失败");
            }
            String parseFilePath = parseFileExist.getMsg();
            mobileLines = FileIO.readAllLines(parseFilePath);
            if (mobileLines.isEmpty()) {
                updateStatusToFail(submitProgressId);
                return ResultVO.failure("文件解析失败");
            }
            parseMobilesInfo = JsmsParseMobilesFile.INSTANCE.parseFirstLine(mobileLines.get(0));
            mobileLines.remove(0);

        }
        if(parseMobilesInfo != null && parseMobilesInfo.getSubmitTotal() < 1){
            return ResultVO.failure("有效号码数为0");
        }
        // (3) 短信参数校验
        result = checkSmsParam(jsmsAccessSmsDTO.getContent(), jsmsAccessSmsDTO.getSmstype());
        if (result != null && result.isFailure()) {
            return result;
        }
        // (4) 用户账号基本信息校验
        JsmsAccount jsmsAccount = jsmsAccountService.getByClientId(jsmsAccessSmsDTO.getClientId());
        result = checkAccountBaseInfo(jsmsAccount);
        if (result != null && result.isFailure()) {
            return result;
        }
        // (5) 检查账户余额信息
        result = checkAccountFiance4MobileList(jsmsAccount, jsmsAccessSmsDTO, mobileLines); // 检查财务
        if (result != null && result.isFailure()) {
            return result;
        }
        // (6) 更新短信提交进度状态为: 提交中, 并补充 解析的号码信息
        updateSubmitProgress(parseMobilesInfo, submitProgressId);

        List<JsmsAccessSmsProgress> progressList = new ArrayList<>();
        for (String mobileLine : mobileLines) {
            JsmsAccessSmsProgress jsmsAccessSmsProgress = new JsmsAccessSmsProgress();
            jsmsAccessSmsProgress.setProgressId(submitProgressId);
            jsmsAccessSmsProgress.setMobileBatch(mobileLine);
            progressList.add(jsmsAccessSmsProgress);
        }
        // (7) 添加任务到队列
        jsmsAsyncSend.putListIntoSendQueue(progressList);
        return ResultVO.successDefault(Code.SUCCESS, "短信开始提交！");


    }

    private ResultVO updateStatusToFail(int progressId) {
        JsmsSubmitProgress failModel = new JsmsSubmitProgress();
        failModel.setId(progressId);
        failModel.setSubmitState(SmsSendSubmitState.提交失败.getValue());
        int i = jsmsSubmitProgressService.updateSelective(failModel);
        if (i > 0) {

            return null;
        } else {
            return null;
        }
    }

    private void updateSubmitProgress(JsmsParseMobilesFile.ParseMobilesInfo parseMobilesInfo, int submitProgressId) {
        logger.debug("更新短信提交状态为  ---->  提交中 : start");
        JsmsSubmitProgress updateModel = new JsmsSubmitProgress();
        updateModel.setId(submitProgressId);
        updateModel.setSubmitTotal(parseMobilesInfo.getSubmitTotal());
        updateModel.setErrNum(parseMobilesInfo.getErrNum());
        updateModel.setRepeatNum(parseMobilesInfo.getRepeatNum());
        updateModel.setActualSubmit(0);
        updateModel.setSubmitState(SmsSendSubmitState.提交中.getValue());
        updateModel.setSubmitBeginTime(new Date());
        int update = jsmsSubmitProgressService.updateSelective(updateModel);
        logger.debug("更新短信提交状态为  ---->  提交中 : end");
    }

    private ResultVO initSubmitProgress(JsmsAccessSmsDTO jsmsAccessSmsDTO, WebId webId, String adminId) {
        logger.debug("初始化短信提交状态为  ----> start");
        if (jsmsAccessSmsDTO != null) {
            JsmsSubmitProgress insertModel = new JsmsSubmitProgress();
            insertModel.setClientId(jsmsAccessSmsDTO.getClientId());
            insertModel.setFileType(jsmsAccessSmsDTO.getFileType());
            insertModel.setContent(jsmsAccessSmsDTO.getContent());
            insertModel.setSmstype(Integer.parseInt(jsmsAccessSmsDTO.getSmstype()));
            insertModel.setAdminId(adminId);
            insertModel.setSubmitState(SmsSendSubmitState.未提交.getValue());
            insertModel.setSign(jsmsAccessSmsDTO.getSign());
            insertModel.setChargeNum(jsmsAccessSmsDTO.getChargeNum());
            insertModel.setSubmitTotal(0);
            insertModel.setActualSubmit(0);
            insertModel.setErrNum(0);
            insertModel.setRepeatNum(0);
            insertModel.setImportFilePath(jsmsAccessSmsDTO.getImportFilePath());
            insertModel.setCreateTime(new Date());
            if (WebId.OEM代理商平台.equals(webId)) {
                insertModel.setAgentId(Integer.parseInt(adminId));
                insertModel.setSubmitType(TaskSubmitTypeEnum.代理商.getValue());
            } else if (WebId.客户端.equals(webId)) {
                insertModel.setSubmitType(TaskSubmitTypeEnum.子账户.getValue());
            }
            int insertNum = jsmsSubmitProgressService.insert(insertModel);
            logger.debug("初始化短信提交状态为  ----> end");
            if (insertNum > 0) {
                return ResultVO.successDefault(insertModel.getId());
            } else {
                return ResultVO.failure(Code.SYS_ERR_PARSE, "请求超时,请稍后再试...");
            }
        }
        return ResultVO.failure(Code.SYS_ERR_PARSE, "请求超时,请稍后再试...");
    }

    /**
     * @param template 短信模板
     * @param params   模板参数
     * @return 转换后的短信内容
     */
    public String convertTemplate(String template, List<String> params) {

        Pattern r = Pattern.compile("\\{[^\\}]*\\}");
        Matcher matcher = r.matcher(template);
        int count = 0;
        try {
            while (matcher.find()) {
                template = template.replaceFirst(r.toString(), params.get(count));
                ++count;
            }
        } catch (IndexOutOfBoundsException e) {
            StringBuilder msg = new StringBuilder("参数数量与模板所需参数个数不匹配: ");
            msg.append("List中params参数个数小于短信模板中参数个数");
            throw new IllegalArgumentException(msg.toString());
        }
        if (params.size() != count) {
            StringBuilder msg = new StringBuilder("参数数量与模板所需参数个数不匹配: ");
            msg.append("短信模板中参数个数 = ")
                    .append(count)
                    .append(" 个, List中参数个数 = ")
                    .append(params.size());
            throw new IllegalArgumentException(msg.toString());
        }
        logger.debug("即将发送的短信内容 --> {}", template);
        return template;
    }

    private ResultVO invokeHttpToSend(String url, JsmsAccessSms jsmsAccessSms) {
        String resultJson;
        String smsAccessUrl = url.replaceFirst("\\{[^\\}]*\\}", jsmsAccessSms.getClientid());
        logger.info("-------------smsAccessUrl---------->{}", smsAccessUrl);
        String accessSmsJson = JsonUtil.toJson(jsmsAccessSms);
        logger.info("-------------即将发送的短信json---------->{}", accessSmsJson);
        resultJson = invokeCommon(smsAccessUrl, accessSmsJson);
        logger.debug("短信响应内容 --> resultJson = {}", resultJson);
        if (StringUtils.isBlank(resultJson)) {
            logger.error("【短信发送失败】发送失败, 请求SMSP无响应, JsmsAccessSms ---> {}", accessSmsJson);
        } else {
            try {
                JsmsAccessSmsRespone smsRespone = JsonUtil.toObject(resultJson, JsmsAccessSmsRespone.class);
                return ResultVO.successDefault(smsRespone);
            } catch (IOException e) {
                logger.error("【短信发送失败】发送失败, JsmsAccessSms ---> {}, 请求SMSP响应异常 --> {}", accessSmsJson, resultJson);
                throw new RuntimeException(e);
            }
        }

        return ResultVO.failure();
    }

    private String invokeCommon(String smsAccessUrl, String accessSmsJson) {
        String resultJson;
        if (smsAccessUrl.startsWith("https")) {
            logger.debug("使用https协议请求短信接口");
            // 线上
            resultJson = HttpUtil.httpPost(smsAccessUrl, accessSmsJson, true);
        } else {
            logger.debug("使用http协议请求短信接口");
            resultJson = HttpUtil.httpPost(smsAccessUrl, accessSmsJson, false);
        }
        return resultJson;
    }

    private ResultVO invokeHttpToTimSend(String url, JsmsAccessTimerSms jsmsAccessTimerSms) {
        ResultVO resultVO = new ResultVO();
        String resultJson;
        String smsAccessUrl = url.replaceFirst("\\{[^\\}]*\\}", jsmsAccessTimerSms.getClientid());
        logger.info("-------------smsAccessUrl---------->{}", smsAccessUrl);
        String accessSmsJson = JsonUtil.toJson(jsmsAccessTimerSms);
        logger.info("-------------即将发送的短信json---------->{}", accessSmsJson);
        resultJson = invokeCommon(smsAccessUrl, accessSmsJson);
        logger.debug("短信响应内容 --> resultJson = {}", resultJson);
        if (StringUtils.isBlank(resultJson)) {
            logger.error("【短信定时发送失败】发送失败, 请求SMSP无响应, JsmsAccessSms ---> {}", accessSmsJson);
        } else {

            try {

                JsmsAccessTimerSmsRespone smsRespone = JSON.parseObject(resultJson, JsmsAccessTimerSmsRespone.class);
                return ResultVO.successDefault(smsRespone);
            } catch (Exception e) {
                logger.error("【短信定时发送失败】发送失败, JsmsAccessSms ---> {}, 异常 --> {}",accessSmsJson,e.getMessage());
                return ResultVO.failure(Code.REDIRECT,"定时短信发送失败,请联系客服");
            }
        }
        return ResultVO.failure();
    }

    /**
     * 检查账户的基本信息
     *
     * @param jsmsAccount
     * @return
     */
    private ResultVO checkAccountBaseInfo(JsmsAccount jsmsAccount) {
        ResultVO result = null;
        if (jsmsAccount == null) {
            result = ResultVO.failure("当前子账号不存在");
        } else {
            if (AccountStatusEnum.注册完成.getValue().equals(jsmsAccount.getStatus())) {
                if (!OauthStatusEnum.证件已认证.getValue().equals(jsmsAccount.getOauthStatus())) { // 未认证
                    result = ResultVO.failure("当前子账号尚未完成资质认证");
                }
            } else if (AccountStatusEnum.冻结.getValue().equals(jsmsAccount.getStatus())) {
                result = ResultVO.failure("当前子账号已被冻结");
            } else if (AccountStatusEnum.注销.getValue().equals(jsmsAccount.getStatus())) {
                result = ResultVO.failure("当前子账号已被注销");
            } else if (AccountStatusEnum.锁定.getValue().equals(jsmsAccount.getStatus())) {
                result = ResultVO.failure("当前子账号已被锁定");
            }
        }
        return result;
    }

    /**
     * 检查账户余额的基本信息(定时短信)
     *
     * @param jsmsAccount
     * @return
     */
    private ResultVO checkFiance4TimerSend(JsmsAccount jsmsAccount, JsmsAccessTimerSms jsmsAccessTimerSms, Integer chargeNumTotal) {
        BigDecimal totalNormal = BigDecimal.ZERO;
        String smstype = jsmsAccessTimerSms.getSmstype();
        // 02.后付费用户 余额校验
        if (PayType.后付费.getValue().equals(jsmsAccount.getPaytype())) {
            logger.debug("【短信发送】后付费用户 = {},不需要判断是否购买产品包", jsmsAccount.getClientid());
            List<JsmsUserPriceLog> priceList = jsmsUserPriceLogService.getuserPrice(jsmsAccount.getClientid(), Integer.parseInt(smstype));
            BigDecimal totalPrice;//总价
            BigDecimal account = getBalanceOEM(jsmsAccount.getAgentId());
            ;//可用额度
            if (chargeNumTotal != null) {
                if (priceList.size() > 0) {
                    if (priceList.get(0).getUserPrice().compareTo(BigDecimal.ZERO) == 1) {//取生效日期最近的一条
                        totalPrice = new BigDecimal(chargeNumTotal).multiply(priceList.get(0).getUserPrice());
                    } else {
                        return ResultVO.failure(Code.OPT_ERR, "获取短信单价异常");
                    }
                } else {
                    return ResultVO.failure(Code.OPT_ERR, "获取短信单价异常");
                }
            } else {
                return ResultVO.failure(Code.OPT_ERR, "获取总计费条数异常");
            }

            if (account.compareTo(totalPrice) >= 0) {
                return ResultVO.successDefault(Code.SUCCESS);
            } else {
                return ResultVO.failure(Code.OPT_ERR_FORBIDDEN, "当前短信库存不足,是否继续保存定时任务？");
            }
        }

        /**
         * 检查OEM短信池剩余数量
         */
        List<JsmsOemClientPool> oemClientPoolList = getOemClientPool(jsmsAccount.getClientid());
        for (JsmsOemClientPool clientPool : oemClientPoolList) {
            Integer productType = clientPool.getProductType();
            if (SmsTypeEnum.通知.getValue().toString().equals(smstype)) {
                if (ProductType.行业.getValue().equals(productType) || ProductType.通知.getValue().equals(productType)) {
                    BigDecimal normalRemain = clientPool.getRemainNumber() == null ? BigDecimal.ZERO : BigDecimal.valueOf(clientPool.getRemainNumber());
                    totalNormal = totalNormal.add(normalRemain);

                }
            }
            if (SmsTypeEnum.验证码.getValue().toString().equals(smstype)) {
                if (ProductType.行业.getValue().equals(productType) || ProductType.验证码.getValue().equals(productType)) {
                    BigDecimal normalRemain = clientPool.getRemainNumber() == null ? BigDecimal.ZERO : BigDecimal.valueOf(clientPool.getRemainNumber());
                    totalNormal = totalNormal.add(normalRemain);

                }
            }
            if (SmsTypeEnum.营销.getValue().toString().equals(smstype)) {
                if (ProductType.营销.getValue().equals(productType)) {
                    BigDecimal normalRemain = clientPool.getRemainNumber() == null ? BigDecimal.ZERO : BigDecimal.valueOf(clientPool.getRemainNumber());
                    totalNormal = totalNormal.add(normalRemain);
                }
            }
        }
        if (totalNormal.compareTo(new BigDecimal(chargeNumTotal)) != 1) {
            return ResultVO.failure(Code.OPT_ERR_FORBIDDEN, "当前短信库存不足,是否继续保存定时任务？");
//            return ResultVO.failure(("本短信将于 " + jsmsAccessTimerSms.getSendtime() + " 发送，总共计费条数为 " + chargeNumTotal + "条，当前库存量已不足，请在本短信发送前保持库存充足！"));
        } else {
            return ResultVO.successDefault(Code.SUCCESS);
        }
    }

    private BigDecimal getBalanceOEM(Integer agentid) {
        JsmsAgentAccount agentAccount = jsmsAgentAccountService.getByAgentId(agentid);
        BigDecimal account;//可用额度
        BigDecimal balance = agentAccount.getBalance();  //余额
        if (balance.compareTo(BigDecimal.ZERO) == 1) {
            //大于0，可用额度=余额+授信额度
            account = agentAccount.getBalance().setScale(2, BigDecimal.ROUND_DOWN).add(agentAccount.getCurrentCredit().setScale(2, BigDecimal.ROUND_DOWN));
        } else {
            //小于或等于0，可用额度=授信额度
            account = agentAccount.getCurrentCredit().setScale(2, BigDecimal.ROUND_DOWN);
        }
        return account;
    }

    private List<JsmsOemClientPool> getOemClientPool(String clientid) {
        JsmsOemClientPool jsmsOemClientPool = new JsmsOemClientPool();
        jsmsOemClientPool.setClientId(clientid);
        jsmsOemClientPool.setStatus(OEM_CLIENT_POOL_STATUS_NORMAL);
        List<JsmsOemClientPool> oemClientPoolList = jsmsOemClientPoolService.getListByPoolInfoAndDueTimeRange(jsmsOemClientPool, new Date(), null);
        return oemClientPoolList;
    }

    private ResultVO checkAccountFianceInfo(JsmsAccount jsmsAccount, JsmsAccessSmsDTO jsmsAccessSmsDTO, String[] mobileArr) {
        ResultVO resultVO;
        resultVO = checkAccountFianceInfo(jsmsAccount, jsmsAccessSmsDTO.getSmstype(), mobileArr, jsmsAccessSmsDTO.getChargeNum());
        return resultVO;
    }

    private ResultVO checkAccountFiance4MobileList(JsmsAccount jsmsAccount, JsmsAccessSmsDTO jsmsAccessSmsDTO, List mobileList) {
        return checkAccountFianceInfo(jsmsAccount, jsmsAccessSmsDTO.getSmstype(), null, mobileList, jsmsAccessSmsDTO.getChargeNum());
    }

    /**
     * 检查账户的基本信息
     *
     * @param jsmsAccount
     * @return
     */
    private ResultVO checkAccountFianceInfo(JsmsAccount jsmsAccount, String smstype, String[] mobileArr, Integer charageNum) {
        return checkAccountFianceInfo(jsmsAccount, smstype, mobileArr, null, charageNum);
    }

    private ResultVO checkAccountFianceInfo(JsmsAccount jsmsAccount, String smstype, String[] mobileArr, List<String> mobileLines, Integer chargeNum) {
        List<String> normalMobileList = new ArrayList<>(); // 普通短信
        List<String> iSmsList = new ArrayList<>(); // 国际短信
        BigDecimal iSmsFeeSum = BigDecimal.ZERO;
        ResultVO result = null;
        // 02.后付费用户 余额校验
        if (PayType.后付费.getValue().equals(jsmsAccount.getPaytype())) {
            logger.debug("【短信发送】后付费用户 = {},不需要判断是否购买产品包", jsmsAccount.getClientid());
            BigDecimal account = BigDecimal.ZERO;//可用额度
            account = getBalanceOEM(jsmsAccount.getAgentId());
            if (!(account.compareTo(BigDecimal.ZERO) == 1)) {
                StringBuffer msg = new StringBuffer();
                msg.append("可用额度不足,无法发送！");
                return ResultVO.failure(msg.toString());
            }
            return result;
        }

        // 按手机最大发送数量将手机号码分组
        //String[] mobileArr = jsmsAccessSms.getMobile().split(",");
        List<String> inValidSmsList = new ArrayList<>(); // 找不到的费用国际短信
        JsmsPage<JsmsClientTariff> jsmsPage = new JsmsPage();
        jsmsPage.setRows(1000);
        jsmsClientTariffService.queryList(jsmsPage);
        List<JsmsClientTariff> tariffList = jsmsPage.getData();
        Map<String, BigDecimal> tariffMap = new HashMap();
        for (JsmsClientTariff clientTariff : tariffList) {
            tariffMap.put(clientTariff.getPrefix().toString(), clientTariff.getFee().divide(new BigDecimal("1000000"), BigDecimal.ROUND_HALF_UP));
        }
        if (mobileArr != null && mobileArr.length > 0) {
            for (String mobile : mobileArr) {
                if (StringUtils.isBlank(mobile)) {
                    continue;
                }
                if (RegexUtils.isInternationMobile(mobile)) { // 国际短信
                    iSmsList.add(mobile);
                    BigDecimal fee = getFee(tariffMap, mobile.substring(2, 5));
                    logger.debug("{}号码费用fee={}", mobile, fee);
                    if (fee == null)
                        inValidSmsList.add(mobile);
                    else
                        iSmsFeeSum = iSmsFeeSum.add(fee);
                } else if (RegexUtils.isMobile(mobile)) {
                    normalMobileList.add(mobile);
                }
            }
        } else if (mobileLines != null && !mobileLines.isEmpty()) {
            for (String mobiles : mobileLines) {
                if (StringUtils.isNoneBlank(mobiles)) {
                    mobileArr = mobiles.split(",");
                    for (String mobile : mobileArr) {
                        if (StringUtils.isBlank(mobile)) {
                            continue;
                        }
                        if (RegexUtils.isInternationMobile(mobile)) { // 国际短信
                            iSmsList.add(mobile);
                            BigDecimal fee = getFee(tariffMap, mobile.substring(2, 5));
                            logger.debug("{}号码费用fee={}", mobile, fee);
                            if (fee == null)
                                inValidSmsList.add(mobile);
                            else
                                iSmsFeeSum = iSmsFeeSum.add(fee);
                        } else if (RegexUtils.isMobile(mobile)) {
                            normalMobileList.add(mobile);
                        }
                    }
                }
            }
        }

        if (inValidSmsList.size() > 0) {
            StringBuffer msg = new StringBuffer();
            String delime = "";
            int i = 0;
            for (String isms : inValidSmsList) {
                if (i == 3)
                    break;
                msg.append(delime).append(isms);
                delime = ", ";
                i++;
            }
            msg.append("等国际号码对应的费用不存在，请修改后再发送。");
            return ResultVO.failure(msg.toString());

        }
        // 发送短信前检查客户订单中是否还有可用短信条数
        // 1.页面中验证码和通知对应订单中行业短信 2.暂时不校验国际短信
        // 0:行业短信,1:营销短信,2国际短信 todo: 暂不支持国际
        Integer smstypeInt = Integer.parseInt(smstype);
        /**
         * 检查OEM短信池剩余数量
         */
        List<JsmsOemClientPool> oemClientPoolList = getOemClientPool(jsmsAccount.getClientid());
        BigDecimal normalRemainSum = BigDecimal.ZERO;
        BigDecimal iSmsRemainSum = BigDecimal.ZERO;
        for (JsmsOemClientPool clientPool : oemClientPoolList) {
            Integer productType = clientPool.getProductType();
            /**
             * 【产品类型】行业的 支持 【短信类型】通知和验证
             */
            if (SmsTypeEnum.通知.getValue().equals(smstypeInt)) {
                if (ProductType.行业.getValue().equals(productType) || ProductType.通知.getValue().equals(productType)) {
                    BigDecimal normalRemain = clientPool.getRemainNumber() == null ? BigDecimal.ZERO : BigDecimal.valueOf(clientPool.getRemainNumber());
                    normalRemainSum = normalRemainSum.add(normalRemain);

                }
            } else if (SmsTypeEnum.验证码.getValue().equals(smstypeInt)) {
                if (ProductType.行业.getValue().equals(productType) || ProductType.验证码.getValue().equals(productType)) {
                    BigDecimal normalRemain = clientPool.getRemainNumber() == null ? BigDecimal.ZERO : BigDecimal.valueOf(clientPool.getRemainNumber());
                    normalRemainSum = normalRemainSum.add(normalRemain);

                }
            } else if (SmsTypeEnum.营销.getValue().equals(smstypeInt)) {
                if (ProductType.营销.getValue().equals(productType)) {
                    BigDecimal normalRemain = clientPool.getRemainNumber() == null ? BigDecimal.ZERO : BigDecimal.valueOf(clientPool.getRemainNumber());
                    normalRemainSum = normalRemainSum.add(normalRemain);

                }
            }

            if (ProductType.国际.getValue().equals(clientPool.getProductType())) {
                BigDecimal ismsRemain = clientPool.getRemainAmount() == null ? BigDecimal.ZERO : clientPool.getRemainAmount();
                iSmsRemainSum = iSmsRemainSum.add(ismsRemain);
            }
        }
        int remainNum = normalRemainSum.intValue();
        int mobileNum = normalMobileList.size();
        int chargeTotal = mobileNum * chargeNum;
        logger.debug("短信内容拆分为 =>{}条, 号码数 =>{}, 总计费条数 =>{} ,  短信池中剩余可以发 smstype = 1 的总数 ==> {}", chargeNum, mobileNum, chargeTotal, remainNum);
        if (chargeTotal > remainNum) {
            StringBuffer reminder = new StringBuffer(); // 提示语
            reminder.append("短信发送失败！您账户下的");
            if (SmsTypeEnum.通知.getValue().equals(smstypeInt)) {
                reminder.append("通知类型短信余额不足");
            } else if (SmsTypeEnum.验证码.getValue().equals(smstypeInt)) {
                reminder.append("验证码类型短信余额不足");
            } else {
                reminder = reminder.append(ProductType.营销.getDesc())//
                        .append("类型短信余额不足。");
            }
            return ResultVO.failure(reminder.toString());
        }
        if ((iSmsFeeSum.multiply(new BigDecimal(chargeNum))).compareTo(iSmsRemainSum) > 0) {
            StringBuffer msg = new StringBuffer();
            String delime = "";
            int i = 0;
            for (String isms : iSmsList) {
                if (i == 3)
                    break;
                msg.append(delime).append(isms);
                delime = ", ";
                i++;
            }
            msg.append("等是国际号码，您的国际短信账户余额不足，请修改后再发送。");
            return ResultVO.failure(msg.toString());
        }
        return result;
    }

    private BigDecimal getFee(Map<String, BigDecimal> tariffMap, String substring) {
        if (substring.length() < 1)
            return null;
        return tariffMap.get(substring);
    }


    private ResultVO checkSmsParam(JsmsAccessSms jsmsAccessSms) {
        return checkSmsParam(jsmsAccessSms.getContent(), jsmsAccessSms.getSmstype());
    }

    private ResultVO checkSmsParam(String content, String smstype) {

        if (SmsTypeEnum.闪信.getValue().toString().equals(smstype) || SmsTypeEnum.USSD.getValue().toString().equals(smstype)) { // 彩印模版长度不一样
            if (content.length() == 0 || content.length() > SMS_LENGTH_62) {
                return ResultVO.failure("彩印短信内容加上签名长度不能为空且长度不能超过62个字符");
            }
        } else { // 普通短信
            if (content.length() == 0 || content.length() > SMS_LENGTH_500) {
                return ResultVO.failure("短信内容加上签名长度不能为空且长度不能超过500个字符");
            }

            if (!isValidSmsType(smstype)) { // 只需判断非模版短信的短信类型即可
                return ResultVO.failure("短信类型不合法");
            }
        }
        return null;
    }

    private ResultVO checkSendTime(JsmsAccessTimerSms jsmsAccessTimerSms) {
        ResultVO resultVO = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date submitTime = null;
        try {
            submitTime = df.parse(jsmsAccessTimerSms.getSendtime());
        } catch (ParseException e) {
            logger.debug("日期格式化异常", e);
        }
        java.util.Date date = new Date();
        long l = submitTime.getTime() - date.getTime();
        if (l <= 0) {
            return resultVO.failure(Code.OPT_ERR, "您设置的定时时间已经过期，请重新选择时间！");
        }
        if (l > 0 && l <= jsmsSendParam.getSendCFG(JsmsSendParam.TimeSendCFG.发送最小间隔) * 60 * 1000) {
            return resultVO.failure(Code.OPT_ERR, "您设置的定时时间距离您要发送的时间太近了，请重新选择时间！");
        }
        return null;
    }

    /**
     * 判断短信类型是否有效
     *
     * @param smstype
     * @return
     */
    private boolean isValidSmsType(String smstype) {
        String smsTypeDesc = null;
        try {
            smsTypeDesc = SmsTypeEnum.getDescByValue(Integer.parseInt(smstype));
        } catch (NumberFormatException e) {
            logger.error("判断短信类型异常, smstype = {}", smstype);
            return false;
        }
        return StringUtils.isNoneBlank(smsTypeDesc);
    }

    /**
     * 将手机号码分组，每组字符串中的号码以英文逗号分隔
     *
     * @param array
     * @return
     */
    private List<String> checkAndSplitSmsMobile(String[] array) {
        List<String> mobileList = new ArrayList<String>();
        StringBuilder mobileBuilder = new StringBuilder();
        String temp;
        for (int i = 0; i < array.length; i++) {
            temp = array[i];
            if (StringUtils.isNoneBlank(temp)) {
                if (!(RegexUtils.isMobile(temp) || RegexUtils.isInternationMobile(temp))) {
                    continue;
                }
                mobileBuilder.append(temp);
                if ((i + 1 == array.length) || ((i + 1) % SMS_SEND_MAX_NUM == 0)) {
                    mobileList.add(mobileBuilder.toString());
                    mobileBuilder.delete(0, mobileBuilder.length());
                    continue;
                } else {
                    mobileBuilder.append(",");
                }
            }
        }

        /*List<String> arrayList = Arrays.asList(array);
        if (array.length <= SMS_SEND_MAX_NUM) {
            mobileList.add(StringUtils.join(array, ","));
            return mobileList;
        }
        List<String> temp = new ArrayList<String>();

        for (int pos = 0; pos < arrayList.size(); pos++) {
            temp.add(arrayList.get(pos));
            if (((pos + 1) % SMS_SEND_MAX_NUM == 0) || (pos == arrayList.size() - 1)) {
                mobileList.add(StringUtils.join(temp.toArray(), ","));
                temp = new ArrayList<String>();
            }
        }*/
        return mobileList;
    }


}
