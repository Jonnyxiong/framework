package com.jsmsframework.middleware.exception;

/**
 * @description 组件与MQ关联表
 * @author huangwenjie
 * @date 2017-11-04
 */
public class JsmsComponentRefMqException extends  RuntimeException{


    public JsmsComponentRefMqException(String message) {
        super(message);
    }
    
}
