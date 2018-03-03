package com.jsmsframework.common.enums;

/**
 * Created by lpjLiu on 2017/7/10.  智能能模版提交类型
 */
@Deprecated
public enum AutoTemplateSubmitType {
	客户提交(0, "客户提交"), 代理商提交(1, "代理商提交"), 平台提交(2, "平台提交"),系统自动提交(3,"系统自动提交");

	private Integer value;
	private String desc;

	AutoTemplateSubmitType(Integer value, String desc) {
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
		for (AutoTemplateSubmitType s : AutoTemplateSubmitType.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
