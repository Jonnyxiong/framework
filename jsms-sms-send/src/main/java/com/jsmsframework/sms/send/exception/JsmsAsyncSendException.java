package com.jsmsframework.sms.send.exception;

public class JsmsAsyncSendException extends JsmsSmsSendException {

	private String errorCode;

	private String message;

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	// 两个参数都包含
	public JsmsAsyncSendException(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	// 只包含消息
	public JsmsAsyncSendException(String message) {
		super();
		this.message = message;
	}

	// 空的构造函数
	public JsmsAsyncSendException() {
		super();
	}

}
