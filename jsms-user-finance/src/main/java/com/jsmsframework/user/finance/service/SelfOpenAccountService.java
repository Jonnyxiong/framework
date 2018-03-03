package com.jsmsframework.user.finance.service;

import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.entity.JsmsOauthPic;

/**
 * 用户中心-自助开户
 *
 * @outhor tanjiangqiang
 * @create 2017-11-23 17:22
 */
public interface SelfOpenAccountService {

    /**
     * @param jsmsAccountPo          用户信息
     * @param oauthPicPo             认证信息
     * @param adminId                用户id
     * @param remindMailBoby         提醒邮箱主题
     * @param remindMailSubject      提醒邮箱主题
     * @param buyAgentOrderId        OEM代理商购买订单
     * @param distributeAgentOrderId OEM代理商分发订单
     * @param oemClientOrderId          oem客户订单
     * @Description: 用户中心--开户
     * @Author: tanjiangqiang
     * @Date: 2017/11/23 - 17:34
     */
    JsmsAccount addUser(JsmsAccount jsmsAccountPo, JsmsOauthPic oauthPicPo, Long adminId, String remindMailBoby, String remindMailSubject, Long buyAgentOrderId, Long distributeAgentOrderId, Long oemClientOrderId);
}