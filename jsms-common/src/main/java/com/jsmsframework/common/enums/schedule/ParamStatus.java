package com.jsmsframework.common.enums.schedule;

/**
 * Created by huangwenjie on 2017/12/01. 状态，
 1：有效，
 2：无效


 */
public enum ParamStatus {
	有效(1, "有效"), 无效(2, "无效");

	private Integer value;
	private String desc;

	ParamStatus(Integer value, String desc) {
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
		for (ParamStatus s : ParamStatus.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
