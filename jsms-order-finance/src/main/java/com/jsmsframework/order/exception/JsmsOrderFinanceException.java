package com.jsmsframework.order.exception;

public class JsmsOrderFinanceException extends RuntimeException {

	private String errorCode;

	private String message;

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	// 两个参数都包含
	public JsmsOrderFinanceException(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	// 只包含消息
	public JsmsOrderFinanceException(String message) {
		super();
		this.message = message;
	}

	// 空的构造函数
	public JsmsOrderFinanceException() {
		super();
	}

}
