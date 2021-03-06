package com.jsmsframework.order.product.exception;

/**
 * @description 代理商产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsClientOrderProductException extends  RuntimeException{


    private String errorCode;

    private String message;

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    // 两个参数都包含
    public JsmsClientOrderProductException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    // 只包含消息
    public JsmsClientOrderProductException(String message) {
        super();
        this.message = message;
    }

    // 空的构造函数
    public JsmsClientOrderProductException() {
        super();
    }

}
