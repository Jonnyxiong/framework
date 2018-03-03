package com.jsmsframework.common.enums;

/**
 * Created by lpjLiu on 2017/7/10. EmailType
 */
public enum EmailType {
	POP3(0, "POP3"),
	IMAP(1, "IMAP");

	private Integer value;
	private String desc;

	EmailType(Integer value, String desc) {
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
		for (EmailType s : EmailType.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}

	public static EmailType getInstanceByValue(Integer value) {
		for (EmailType webIdNew : EmailType.values()) {
			if (webIdNew.getValue().equals(value)) {
				return webIdNew;
			}
		}
		return null;
	}
}
