package com.jsmsframework.sms.send.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.concurrent.atomic.AtomicInteger;

@JsonInclude(Include.NON_NULL)
public class JsmsAccessSmsProgress {

	private static final long serialVersionUID = 8884923026714807330L;
	private static final int SEND_FAIL_RETRY_TIMES = 5;

	/**
	 * 一批次的号码, 每100为1批
	 */
	private String mobileBatch;
	/**
	 * 对应短信进度的主键id
	 */
	private int progressId;
	/**
	 * 失败重试次数
	 */
	private AtomicInteger failRetryTimes = new AtomicInteger(SEND_FAIL_RETRY_TIMES);

	public String getMobileBatch() {
		return mobileBatch;
	}

	public void setMobileBatch(String mobileBatch) {
		this.mobileBatch = mobileBatch;
	}

	public int getProgressId() {
		return progressId;
	}

	public void setProgressId(int progressId) {
		this.progressId = progressId;
	}

	public AtomicInteger getFailRetryTimes() {
		return failRetryTimes;
	}
}
