package com.jsmsframework.sms.send.service;

import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.enums.SmsTypeEnum;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.sms.send.dto.JsmsAccessSmsDTO;
import com.jsmsframework.sms.send.po.JsmsAccessSms;
import com.jsmsframework.sms.send.po.JsmsAccessTimerSms;

import java.util.List;

public interface JsmsSendService {

    /**
     * 短信发送
     * @param jsmsAccessSms
     * @param url 短信发送接口, url包含一组大括号, 如:http://sms.ucpaas.com?
     * @return
     */
    @Deprecated
    public ResultVO oemSmsSend(JsmsAccessSms jsmsAccessSms, String url);
    /**
     * OEM短信发送, 发送前会校验账号状态及余额
     * @param clientid 子账号id
     * @param password 密码,MD5加密
     * @param mobile 手机号
     * @param smstype 短信类型
     * @param content 发送内容
     * @param url 短信发送接口, url包含一组大括号, 如:http://sms.ucpaas.com/sms/{client}/sending
     * @return
     */
    @Deprecated
    public ResultVO oemSmsSend(String clientid,String password,String mobile,String smstype,String content, String url);

    /**
     * 告警短信发送, 不校验账号信息(适用于内部通知)
     * @param clientid 子账号id
     * @param password 密码,MD5加密
     * @param mobile 手机号
     * @param smstype 短信类型, (可空,空即为通知)
     * @param template 发送模板, 模板中参数需要和List中参数数量一致
     * @param params 模板参数
     * @param url 短信发送接口, url包含一组大括号, 如:http://sms.ucpaas.com/sms/{client}/sending
     * @return
     */
    public ResultVO sendTemplateSms(String clientid, String password, String mobile, SmsTypeEnum smstype, String template, List<String> params, String url);

    /**
     * oem定时发送短j信,发送前会校验账号状态及余额
     * @param jsmsAccessTimerSms
     * @param url 短信发送接口, url包含一组大括号, 如:http://sms.ucpaas.com?
     * @return
     */
    public ResultVO oemSmsTimSend(JsmsAccessTimerSms jsmsAccessTimerSms, String taskId,String url,Integer chargeNumTotal,boolean bool);

    /**
     * 立即发送   最大支持1百万 txt csv xls
     * @param jsmsAccessSmsDTO
     * @param webid
     * @param id
     * @param dirOfParsedFile 解析文件保存的路径, 不包含文件名
     * @return
     */
    public ResultVO oemSmsSend(JsmsAccessSmsDTO jsmsAccessSmsDTO, String dirOfParsedFile, WebId webid, String id);

    /**
     * @param downloadUrl 文件服务器下载接口
     * @param importFilePath 文件服务器内保存的路径, des3加密
     * @param tempDir4Download 本机保存临时文件的路径
     * @param dirOfParsedFile 本机保存解析文件的文件夹
     * @param maxImportLimit 最大解析号码限制
     * @return
     */
    public ResultVO parseMobileFile(String downloadUrl,String importFilePath, String tempDir4Download, String dirOfParsedFile,int maxImportLimit);
    /**
     * @param downloadUrl 文件服务器下载接口
     * @param importFilePath 文件服务器内保存的路径, des3加密
     * @param tempDir4Download 本机保存临时文件的路径
     * @param dirOfParsedFile 本机保存解析文件的文件夹
     *  最大解析号码限制, 默认 100万
     * @return
     */
    public ResultVO parseMobileFile(String downloadUrl,String importFilePath, String tempDir4Download, String dirOfParsedFile);

}
