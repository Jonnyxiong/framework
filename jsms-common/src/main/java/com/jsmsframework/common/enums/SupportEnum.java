package com.jsmsframework.common.enums;

public enum SupportEnum {

	不支持(0, "不支持"), 支持(1, "支持");
	private Integer value;
	private String desc;

	SupportEnum(Integer value, String desc) {
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
		if (value == null) {
			return null;
		}
		String result = null;
		for (SupportEnum s : SupportEnum.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}

	public static SupportEnum getInstanceByValue(Integer value) {
		for (SupportEnum supportEnum : SupportEnum.values()) {
			if (supportEnum.getValue().equals(value)) {
				return supportEnum;
			}
		}
		return null;
	}
}
