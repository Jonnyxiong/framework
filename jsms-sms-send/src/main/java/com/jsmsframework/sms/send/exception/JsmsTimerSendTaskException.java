package com.jsmsframework.sms.send.exception;

/**
 * @description 定时短信发送任务信息表
 * @author Don
 * @date 2018-01-04
 */
public class JsmsTimerSendTaskException extends  RuntimeException{


    public JsmsTimerSendTaskException(String message) {
        super(message);
    }
    
}
