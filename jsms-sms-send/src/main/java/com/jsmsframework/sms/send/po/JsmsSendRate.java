package com.jsmsframework.sms.send.po;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class JsmsSendRate {

	private static final long serialVersionUID = 8884923026714807330L;
	/**
	 * 默认的频率: 150批次
	 */
	private static final int DEFAULT_RATE = 150;
	/**
	 * 默认频率基础时间: 1000毫秒
	 */
	public static final int DEFAULT_MILLIS = 1000;

	/**
	 * 一秒内待请求的次数
	 */
	private AtomicInteger count = new AtomicInteger(DEFAULT_RATE);
	/**
	 * 一秒内待请求的次数
	 */
	private AtomicInteger rate = new AtomicInteger(DEFAULT_RATE);
	/**
	 * 每秒请求开始的时间戳
	 */
	private AtomicLong timeStamp = new AtomicLong();

	public AtomicInteger getCount() {
		return count;
	}

	public AtomicLong getTimeStamp() {
		return timeStamp;
	}

	public AtomicInteger getRate() {
		return rate;
	}

}
