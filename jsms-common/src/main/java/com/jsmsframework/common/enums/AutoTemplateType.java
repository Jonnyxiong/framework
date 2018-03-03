package com.jsmsframework.common.enums;

/**
 * Created by lpjLiu on 2017/7/10. 智能能模版类型
 */
@Deprecated
public enum AutoTemplateType {
	固定模板(0, "固定模板"), 变量模板(1, "变量模板");

	private Integer value;
	private String desc;

	AutoTemplateType(Integer value, String desc) {
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
		for (AutoTemplateType s : AutoTemplateType.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
