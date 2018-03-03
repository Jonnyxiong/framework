package com.jsmsframework.common.enums;

/**
 * Created by lpjLiu on 2017/7/10. TaskType
 */
public enum TaskType {
	预付费客户余额预警(28, "预付费客户余额预警"),
	代理商可用额度预警(30, "代理商可用额度预警"),
	预付费OEM客户余额预警(36, "预付费OEM客户余额预警"),
	OEM代理商可用额度预警(37, "OEM代理商可用额度预警");
	private Integer value;
	private String desc;

	TaskType(Integer value, String desc) {
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
		for (TaskType s : TaskType.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
