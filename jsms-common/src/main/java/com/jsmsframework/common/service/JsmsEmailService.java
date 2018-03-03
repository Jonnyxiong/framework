package com.jsmsframework.common.service;

import org.springframework.mail.javamail.JavaMailSender;

import com.jsmsframework.common.entity.JsmsSendEmailList;

/**
 * Created by xiongfenglin on 2017/11/25.
 *
 * @author: xiongfenglin
 */
public interface JsmsEmailService {
    /**
     * 发送文本格式的Email
     * @param to 接收者，用,分割
     * @param subject 主题
     * @param body 内容
     * @return 是否发送成功
     */
    boolean sendTextEmail(String form,String to, String subject, String body);
    /**
     * 发送文本格式的Email
     * @param javaMailSender 手动注入 javaMailSender 发送对象
     * @param to 接收者，用,分割
     * @param subject 主题
     * @param body 内容
     * @return 是否发送成功
     */
    boolean sendTextEmail(JavaMailSender javaMailSender,String form,String to, String subject, String body);

    /**
     * 发送html格式的Email
     * @param to 接收者，用,分割
     * @param subject 主题
     * @param body 内容
     * @return 是否发送成功
     */
    boolean sendHtmlEmail(String form,String to, String subject, String body);
    /**
     * 发送html格式的Email
     * @param javaMailSender 手动注入 javaMailSender 发送对象
     * @param to 接收者，用,分割
     * @param subject 主题
     * @param body 内容
     * @return 是否发送成功
     */
    boolean sendHtmlEmail(JavaMailSender javaMailSender,String form, String to, String subject, String body);

    /**
     * 发送html格式的Email
     * @param jsmsSendEmailList 发送邮箱管理(ip,端口等)
     * @param to 接收者，用,分割
     * @param subject 主题
     * @param body 内容
     * @return 是否发送成功
     */
    boolean sendHtmlEmail(JsmsSendEmailList jsmsSendEmailList, String to, String subject, String body);
}
