package com.jsmsframework.common.enums;

/**
 * Created by lpjLiu on 2017/7/10. WEBid
 */
public enum WebId {
	短信调度系统(1, "短信调度系统"),
	代理商平台(2, "代理商平台"),
	运营平台(3, "运营平台"),
	OEM代理商平台(4, "OEM代理商平台"),
	云运营平台(5, "云运营平台"),
	云运维平台(6, "云运维平台"),
	客户端(7, "客户端");

	private Integer value;
	private String desc;

	WebId(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public Integer getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public static String getDescByValue(Integer value) {
		if(value == null){ return null;}
		String result = null;
		for (WebId s : WebId.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
