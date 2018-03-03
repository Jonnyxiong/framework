package com.jsmsframework.common.service;

import com.jsmsframework.common.entity.JsmsSendEmailList;
import com.jsmsframework.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by xiongfenglin on 2017/11/25.
 *
 * @author: xiongfenglin
 */
@Service
public class JsmsEmailServiceImpl implements JsmsEmailService{
    private static final Logger logger = LoggerFactory.getLogger(JsmsEmailServiceImpl.class);
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送者
     */
   // private static final String from = "admin@ucpaas.com";

    @Override
    public boolean sendTextEmail(String from,String to, String subject, String body) {
        return sendTextEmail(javaMailSender,from,to,subject,body);
    }

    @Override
    public boolean sendTextEmail(JavaMailSender javaMailSender,String from,String to, String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(from);
            msg.setTo(to.split(","));
            msg.setSubject(subject);
            msg.setText(body);
            javaMailSender.send(msg);
            return true;
        } catch (Throwable e) {
            logger.error("发送文本格式的Email【失败】：to=" + to + ", subject=" + subject + ", body=" + body, e);
        }
        return false;
    }

    @Override
    public boolean sendHtmlEmail(String from,String to, String subject, String body) {
        return sendHtmlEmail(javaMailSender,from,to,subject,body);
    }

    @Override
    public boolean sendHtmlEmail(JavaMailSender javaMailSender,String from,String to, String subject, String body) {
       // logger.debug("发送html格式的Email【开始】：to={}, subject={}, body={}", to, subject, body);
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");
            helper.setFrom(from);
            helper.setTo(to.split(","));
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(msg);

            logger.debug("发送html格式的Email【成功】：to={}, subject={}, body={}", to, subject, body);
            return true;
        } catch (Throwable e) {
           logger.error("发送html格式的Email【失败】：to=" + to + ", subject=" + subject + ", body=" + body, e);
        }
        return false;
    }

    @Override
    public boolean sendHtmlEmail(JsmsSendEmailList jsmsSendEmailList, String to, String subject, String body) {
        if (null != jsmsSendEmailList){
            try {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                String[] hostAndPort = jsmsSendEmailList.getIncomingMailServer().split(":");
                mailSender.setHost(hostAndPort[0]);
                mailSender.setPort(Integer.valueOf(hostAndPort[1]));
                mailSender.setUsername(jsmsSendEmailList.getEmailUsername());
                mailSender.setPassword(jsmsSendEmailList.getEmailPassword());
                mailSender.setDefaultEncoding("utf-8");

                //加认证机制
                Properties javaMailProperties = new Properties();
                javaMailProperties.put("mail.smtp.auth", true);
                javaMailProperties.put("mail.smtp.timeout", 25000);
                javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                mailSender.setJavaMailProperties(javaMailProperties);

                MimeMessage msg = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");
                helper.setFrom(jsmsSendEmailList.getEmailUsername());
                helper.setTo(to.split(","));
                helper.setSubject(subject);
                helper.setText(body, true);
                mailSender.send(msg);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("发送html格式的Email【失败】：配置发送服务器错误"+ JsonUtil.toJson(jsmsSendEmailList));
                return false;
            }
        }
        logger.error("发送html格式的Email【失败】：配置发送服务器为空{}", jsmsSendEmailList);
        return false;
    }
}
