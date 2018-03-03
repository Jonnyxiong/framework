package com.jsmsframework.common.enums.schedule;

/**
 * Created by huangwenjie on 2017/11/30. 参数类型，
 0：系统参数
 1：SMSP参数

 */
public enum ParamType {
	系统参数(0, "系统参数"), SMSP参数(1, "SMSP参数");

	private Integer value;
	private String desc;

	ParamType(Integer value, String desc) {
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
		for (ParamType s : ParamType.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
